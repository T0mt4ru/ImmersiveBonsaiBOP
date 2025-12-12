package com.tomtaru.immersivebonsaibop.block;

import com.tomtaru.immersivebonsaibop.ImmersiveBonsaiBOP;
import com.tomtaru.immersivebonsaibop.item.ModitemsBOP;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocksBOP {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ImmersiveBonsaiBOP.MODID);

    public static final DeferredBlock<Block> ORIGIN_BONSAI = registerBonsai("origin");
    public static final DeferredBlock<Block> FLOWERING_OAK_BONSAI = registerBonsai("flowering_oak");
    public static final DeferredBlock<Block> CYPRESS_BONSAI = registerBonsai("cypress");
    public static final DeferredBlock<Block> SNOWBLOSSOM_BONSAI = registerBonsai("snowblossom");
    public static final DeferredBlock<Block> RAINBOW_BIRCH_BONSAI = registerBonsai("rainbow_birch");
    public static final DeferredBlock<Block> FIR_BONSAI = registerBonsai("fir");
    public static final DeferredBlock<Block> PINE_BONSAI = registerBonsai("pine");
    public static final DeferredBlock<Block> RED_MAPLE_BONSAI = registerBonsai("red_maple");
    public static final DeferredBlock<Block> ORANGE_MAPLE_BONSAI = registerBonsai("orange_maple");
    public static final DeferredBlock<Block> YELLOW_MAPLE_BONSAI = registerBonsai("yellow_maple");
    public static final DeferredBlock<Block> REDWOOD_BONSAI = registerBonsai("redwood");
    public static final DeferredBlock<Block> MAHOGANY_BONSAI = registerBonsai("mahogany");
    public static final DeferredBlock<Block> JACARANDA_BONSAI = registerBonsai("jacaranda");
    public static final DeferredBlock<Block> PALM_BONSAI = registerBonsai("palm");
    public static final DeferredBlock<Block> WILLOW_BONSAI = registerBonsai("willow");
    public static final DeferredBlock<Block> DEAD_BONSAI = registerBonsai("dead");
    public static final DeferredBlock<Block> MAGIC_BONSAI = registerBonsai("magic");
    public static final DeferredBlock<Block> UMBRAN_BONSAI = registerBonsai("umbran");
    public static final DeferredBlock<Block> HELLBARK_BONSAI = registerBonsai("hellbark");
    public static final DeferredBlock<Block> EMPYREAL_BONSAI = registerBonsai("empyreal");



    private static DeferredBlock<Block> registerBonsai(String woodType) {
        return registerBlock(woodType + "_bonsai", () -> createBonsaiBlock());
    }
    private static Block createBonsaiBlock() {
            return new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void  registerBlockItem(String name, DeferredBlock<T> block) {
        ModitemsBOP.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus);}
}
