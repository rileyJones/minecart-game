package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import components.Velocity;
import ecs.Component;
import ecs.Entity;
import ecs.OneBodySystem;
import ecs.Result;
import ecs.TRAIT;

public class FrictionSystem extends OneBodySystem{

	@Override
	protected void update(Entity e, GameContainer container, StateBasedGame game, int delta) {
		Velocity vel = (Velocity) e.getTraitByID(TRAIT.VELOCITY).unwrap();
		Vector2f velVec = ((Velocity)vel.getInternalValue()).getVel().unwrap();
		vel.set(new Velocity(velVec.x*7/8, velVec.y*7/8));
	}

	@Override
	protected boolean test(Entity e) {
		Result<Component, NoSuchElementException> velTrait = e.getTraitByID(TRAIT.VELOCITY);
		Result<Component, NoSuchElementException> noFrictionTrait = e.getTraitByID(TRAIT.NO_FRICTION);
		return velTrait.is_ok() && noFrictionTrait.is_err();
	}

}
