package net.squidwalk.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.squidwalk.SquidWalk;
import net.squidwalk.entitiy.ModEntities;
import software.bernie.geckolib.event.GeoRenderEvent;

public class ModItems {

    public static final Item ORANGE_SQUID_SPAWN_EGG = registeItem("orange_squid_spawn_egg",
            new SpawnEggItem(ModEntities.ORANGE_SQUID,0xff5900,0xff5900, new FabricItemSettings()));

    private static Item registeItem(String name, Item item){
        return Registry.register(Registries.ITEM,new Identifier(SquidWalk.MOD_ID,name),item);
    }
}
