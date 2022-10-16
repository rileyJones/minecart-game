package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import components.Position;
import components.Velocity;
import ecs.Component;
import ecs.Entity;
import ecs.OneBodySystem;
import ecs.Result;
import ecs.TRAIT;
import physics.Physics;

public class VelocitySystem extends OneBodySystem{

	@Override
	protected void update(Entity e, GameContainer container, StateBasedGame game, int delta) {
		Position pos = (Position) e.getTraitByID(TRAIT.POSITION).unwrap();
		Velocity vel = (Velocity) e.getTraitByID(TRAIT.VELOCITY).unwrap();
		Physics.doVelocity(pos, vel, 0, 0, delta);
	}

	@Override
	protected boolean test(Entity e) {
		Result<Component, NoSuchElementException> posTrait = e.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> velTrait = e.getTraitByID(TRAIT.VELOCITY);
		return posTrait.is_ok() && velTrait.is_ok();
	}

}
