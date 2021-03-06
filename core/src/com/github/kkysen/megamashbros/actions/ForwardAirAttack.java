package com.github.kkysen.megamashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.megamashbros.core.Hitbox;
import com.github.kkysen.megamashbros.core.State;

public class ForwardAirAttack extends AirAttack {
    
    public ForwardAirAttack(final State state, final float startup, final float duration,
            final float cooldown, final float damage, final float knockback) {
        super(state, KeyBinding.ATTACK_FORWARD, new State[] {}, startup, duration, cooldown, damage,
                270, knockback);
    }
    
    @Override
    protected void attack(final State state, final boolean facingRight) {
        super.attack(state, facingRight);
        final Hitbox hitbox = state.newHitbox(this, 40f, 50f);
        hitbox.angle = angle;
        hitbox.position.x += facingRight ? 20f : -20f;
        state.addHitbox(hitbox);
    }
    
}
