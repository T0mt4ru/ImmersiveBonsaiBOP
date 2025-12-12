package com.tomtaru.immersivebonsaibop.datagen;

import biomesoplenty.api.item.BOPItems;
import com.tomtaru.immersivebonsaibop.block.ModBlocksBOP;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

/**
 * Central definition for all bonsai types
 * Define each wood type ONCE here.
 */
public class BonsaiDataBOP {

    // All bonsai definitions in one place
    public static final List<BonsaiType> ALL_BONSAIS_BOP = List.of(
            new BonsaiType(ModBlocksBOP.ORIGIN_BONSAI, "origin", BOPItems.ORIGIN_SAPLING, PlantType.SAPLING,
                    Items.OAK_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f),
                    new ExtraDrop(Items.APPLE, 1, 0.005f)
            ),
            new BonsaiType(ModBlocksBOP.CYPRESS_BONSAI, "cypress", BOPItems.CYPRESS_SAPLING, PlantType.SAPLING,
                    Items.SPRUCE_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.DEAD_BONSAI, "dead", BOPItems.DEAD_SAPLING, PlantType.SAPLING,
                    BOPItems.DEAD_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.EMPYREAL_BONSAI, "empyreal", BOPItems.EMPYREAL_SAPLING, PlantType.SAPLING,
                    BOPItems.EMPYREAL_LOG, 4,
                    Dimension.END, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f),
                    new ExtraDrop(Items.SHROOMLIGHT, 1, 0.01f)
            ),
            new BonsaiType(ModBlocksBOP.FIR_BONSAI, "fir", BOPItems.FIR_SAPLING, PlantType.SAPLING,
                    BOPItems.FIR_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.FLOWERING_OAK_BONSAI, "flowering_oak", BOPItems.FLOWERING_OAK_SAPLING, PlantType.SAPLING,
                    Items.OAK_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f),
                    new ExtraDrop(Items.APPLE, 1, 0.005f)
            ),
            new BonsaiType(ModBlocksBOP.HELLBARK_BONSAI, "hellbark", BOPItems.HELLBARK_SAPLING, PlantType.SAPLING,
                    BOPItems.HELLBARK_LOG, 4,
                    Dimension.NETHER, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.JACARANDA_BONSAI, "jacaranda", BOPItems.JACARANDA_SAPLING, PlantType.SAPLING,
                    BOPItems.JACARANDA_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.MAGIC_BONSAI, "magic", BOPItems.MAGIC_SAPLING, PlantType.SAPLING,
                    BOPItems.MAGIC_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.MAHOGANY_BONSAI, "mahogany", BOPItems.MAHOGANY_SAPLING, PlantType.SAPLING,
                    BOPItems.MAHOGANY_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.ORANGE_MAPLE_BONSAI, "orange_maple", BOPItems.ORANGE_MAPLE_SAPLING, PlantType.SAPLING,
                    BOPItems.MAPLE_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f),
                    new ExtraDrop(BOPItems.ORANGE_MAPLE_LEAF_PILE, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.RED_MAPLE_BONSAI, "red_maple", BOPItems.RED_MAPLE_SAPLING, PlantType.SAPLING,
                    BOPItems.MAPLE_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f),
                    new ExtraDrop(BOPItems.RED_MAPLE_LEAF_PILE, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.YELLOW_MAPLE_BONSAI, "yellow_maple", BOPItems.YELLOW_MAPLE_SAPLING, PlantType.SAPLING,
                    BOPItems.MAPLE_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f),
                    new ExtraDrop(BOPItems.YELLOW_MAPLE_LEAF_PILE, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.PALM_BONSAI, "palm", BOPItems.PALM_SAPLING, PlantType.SAPLING,
                    BOPItems.PALM_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.PINE_BONSAI, "pine", BOPItems.PINE_SAPLING, PlantType.SAPLING,
                    BOPItems.PINE_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.RAINBOW_BIRCH_BONSAI, "rainbow_birch", BOPItems.RAINBOW_BIRCH_SAPLING, PlantType.SAPLING,
                    Items.BIRCH_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.REDWOOD_BONSAI, "redwood", BOPItems.REDWOOD_SAPLING, PlantType.SAPLING,
                    BOPItems.REDWOOD_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.SNOWBLOSSOM_BONSAI, "snowblossom", BOPItems.SNOWBLOSSOM_SAPLING, PlantType.SAPLING,
                    Items.CHERRY_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.UMBRAN_BONSAI, "umbran", BOPItems.UMBRAN_SAPLING, PlantType.SAPLING,
                    BOPItems.UMBRAN_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            ),
            new BonsaiType(ModBlocksBOP.WILLOW_BONSAI, "willow", BOPItems.WILLOW_SAPLING, PlantType.SAPLING,
                    BOPItems.WILLOW_LOG, 4,
                    Dimension.OVERWORLD, 3600,
                    new ExtraDrop(Items.STICK, 1, 0.05f)
            )

    );

    /**
     * Complete definition of a bonsai type with all its properties
     */
    public record BonsaiType(
            DeferredBlock<?> bonsai,        // The bonsai block
            String woodType,                // e.g., "oak", "spruce" - for rendering
            ItemLike plantItem,             // The sapling item for crafting
            PlantType plantType,            // Suffix to use for the texture (sapling, fungus, etc.)
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