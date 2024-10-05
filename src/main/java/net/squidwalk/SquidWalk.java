package net.squidwalk;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.squidwalk.entitiy.ModEntities;
import net.squidwalk.entitiy.custom.OrangeSquidEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SquidWalk implements ModInitializer {
	public static final String MOD_ID = "squidwalk";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		FabricDefaultAttributeRegistry.register(ModEntities.ORANGE_SQUID, OrangeSquidEntity.setAttributes());

	}
}