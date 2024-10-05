package net.squidwalk.entitiy.client;

import net.minecraft.util.Identifier;
import net.squidwalk.SquidWalk;
import net.squidwalk.entitiy.custom.OrangeSquidEntity;
import software.bernie.geckolib.model.GeoModel;

public class OrangeSquidModel extends GeoModel<OrangeSquidEntity> {
    @Override
    public Identifier getModelResource(OrangeSquidEntity animatable) {
        return new Identifier(SquidWalk.MOD_ID, "geo/orange_squid.geo.json");
    }

    @Override
    public Identifier getTextureResource(OrangeSquidEntity animatable) {
        return new Identifier(SquidWalk.MOD_ID,"textures/entity/orange_squid.png");
    }

    @Override
    public Identifier getAnimationResource(OrangeSquidEntity animatable) {
        return new Identifier(SquidWalk.MOD_ID,"animations/orange_squid.animation.json");
    }


}
