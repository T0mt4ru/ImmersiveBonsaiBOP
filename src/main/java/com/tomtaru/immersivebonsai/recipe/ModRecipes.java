package com.tomtaru.immersivebonsai.recipe;

import com.tomtaru.immersivebonsai.ImmersiveBonsai;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ImmersiveBonsai.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BonsaiCraftingRecipe>> BONSAI_CRAFTING_SERIALIZER =
            RECIPE_SERIALIZERS.register("bonsai_crafting", BonsaiCraftingRecipe.Serializer::new);
}