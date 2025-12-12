package com.tomtaru.immersivebonsaibop.datagen;

import com.tomtaru.immersivebonsaibop.block.ModBlocksBOP;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModLootTableProviderBOP extends BlockLootSubProvider {

    protected ModLootTableProviderBOP(HolderLookup.Provider registries) {
        // Empty set for conditions, all feature flags
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // Loop through all bonsai types and drop themselves
        for (BonsaiDataBOP.BonsaiType bonsai : BonsaiDataBOP.ALL_BONSAIS_BOP) {
            dropSelf(bonsai.bonsai().get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocksBOP.BLOCKS.getEntries().stream()
                .<Block>map(holder -> holder.get())  // cast to Block explicitly
                .toList();                            // now List<Block>, satisfies Iterable<Block>
    }


}
