package net.squidwalk.entitiy;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.squidwalk.SquidWalk;
import net.squidwalk.entitiy.custom.OrangeSquidEntity;

public class ModEntities {
    public static final EntityType<OrangeSquidEntity> ORANGE_SQUID = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(SquidWalk.MOD_ID,"orange_squid"),
            FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, OrangeSquidEntity::new)
                    .dimensions(EntityDimensions.fixed(1f,2f)).build());

}
