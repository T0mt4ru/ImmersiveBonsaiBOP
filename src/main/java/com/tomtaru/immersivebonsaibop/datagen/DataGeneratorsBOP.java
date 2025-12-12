package com.tomtaru.immersivebonsaibop.datagen;


import com.tomtaru.immersivebonsaibop.ImmersiveBonsaiBOP;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// This annotation makes this class listen for datagen events
@EventBusSubscriber(modid = ImmersiveBonsaiBOP.MODID)
public class DataGeneratorsBOP {

    // This method runs when you execute "runData" gradle task
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // Get the data generator
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = new NoValidationExistingFileHelper(
                event.getExistingFileHelper()
        );
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Register CLIENT-side providers (models, blockstates, translations)
        // includeClient() checks if we should generate client data
        generator.addProvider(
                event.includeClient(),                                    // Should we run this?
                new ModBlockStateProviderBOP(output, existingFileHelper)    // Our provider
        );

        generator.addProvider(
                event.includeClient(),
                new ModLanguageProviderBOP(output)
        );

        // Register SERVER-side providers (recipes, loot tables, tags)
        generator.addProvider(
                event.includeServer(),
                new ModRecipeProviderBOP(output, lookupProvider)
        );

        generator.addProvider(
                event.includeServer(),
                new ModClocheRecipeProviderBOP(output)
        );

        generator.addProvider(
                event.includeServer(),
                new ModFarmersClocheRecipeProviderBOP(output)
        );

        // Loot tables need special wrapping
        generator.addProvider(
                event.includeServer(),
                new LootTableProvider(
                        output,
                        Collections.emptySet(),  // No required tables
                        List.of(
                                // Tell it to use our ModLootTableProviderBOP for block loot tables
                                new LootTableProvider.SubProviderEntry(
                                        ModLootTableProviderBOP::new,           // Our provider
                                        LootContextParamSets.BLOCK          // Type: block loot
                                )
                        ),
                        lookupProvider
                )
        );
    }
    /**
     * Custom ExistingFileHelper that skips validation for external mod resources
     * This allows us to reference BOP textures without having them during datagen
     */
    private static class NoValidationExistingFileHelper extends ExistingFileHelper {
        private final ExistingFileHelper delegate;

        public NoValidationExistingFileHelper(ExistingFileHelper delegate) {
            super(Collections.emptyList(), Collections.emptySet(), false, null, null);
            this.delegate = delegate;
        }


        public boolean exists(ResourceLocation loc, ResourceType resourceType) {
            // If it's from biomesoplenty, just say it exists
            if (loc.getNamespace().equals("biomesoplenty")) {
                return true;
            }
            // Otherwise, delegate to the real helper
            return delegate.exists(loc, resourceType);
        }


        public boolean exists(ResourceLocation loc, ResourceType resourceType, String pathSuffix, String pathPrefix) {
            // If it's from biomesoplenty, just say it exists
            if (loc.getNamespace().equals("biomesoplenty")) {
                return true;
            }
            // Otherwise, delegate to the real helper
            return delegate.exists(loc, resourceType);
        }
    }
}