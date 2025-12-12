package com.tomtaru.immersivebonsai.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

public class BonsaiCraftingRecipe extends ShapelessRecipe {
    private final String group;
    private final CraftingBookCategory category;

    public BonsaiCraftingRecipe(String group, CraftingBookCategory category, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(group, category, result, ingredients);
        this.group = group;
        this.category = category;
    }

    public String getGroup() {
        return group;
    }

    public CraftingBookCategory getCategory() {
        return category;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < remaining.size(); i++) {
            ItemStack stack = input.getItem(i);

            // Check if this is shears
            if (stack.is(Items.SHEARS)) {
                ItemStack damagedShears = stack.copy();
                // Damage the shears by 1
                damagedShears.setDamageValue(damagedShears.getDamageValue() + 1);

                // If shears would break, don't return them
                if (damagedShears.getDamageValue() >= damagedShears.getMaxDamage()) {
                    remaining.set(i, ItemStack.EMPTY);
                } else {
                    remaining.set(i, damagedShears);
                }
            }
        }

        return remaining;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BONSAI_CRAFTING_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<BonsaiCraftingRecipe> {
        private static final MapCodec<BonsaiCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Codec.STRING.optionalFieldOf("group", "").forGetter(BonsaiCraftingRecipe::getGroup),
                        CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(BonsaiCraftingRecipe::getCategory),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.getResultItem(null)),
                        Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(
                                ingredients -> {
                                    Ingredient[] array = ingredients.toArray(Ingredient[]::new);
                                    if (array.length == 0) {
                                        return com.mojang.serialization.DataResult.error(() -> "No ingredients for shapeless recipe");
                                    }
                                    if (array.length > 9) {
                                        return com.mojang.serialization.DataResult.error(() -> "Too many ingredients for shapeless recipe");
                                    }
                                    NonNullList<Ingredient> list = NonNullList.of(Ingredient.EMPTY, array);
                                    return com.mojang.serialization.DataResult.success(list);
                                },
                                com.mojang.serialization.DataResult::success
                        ).forGetter(recipe -> recipe.getIngredients())
                ).apply(instance, BonsaiCraftingRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, BonsaiCraftingRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork,
                Serializer::fromNetwork
        );

        @Override
        public MapCodec<BonsaiCraftingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BonsaiCraftingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BonsaiCraftingRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            String group = buf.readUtf();
            CraftingBookCategory category = buf.readEnum(CraftingBookCategory.class);
            int ingredientCount = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
                ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            }

            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            return new BonsaiCraftingRecipe(group, category, result, ingredients);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, BonsaiCraftingRecipe recipe) {
            buf.writeUtf(recipe.getGroup());
            buf.writeEnum(recipe.getCategory());
            buf.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buf, recipe.getResultItem(null));
        }
    }
}