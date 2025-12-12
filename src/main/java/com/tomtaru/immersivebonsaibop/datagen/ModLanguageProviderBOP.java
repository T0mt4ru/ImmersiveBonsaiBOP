package com.tomtaru.immersivebonsaibop.datagen;

import com.tomtaru.immersivebonsaibop.ImmersiveBonsaiBOP;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProviderBOP extends LanguageProvider {
    public ModLanguageProviderBOP(PackOutput output) {
        super(output, ImmersiveBonsaiBOP.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (BonsaiDataBOP.BonsaiType bonsai : BonsaiDataBOP.ALL_BONSAIS_BOP) {
            addBlock(bonsai.bonsai(), formatWoodName(bonsai.woodType()) + " Bonsai");
        }
    }
    private String formatWoodName(String woodType) {
        String[] parts = woodType.split("_"); // e.g., "dark_oak" -> ["dark", "oak"]
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.isEmpty()) continue;

            // Capitalize first letter, lowercase the rest
            sb.append(part.substring(0, 1).toUpperCase());
            if (part.length() > 1) sb.append(part.substring(1).toLowerCase());

            if (i < parts.length - 1) sb.append(" "); // add space between words
        }

        return sb.toString();
    }

}
