package com.tomtaru.immersivebonsaibop;

import com.tomtaru.immersivebonsaibop.block.ModBlocksBOP;
import com.tomtaru.immersivebonsaibop.datagen.BonsaiDataBOP;
import com.tomtaru.immersivebonsaibop.item.ModitemsBOP;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ImmersiveBonsaiBOP.MODID)
public class ImmersiveBonsaiBOP {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "immersivebonsaibop";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ImmersiveBonsaiBOP(IEventBus modEventBus, ModContainer modContainer) {

        // modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ModitemsBOP.register(modEventBus);
        ModBlocksBOP.register(modEventBus);
        //ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        // modContainer.registerConfig(ModConfig.Type.COMMON, ConfigBOP.SPEC);
    }

    //private void commonSetup(FMLCommonSetupEvent event) {
    //    // Some common setup code
    //    LOGGER.info("HELLO FROM COMMON SETUP");
    //
    //    if (ConfigBOP.LOG_DIRT_BLOCK.getAsBoolean()) {
    //        LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
    //    }
    //
    //    LOGGER.info("{}{}", ConfigBOP.MAGIC_NUMBER_INTRODUCTION.get(), ConfigBOP.MAGIC_NUMBER.getAsInt());
    //
    //    ConfigBOP.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    //}

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {

            for (BonsaiDataBOP.BonsaiType bonsai : BonsaiDataBOP.ALL_BONSAIS_BOP) {
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
