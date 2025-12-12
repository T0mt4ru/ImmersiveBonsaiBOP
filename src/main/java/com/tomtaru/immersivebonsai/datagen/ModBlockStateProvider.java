package com.tomtaru.immersivebonsai.datagen;

import com.tomtaru.immersivebonsai.ImmersiveBonsai;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ImmersiveBonsai.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Generate blockstates and models for ALL bonsais from central data
        for (BonsaiData.BonsaiType bonsai : BonsaiData.ALL_BONSAIS) {
            makeBonsai(bonsai);
        }
    }

    /**
     * Creates blockstate, block model, and item model for a bonsai
     * Uses the vanilla sapling texture from the wood type
     */
    private void makeBonsai(BonsaiData.BonsaiType bonsaiType) {
        DeferredBlock<?> block = bonsaiType.bonsai();
        String name = block.getId().getPath();
        String woodType = bonsaiType.woodType();

        // Create BLOCK MODEL (cross model for in-world rendering)
        ModelFile blockModel = models().withExistingParent(name, "minecraft:block/cross")
                .texture("cross", "minecraft:block/" + woodType + bonsaiType.plantType().getPlantType())
                .renderType("minecraft:cutout_mipped");

        // Create BLOCKSTATE
        simpleBlock(block.get(), blockModel);

        // Create ITEM MODEL (generated parent for inventory)
        // This matches your manual JSON: uses "item/generated" parent
        itemModels().withExistingParent(name, "minecraft:item/generated")
                .texture("layer0", "minecraft:block/" + woodType + bonsaiType.plantType().getPlantType());
    }
}