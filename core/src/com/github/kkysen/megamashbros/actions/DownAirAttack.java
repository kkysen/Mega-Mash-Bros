package com.github.kkysen.megamashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.megamashbros.core.Hitbox;
import com.github.kkysen.megamashbros.core.State;

public class DownAirAttack extends AirAttack {

	public DownAirAttack(State state, float startup, float duration,
			float cooldown, float damage, float knockback) {
		super(state, KeyBinding.ATTACK_DOWN, new State[]{}, startup, duration, cooldown, damage, 10, knockback);
	}

	
	@Override
    protected void attack(final State state, final boolean facingRight) {
        super.attack(state, facingRight);
        final Hitbox hitbox = state.newHitbox(this, 30f, 30f);
        hitbox.angle = facingRight ? angle : PI - angle;
        hitbox.position.y += -20f;
        hitbox.position.x += facingRight ? 5f : -5f;
        state.addHitbox(hitbox);
    }
}
