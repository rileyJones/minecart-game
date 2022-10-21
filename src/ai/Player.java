package ai;

import java.util.NoSuchElementException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import components.AI;
import components.Box;
import components.ColorC;
import components.Position;
import components.Velocity;
import controller.BUTTON;
import controller.Controller;
import ecs.Component;
import ecs.Entity;
import ecs.Result;
import ecs.TRAIT;

public class Player extends AI{
	
	private Controller controller;
	
	private int timer = -1;
	
	private static final float MOVE_VEL = 0.1f; 
	
	Entity swordEntity = new Entity(new Component[] {
		new Position(0,0),
		new Box(-24,-24,48,48),
		new ColorC(Color.white),
		new Sword()
	});
	
	ITEM button_a;
	ITEM button_b;
	
	STATE currentState;
	
	DIRECTION facingDirection;
	
	enum ITEM {
		NOTHING,
		SWORD,
		SHIELD,
		FEATHER,
		STAFF,
		RAY
	}
	
	enum STATE {
		WALKING,
		SWORD,
	}
	
	enum DIRECTION {
		UP,
		LEFT,
		RIGHT,
		DOWN
	}
	
	public Player(Controller controller) {
		this.controller = controller;
		button_a = ITEM.SWORD;
		button_b = ITEM.NOTHING;
		currentState = STATE.WALKING;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		updateTimer(delta);
		resetVelocity();
		switch(currentState) {
			case SWORD:
				if(itemUsed(ITEM.SWORD)) enterState(STATE.SWORD);
				if(timerIsDone()) enterState(STATE.WALKING);
				break;
			case WALKING:
				setVelocityController();
				useItem();
			default:
				break;
		}
	}
	
	private void enterState(STATE S) {
		switch(currentState) {
			case SWORD:
				swordEntity.setParent(null);
				break;
			case WALKING:
				break;
			default:
				break;
			
		}
		switch(S) {
			case SWORD:
				swordEntity.setParent(owner);
				switch(facingDirection) {
					case DOWN:
						swordEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(-12,12));
						break;
					case LEFT:
						swordEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(-12,-12));
						break;
					case RIGHT:
						swordEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(12,-12));
						break;
					case UP:
						swordEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(12,-12));
						break;
					default:
						break;
					
				}
				setTimer(200);
				break;
			case WALKING:
				break;
			default:
				break;
			
		}
		currentState = S;
	}
	
	private boolean itemUsed(ITEM I) {
		if(controller.buttonPressed(BUTTON.KEY_A) && button_a == I) {
			return true;
		} else if(controller.buttonPressed(BUTTON.KEY_B) && button_b == I) {
			return true;
		}
		return false;
	}
	
	private void setTimer(int time) {
		timer = time;
	}
	private boolean updateTimer(int delta) {
		timer = Math.max(-1,timer-delta);
		return timer <= 0;
	}
	private boolean timerIsDone() {
		return timer <= 0;
	}
	
	private void useItem() {
		ITEM itemUsed = ITEM.NOTHING;
		
		if(controller.buttonPressed(BUTTON.KEY_A)) {
			itemUsed = button_a;
		} else if(controller.buttonPressed(BUTTON.KEY_B)) {
			itemUsed = button_b;
		}
		
		switch(itemUsed) {
			case FEATHER:
				break;
			case NOTHING:
				break;
			case RAY:
				break;
			case SHIELD:
				break;
			case STAFF:
				break;
			case SWORD:
				enterState(STATE.SWORD);
				break;
			default:
				break;
			
		}
	}
	
	private void resetVelocity() {
		Result<Component, NoSuchElementException> velResult = owner.getTraitByID(TRAIT.VELOCITY);
		if(velResult.is_ok()) {
			velResult.unwrap().set(new Velocity(0, 0));
		}
	}
	
	private void setVelocityController() {
		Vector2f newVel = new Vector2f(0,0);
		if(controller.buttonHeld(BUTTON.KEY_UP)) {
			facingDirection = DIRECTION.UP;
			newVel.y -= 1;
		}
		if(controller.buttonHeld(BUTTON.KEY_LEFT)) {
			facingDirection = DIRECTION.LEFT;
			newVel.x -= 1;
		}
		if(controller.buttonHeld(BUTTON.KEY_RIGHT)) {
			facingDirection = DIRECTION.RIGHT;
			newVel.x += 1;
		}
		if(controller.buttonHeld(BUTTON.KEY_DOWN)) {
			facingDirection = DIRECTION.DOWN;
			newVel.y += 1;
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
	
	@Override
	public AI_TYPE getType() {
		return AI_TYPE.PLAYER;
	}
	
}
