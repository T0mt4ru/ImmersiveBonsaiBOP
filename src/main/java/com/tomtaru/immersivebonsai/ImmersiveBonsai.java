package com.tomtaru.immersivebonsai;

import com.tomtaru.immersivebonsai.block.ModBlocks;
import com.tomtaru.immersivebonsai.datagen.BonsaiData;
import com.tomtaru.immersivebonsai.item.Moditems;
import com.tomtaru.immersivebonsai.recipe.ModRecipes;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ImmersiveBonsai.MODID)
public class ImmersiveBonsai {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "immersivebonsai";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ImmersiveBonsai(IEventBus modEventBus, ModContainer modContainer) {

        // modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        Moditems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        // modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    //private void commonSetup(FMLCommonSetupEvent event) {
    //    // Some common setup code
    //    LOGGER.info("HELLO FROM COMMON SETUP");
    //
    //    if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
    //        LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
    //    }
    //
    //    LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());
    //
    //    Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    //}

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            for (BonsaiData.BonsaiType bonsai : BonsaiData.ALL_BONSAIS) {
                event.accept(bonsai.bonsai().get());
            }
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
