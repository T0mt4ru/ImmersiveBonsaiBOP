package com.tomtaru.immersivebonsaibop.item;

import com.tomtaru.immersivebonsaibop.ImmersiveBonsaiBOP;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModitemsBOP {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ImmersiveBonsaiBOP.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
