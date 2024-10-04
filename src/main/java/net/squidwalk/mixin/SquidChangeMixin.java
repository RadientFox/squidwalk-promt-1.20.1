package net.squidwalk.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;


@Mixin(SquidEntity.class)
public class SquidChangeMixin extends WaterCreatureEntity implements Monster {

    private PathAwareEntity SquidEntity;

    public SquidChangeMixin(EntityType<? extends SquidEntity> entityType, World world) {
        super(entityType, world);
    }
    private float swimX;
    private float swimY;
    private float swimZ;

    public void setSwimmingVector(float x, float y, float z) {
        this.swimX = x;
        this.swimY = y;
        this.swimZ = z;
    }
    public boolean hasSwimmingVector() {
        return this.swimX != 0.0F || this.swimY != 0.0F || this.swimZ != 0.0F;
    }

    @Overwrite
    public void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AttackGoal(this));

    }


    @Overwrite
    public static DefaultAttributeContainer.Builder createSquidAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10);
    }



    public boolean isAngryAt(PlayerEntity player) {
        return true;
    }


    static class SwimGoal extends Goal {
        private final SquidChangeMixin squid;

        public SwimGoal(SquidChangeMixin squid) {
            this.squid = squid;
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void tick() {
            int i = this.squid.getDespawnCounter();
            if (i > 100) {
                this.squid.setSwimmingVector(0.0F, 0.0F, 0.0F);
            } else if (this.squid.getRandom().nextInt(toGoalTicks(50)) == 0 || !this.squid.isTouchingWater() || !this.squid.hasSwimmingVector()) {
                float f = this.squid.getRandom().nextFloat() * (float) (Math.PI * 2);
                float g = MathHelper.cos(f) * 0.2F;
                float h = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;
                float j = MathHelper.sin(f) * 0.2F;
                this.squid.setSwimmingVector(g, h, j);
            }
        }
    }
}