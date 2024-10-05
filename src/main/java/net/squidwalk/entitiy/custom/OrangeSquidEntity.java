package net.squidwalk.entitiy.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class OrangeSquidEntity extends HostileEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private float swimX;
    private float swimY;
    private float swimZ;
    protected WanderAroundGoal wanderGoal;


    public OrangeSquidEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new AmphibiousSwimNavigation(this, world);


    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.wanderGoal = new WanderAroundGoal(this, 1.0, 80);
        this.goalSelector.add(1, new MeleeAttackGoal(this,1D,false));
        this.goalSelector.add(1,new ActiveTargetGoal<>(this, PlayerEntity.class, false));
    }


    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE,100)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE,100)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::predicate));

    }

    private <T extends GeoAnimatable>PlayState predicate(AnimationState<OrangeSquidEntity> orandeSquidEntityAnimationState) {
        if (orandeSquidEntityAnimationState.isMoving()){
            orandeSquidEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.orange_squid.move",Animation.LoopType.LOOP));
        }
            orandeSquidEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.orange_squid.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SQUID_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SQUID_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SQUID_DEATH;
    }

    public void setSwimmingVector(float x, float y, float z) {
        this.swimX = x;
        this.swimY = y;
        this.swimZ = z;
    }

    public boolean hasSwimmingVector() {
        return this.swimX != 0.0F || this.swimY != 0.0F || this.swimZ != 0.0F;
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.EVENTS;
    }


    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    static class SwimGoal extends Goal {
        private final OrangeSquidEntity orange_squid;

        public SwimGoal(OrangeSquidEntity orange_squid) {
            this.orange_squid = orange_squid;
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void tick() {
            int i = this.orange_squid.getDespawnCounter();
            if (i > 100) {
                this.orange_squid.setSwimmingVector(0.0F, 0.0F, 0.0F);
            } else if (this.orange_squid.getRandom().nextInt(toGoalTicks(50)) == 0 || !this.orange_squid.touchingWater || !this.orange_squid.hasSwimmingVector()) {
                float f = this.orange_squid.getRandom().nextFloat() * (float) (Math.PI * 2);
                float g = MathHelper.cos(f) * 0.2F;
                float h = -0.1F + this.orange_squid.getRandom().nextFloat() * 0.2F;
                float j = MathHelper.sin(f) * 0.2F;
                this.orange_squid.setSwimmingVector(g, h, j);
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return super.damage(source, amount) && this.getAttacker() != null;
    }

}
