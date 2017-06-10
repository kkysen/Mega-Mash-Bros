package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.app.Game;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class Action extends Executable implements Loggable {
    
    protected static final float PI = (float) Math.PI;
    
    private final State state;
    
    private final State[] impossiblePreStates;
    
    public final float startup;
    public final float duration;
    public final float cooldown;
    
    protected float elapsedTime;
    
    public boolean alreadyUsed = false;
    
    protected Action(final State state, final KeyBinding keyBinding,
            final State[] impossiblePreStates, final float warmupTime, final float duration,
            final float cooldown) {
        super(keyBinding);
        this.state = state.clone();
        this.state.action = this;
        this.impossiblePreStates = impossiblePreStates;
        startup = warmupTime;
        this.duration = duration;
        this.cooldown = cooldown;
    }
    
    public float totalTime() {
        return startup + duration + cooldown;
    }
    
    @Override
    public void update() {
        elapsedTime += Game.deltaTime;
    }
    
    private boolean isImpossiblePreState(final State state) {
        for (final State impossiblePreState : impossiblePreStates) {
            if (state == impossiblePreState) { // I meant to use ==
                return true;
            }
        }
        return false;
    }
    
    /**
     * allows subclasses to stop the execution
     * 
     * @param player the player executing this action
     * @return true if the execution shouldn't happen
     */
    protected boolean dontExecute(final Player player) {
        return false;
    }
    
    @Override
    public final State execute(final Player player) {
        // FIXME why did you change this
        if (/*elapsedTime < cooldown || */isImpossiblePreState(player.state)
                || dontExecute(player)) {
            error(this + " still in cooldown, " + (cooldown - elapsedTime) + " left");
            return player.state;
        }
        
        error("someone called " + this);
        
        if (!alreadyUsed /*&& !(this instanceof Stop)*/) {
            player.state.setPlayer(null);
            if (this instanceof ForwardAirAttack) {
                System.out.println("lol");
            }
            elapsedTime = 0;
            state.setPlayer(player);
            alreadyUsed = true;
        } else {
            player.state.setPlayer(null, false);
            state.setPlayer(player, false);
        }
        
        tryAttack(state, player.facingRight);
        move(player);
        return state;
    }
    
    protected void tryAttack(final State state, final boolean facingRight) {}
    
    protected void move(final Player player) {}
    
    @Override
    public void reset() {
        alreadyUsed = false;
        elapsedTime = 0;
        state.resetTime();
        // I don't know what you were trying to do here before,
        // but this should do the same and is clearer
    }
}
