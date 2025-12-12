package com.tomtaru.immersivebonsai.block;

import com.tomtaru.immersivebonsai.ImmersiveBonsai;
import com.tomtaru.immersivebonsai.item.Moditems;
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

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ImmersiveBonsai.MODID);

    public static final DeferredBlock<Block> OAK_BONSAI = registerBonsai("oak");
    public static final DeferredBlock<Block> SPRUCE_BONSAI = registerBonsai("spruce");
    public static final DeferredBlock<Block> BIRCH_BONSAI = registerBonsai("birch");
    public static final DeferredBlock<Block> JUNGLE_BONSAI = registerBonsai("jungle");
    public static final DeferredBlock<Block> ACACIA_BONSAI = registerBonsai("acacia");
    public static final DeferredBlock<Block> DARK_OAK_BONSAI = registerBonsai("dark_oak");
    public static final DeferredBlock<Block> CHERRY_BONSAI = registerBonsai("cherry");

    public static final DeferredBlock<Block> CRIMSON_BONSAI = registerBonsai("crimson");
    public static final DeferredBlock<Block> WARPED_BONSAI = registerBonsai("warped");



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
        Moditems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus);}
}
