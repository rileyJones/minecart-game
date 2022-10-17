package ai;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import components.AI;
import components.Velocity;
import controller.BUTTON;
import controller.Controller;
import ecs.Component;
import ecs.Result;
import ecs.TRAIT;

public class Player extends AI{
	
	private Controller controller;
	
	private static final float MOVE_VEL = 0.1f;
	
	public Player(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		setVelocityController();
		
	}
	
	private void setVelocityController() {
		Vector2f newVel = new Vector2f(0,0);
		if(controller.buttonHeld(BUTTON.KEY_LEFT)) {
			newVel.x -= 1;
		}
		if(controller.buttonHeld(BUTTON.KEY_DOWN)) {
			newVel.y += 1;
		}
		if(controller.buttonHeld(BUTTON.KEY_UP)) {
			newVel.y -= 1;
		}
		if(controller.buttonHeld(BUTTON.KEY_RIGHT)) {
			newVel.x += 1;
		}
		
		if(newVel.lengthSquared() != 0) {
			newVel.normalise();
		}
		
		newVel.x *= MOVE_VEL;
		newVel.y *= MOVE_VEL;
		
		Result<Component, NoSuchElementException> velResult = owner.getTraitByID(TRAIT.VELOCITY);
		if(velResult.is_ok()) {
			velResult.unwrap().set(new Velocity(newVel.x, newVel.y));
		}
	}

	@Override
	public Component clone() {
		return new Player(controller);
	}

}
