package com.tomtaru.immersivebonsai.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tomtaru.immersivebonsai.ImmersiveBonsai;
import com.tomtaru.immersivebonsai.recipe.BonsaiCraftingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Generates ALL recipes for bonsai trees in a single provider:
 * 1. Bonsai crafting recipes (shears + sapling = bonsai)
 * 2. Cloche growth recipes (bonsai -> logs + extras)
 *
 * Uses BonsaiData for central definition - change once, applies everywhere!
 */
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // Generate crafting recipes for ALL bonsais from central data
        for (BonsaiData.BonsaiType bonsai : BonsaiData.ALL_BONSAIS) {
            makeBonsaiCraftingRecipe(recipeOutput, bonsai);
        }

        // Cloche recipes are handled by the companion provider
        // (RecipeProvider is final and can't be extended further for custom JSON)
    }

    private void makeBonsaiCraftingRecipe(RecipeOutput output, BonsaiData.BonsaiType bonsaiType) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(Ingredient.of(Items.SHEARS));
        ingredients.add(Ingredient.of(bonsaiType.plantItem()));

        ItemStack resultStack = new ItemStack(bonsaiType.bonsai().get());

        BonsaiCraftingRecipe recipe = new BonsaiCraftingRecipe(
                "",
                CraftingBookCategory.MISC,
                resultStack,
                ingredients
        );

        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(
                ImmersiveBonsai.MODID,
                bonsaiType.bonsai().getId().getPath()
        );

        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_shears", has(Items.SHEARS))
                .addCriterion("has_plant_item", has(bonsaiType.plantItem()));

        output.accept(recipeId, recipe, advancement.build(recipeId.withPrefix("recipes/misc/")));
    }
}

/**
 * Companion provider for Cloche recipes
 * This needs to be separate because RecipeProvider doesn't support custom JSON formats
 */
class ModClocheRecipeProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public ModClocheRecipeProvider(PackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe/cloche");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        // Generate cloche recipes for ALL bonsais from central data
        for (BonsaiData.BonsaiType bonsai : BonsaiData.ALL_BONSAIS) {
            futures.add(makeClocheRecipe(cache, bonsai));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public String getName() {
        return "Immersive Bonsai Cloche Recipes";
    }

    private CompletableFuture<?> makeClocheRecipe(CachedOutput cache, BonsaiData.BonsaiType bonsaiType) {
        JsonObject json = new JsonObject();

        json.addProperty("type", "immersiveengineering:cloche");

        JsonObject input = new JsonObject();
        input.addProperty("item", bonsaiType.bonsai().getId().toString());
        json.add("input", input);

        JsonObject render = new JsonObject();
        render.addProperty("type", "immersiveengineering:generic");
        render.addProperty("block", "minecraft:" + bonsaiType.woodType() + bonsaiType.plantType().getPlantType());
        json.add("render", render);

        JsonArray results = new JsonArray();

        JsonObject mainResult = new JsonObject();
        mainResult.addProperty("id", getItemId(bonsaiType.log()));
        mainResult.addProperty("count", bonsaiType.logCount());
        results.add(mainResult);

        for (BonsaiData.ExtraDrop drop : bonsaiType.extraDrops()) {
            JsonObject chanceObj = new JsonObject();
            chanceObj.addProperty("chance", drop.chance());

            JsonObject output = new JsonObject();
            output.addProperty("id", getItemId(drop.item().asItem()));
            output.addProperty("count", drop.count());

            chanceObj.add("output", output);
            results.add(chanceObj);
        }

        json.add("results", results);

        JsonObject soil = new JsonObject();
        soil.addProperty("item", bonsaiType.dimension().getSoilBlock());
        json.add("soil", soil);

        json.addProperty("time", bonsaiType.growthTime());

        Path path = pathProvider.json(ResourceLocation.fromNamespaceAndPath(
                ImmersiveBonsai.MODID,
                bonsaiType.bonsai().getId().getPath()
        ));

        return DataProvider.saveStable(cache, json, path);
    }

    private String getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }
}

class ModFarmersClocheRecipeProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public ModFarmersClocheRecipeProvider(PackOutput output) {
        // Save to a different folder if you want: "recipe/cloche_special"
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe/cloche");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (BonsaiData.BonsaiType bonsai : BonsaiData.ALL_BONSAIS) {
            futures.add(makeFarmersClocheRecipe(
                    cache,
                    bonsai,
                    "farmersdelight",
                    "farmersdelight:rich_soil"
            ));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public String getName() {
        return "Immersive Bonsai Farmer's Delight Cloche Recipes";
    }

    /**
     * Example of a cloche recipe with custom soil and mod conditions
     */
    private CompletableFuture<?> makeFarmersClocheRecipe(
            CachedOutput cache,
            BonsaiData.BonsaiType bonsaiType,
            String requiredModId,
            String customSoil) {

        JsonObject json = new JsonObject();

        // Add NeoForge mod loaded condition
        JsonArray conditions = new JsonArray();
        JsonObject modLoadedCondition = new JsonObject();
        modLoadedCondition.addProperty("type", "neoforge:mod_loaded");
        modLoadedCondition.addProperty("modid", requiredModId);
        conditions.add(modLoadedCondition);
        json.add("neoforge:conditions", conditions);

        // Standard cloche recipe structure
        json.addProperty("type", "immersiveengineering:cloche");

        JsonObject input = new JsonObject();
        input.addProperty("item", bonsaiType.bonsai().getId().toString());
        json.add("input", input);

        JsonObject render = new JsonObject();
        render.addProperty("type", "immersiveengineering:generic");
        render.addProperty("block", "minecraft:" + bonsaiType.woodType() + bonsaiType.plantType().getPlantType());
        json.add("render", render);

        JsonArray results = new JsonArray();
        JsonObject mainResult = new JsonObject();
        mainResult.addProperty("id", getItemId(bonsaiType.log()));
        mainResult.addProperty("count", bonsaiType.logCount());
        results.add(mainResult);

        for (BonsaiData.ExtraDrop drop : bonsaiType.extraDrops()) {
            JsonObject chanceObj = new JsonObject();
            chanceObj.addProperty("chance", drop.chance());

            JsonObject output = new JsonObject();
            output.addProperty("id", getItemId(drop.item().asItem()));
            output.addProperty("count", drop.count());

            chanceObj.add("output", output);
            results.add(chanceObj);
        }
        json.add("results", results);

        // CUSTOM SOIL - this is different from the default dirt!
        JsonObject soil = new JsonObject();
        soil.addProperty("item", String.valueOf(customSoil));
        json.add("soil", soil);

        // Maybe faster growth time?
        json.addProperty("time", bonsaiType.growthTime() / 2);

        // Save with a different name to avoid conflicts
        Path path = pathProvider.json(ResourceLocation.fromNamespaceAndPath(
                ImmersiveBonsai.MODID,
                bonsaiType.bonsai().getId().getPath() + "_rich_soil"
        ));

        return DataProvider.saveStable(cache, json, path);
    }

    private String getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }
}