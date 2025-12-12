package com.tomtaru.immersivebonsai.datagen;

import com.tomtaru.immersivebonsai.ImmersiveBonsai;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// This annotation makes this class listen for datagen events
@EventBusSubscriber(modid = ImmersiveBonsai.MODID)
public class DataGenerators {

    // This method runs when you execute "runData" gradle task
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // Get the data generator
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Register CLIENT-side providers (models, blockstates, translations)
        // includeClient() checks if we should generate client data
        generator.addProvider(
                event.includeClient(),                                    // Should we run this?
                new ModBlockStateProvider(output, existingFileHelper)    // Our provider
        );

        generator.addProvider(
                event.includeClient(),
                new ModLanguageProvider(output)
        );

        // Register SERVER-side providers (recipes, loot tables, tags)
        generator.addProvider(
                event.includeServer(),
                new ModRecipeProvider(output, lookupProvider)
        );

        generator.addProvider(
                event.includeServer(),
                new ModClocheRecipeProvider(output)
        );

        generator.addProvider(
                event.includeServer(),
                new ModFarmersClocheRecipeProvider(output)
        );

        // Loot tables need special wrapping
        generator.addProvider(
                event.includeServer(),
                new LootTableProvider(
                        output,
                        Collections.emptySet(),  // No required tables
                        List.of(
                                // Tell it to use our ModLootTableProvider for block loot tables
                                new LootTableProvider.SubProviderEntry(
                                        ModLootTableProvider::new,           // Our provider
                                        LootContextParamSets.BLOCK          // Type: block loot
                                )
                        ),
                        lookupProvider
                )
        );
    }
}