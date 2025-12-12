package com.tomtaru.immersivebonsai.datagen;

import com.tomtaru.immersivebonsai.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModLootTableProvider extends BlockLootSubProvider {

    protected ModLootTableProvider(HolderLookup.Provider registries) {
        // Empty set for conditions, all feature flags
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // Loop through all bonsai types and drop themselves
        for (BonsaiData.BonsaiType bonsai : BonsaiData.ALL_BONSAIS) {
            dropSelf(bonsai.bonsai().get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .<Block>map(holder -> holder.get())  // cast to Block explicitly
                .toList();                            // now List<Block>, satisfies Iterable<Block>
    }


}
