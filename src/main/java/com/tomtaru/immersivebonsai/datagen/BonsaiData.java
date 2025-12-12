package com.tomtaru.immersivebonsai.datagen;

import com.tomtaru.immersivebonsai.block.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.awt.*;
import java.util.List;

/**
 * Central definition for all bonsai types
 * Define each wood type ONCE here, and both recipe providers use it!
 */
public class BonsaiData {

    // All bonsai definitions in one place
    public static final List<BonsaiType> ALL_BONSAIS = List.of(
            new BonsaiType(ModBlocks.OAK_BONSAI, "oak", Items.OAK_SAPLING, PlantType.SAPLING, Items.OAK_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f),
                    new ExtraDrop(Items.APPLE, 1, 0.005f)
            ),
            new BonsaiType(ModBlocks.SPRUCE_BONSAI, "spruce", Items.SPRUCE_SAPLING, PlantType.SAPLING, Items.SPRUCE_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocks.BIRCH_BONSAI, "birch", Items.BIRCH_SAPLING, PlantType.SAPLING, Items.BIRCH_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocks.JUNGLE_BONSAI, "jungle", Items.JUNGLE_SAPLING, PlantType.SAPLING, Items.JUNGLE_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f),
                    new ExtraDrop(Items.COCOA_BEANS, 1, 0.01f)
            ),
            new BonsaiType(ModBlocks.ACACIA_BONSAI, "acacia", Items.ACACIA_SAPLING, PlantType.SAPLING, Items.ACACIA_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocks.DARK_OAK_BONSAI, "dark_oak", Items.DARK_OAK_SAPLING, PlantType.SAPLING, Items.DARK_OAK_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f),
                    new ExtraDrop(Items.APPLE, 1, 0.005f)
            ),
            new BonsaiType(ModBlocks.CHERRY_BONSAI, "cherry", Items.CHERRY_SAPLING, PlantType.SAPLING, Items.CHERRY_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),

            new BonsaiType(ModBlocks.CRIMSON_BONSAI, "crimson", Items.CRIMSON_FUNGUS, PlantType.FUNGUS, Items.CRIMSON_STEM, 4,
                    Dimension.NETHER, 3600,
                    new ExtraDrop(Items.NETHER_WART_BLOCK, 1, 0.25f)
            ),
            new BonsaiType(ModBlocks.WARPED_BONSAI, "warped", Items.WARPED_FUNGUS, PlantType.FUNGUS, Items.WARPED_STEM, 4,
                    Dimension.NETHER, 3600,
                    new ExtraDrop(Items.WARPED_WART_BLOCK, 1, 0.25f)
            )
    );

    /**
     * Complete definition of a bonsai type with all its properties
     */
    public record BonsaiType(
            DeferredBlock<?> bonsai,        // The bonsai block
            String woodType,                // e.g., "oak", "spruce" - for rendering
            ItemLike plantItem,             // The sapling item for crafting
            PlantType plantType,           // Suffix to use for the texture (sapling, fungus, etc.)
            Item log,                       // The log output
            int logCount,                   // How many logs
            Dimension dimension,            // Dimension this normally grows in (overworld, nether, end)
            int growthTime,                 // Cloche growth time in ticks
            ExtraDrop... extraDrops         // Optional extra drops (apples, sticks, etc.)
    ) {}

    public enum PlantType {
        SAPLING,
        FUNGUS;

        public String getPlantType() {
            return switch (this) {
                case SAPLING -> "_sapling";
                case FUNGUS ->  "_fungus";
            };
        }
    }
    public enum Dimension {
        OVERWORLD,
        NETHER,
        END;
        // AETHER;

        public String getSoilBlock() {
            return switch (this) {
                case OVERWORLD ->   "minecraft:dirt";
                case NETHER ->      "minecraft:netherrack";
                case END ->         "minecraft:end_stone";
            };
        }
    }

    /**
     * Extra drop with chance (for cloche recipes)
     */
    public record ExtraDrop(
            ItemLike item,
            int count,
            float chance   // 0.0-1.0 (0.05 = 5%)
    ) {}
}