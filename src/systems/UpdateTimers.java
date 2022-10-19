package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import components.Timer;
import ecs.Component;
import ecs.Entity;
import ecs.OneBodySystem;
import ecs.Result;
import ecs.TRAIT;

public class UpdateTimers extends OneBodySystem{

	@Override
	protected void update(Entity e, GameContainer container, StateBasedGame game, int delta) {
		Timer timer = (Timer) e.getTraitByID(TRAIT.TIMER).unwrap();
		timer.update(delta);
	}

	@Override
	protected boolean test(Entity e) {
		Result<Component, NoSuchElementException> timerTrait = e.getTraitByID(TRAIT.TIMER);
		return timerTrait.is_ok();
	}

}
