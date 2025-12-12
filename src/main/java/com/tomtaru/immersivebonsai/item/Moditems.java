package com.tomtaru.immersivebonsai.item;

import com.tomtaru.immersivebonsai.ImmersiveBonsai;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Moditems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ImmersiveBonsai.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
