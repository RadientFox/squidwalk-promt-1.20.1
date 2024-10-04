package net.squidwalk.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.squidwalk.entity.ai.goal.SquidAttackGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import java.util.EnumSet;


@Mixin(WaterCreatureEntity.class)
public class SquidChangeMixin extends WaterCreatureEntity {

	private PathAwareEntity SquidEntity;

	public SquidChangeMixin(EntityType<? extends net.minecraft.entity.passive.SquidEntity> entityType, World world) {
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
this.goalSelector.add(1,new WanderAroundGoal(this,speed));
	this.goalSelector.add(0, new SquidAttackGoal(this,speed,true));

	}



		public static class AttackGoal extends Goal {
			private final MobEntity mob;
			private LivingEntity target;

			private int cooldown;

			public AttackGoal(MobEntity mob) {
				this.mob = mob;
				this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
			}

			@Override
			public boolean canStart() {
				LivingEntity livingEntity = this.mob.getTarget();
				if (livingEntity == null) {
					return false;
				} else {
					this.target = livingEntity;
					return true;
				}
			}

			@Override
			public boolean shouldContinue() {
				if (!this.target.isAlive()) {
					return false;
				} else {
					return !(this.mob.squaredDistanceTo(this.target) > 225.0) && (!this.mob.getNavigation().isIdle() || this.canStart());
				}
			}

			@Override
			public void stop() {
				this.target = null;
				this.mob.getNavigation().stop();
			}

			@Override
			public boolean shouldRunEveryTick() {
				return true;
			}

			@Override
			public void tick() {
				this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
				double d = (double)(this.mob.getWidth() * 2.0F * this.mob.getWidth() * 2.0F);
				double e = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
				double f = 0.8;
				if (e > d && e < 16.0) {
					f = 1.33;
				} else if (e < 225.0) {
					f = 0.6;
				}

				this.mob.getNavigation().startMovingTo(this.target, f);
				this.cooldown = Math.max(this.cooldown - 1, 0);
				if (!(e > d)) {
					if (this.cooldown <= 0) {
						this.cooldown = 20;
						this.mob.tryAttack(this.target);
					}
				}
			}
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

