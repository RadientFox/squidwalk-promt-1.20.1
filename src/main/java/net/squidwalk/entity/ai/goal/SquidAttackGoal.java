package net.squidwalk.entity.ai.goal;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.passive.SquidEntity;

public class SquidAttackGoal extends MeleeAttackGoal {
    private final SquidEntity squid;
    private int ticks;

    public SquidAttackGoal(SquidEntity squid, double speed, boolean pauseWhenMobIdle) {
        super(squid, speed, pauseWhenMobIdle);
        this.squid = squid;
    }

    @Override
    public void start() {
        super.start();
        this.ticks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.squid.setAttacking(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.ticks++;
        if (this.ticks >= 5 && this.getCooldown() < this.getMaxCooldown() / 2) {
            this.squid.setAttacking(true);
        } else {
            this.squid.setAttacking(false);
        }
    }
}
