package net.squidwalk;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.squidwalk.entitiy.ModEntities;
import net.squidwalk.entitiy.client.OrangeSquidRenderer;

public class SquidWalkClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.ORANGE_SQUID, OrangeSquidRenderer::new);

    }
}
