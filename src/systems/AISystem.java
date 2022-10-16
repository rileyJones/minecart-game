package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import components.AI;
import ecs.Component;
import ecs.Entity;
import ecs.OneBodySystem;
import ecs.Result;
import ecs.TRAIT;

public class AISystem extends OneBodySystem{

	@Override
	protected void update(Entity e, GameContainer container, StateBasedGame game, int delta) {
		((AI)(e.getTraitByID(TRAIT.AI).unwrap())).update(container, game, delta);
	}

	@Override
	protected boolean test(Entity e) {
		Result<Component, NoSuchElementException> aiTrait = e.getTraitByID(TRAIT.AI);
		return aiTrait.is_ok();
	}

}
