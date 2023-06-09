package ai;

import java.util.NoSuchElementException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import color.CNAME;
import color.CTYPE;
import color.ColorSelector;
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
	private int spiceTimer = -1;
	private int staffIndex = 3;
	
	private static final float MOVE_VEL = 0.1f; 
	private static final float SHIELD_VEL = 0.03f;
	
	Entity swordEntity = new Entity(new Component[] {
		new Position(0,0),
		new Box(-20,-20,40,40),
		new ColorC(Color.white),
		new Sword(),
		new Velocity(0,0)
	});
	Entity staffEntity = new Entity(new Component[] {
			new Position(0,0),
			new Box(0,0,0,0),
			new ColorC(Color.white),
			new Staff(staffIndex),
			new Velocity(0,0)
		});
	public Entity tunnelEntity = new Entity(new Component[] {
		new Tunnel(),
		new Position(0,0),
		new Box(0,0,0,0),
		new ColorC(Color.green)
	});
	
	public ITEM button_a;
	public ITEM button_b;
	
	STATE currentState;
	
	DIRECTION facingDirection;
	
	public enum ITEM {
		NOTHING,
		SWORD,
		SHIELD,
		FEATHER,
		STAFF,
		RAY,
		SPICES,
		GALE
	}
	
	public enum STATE {
		WALKING,
		SWORD,
		SHIELD,
		JUMP,
		STAFF,
		RAY
	}
	
	enum DIRECTION {
		UP,
		LEFT,
		RIGHT,
		DOWN
	}
	private boolean doGale = false;
	
	public Player(Controller controller, Entity world) {
		this.controller = controller;
		button_a = ITEM.SWORD;
		button_b = ITEM.SHIELD;
		currentState = STATE.WALKING;
		facingDirection = DIRECTION.DOWN;
		tunnelEntity.setParent(world);
	}
	
	public STATE getState() {
		return currentState;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		updateTimer(delta);
		spice_updateTimer(delta);
		resetVelocity();
		switch(currentState) {
			case SWORD:
				if(itemUsed(ITEM.SWORD)) {
					setVelocityController(0);
					enterState(STATE.SWORD);
				}
				if(timerIsDone()) enterState(STATE.WALKING);
				break;
			case WALKING:
				setVelocityController(MOVE_VEL);
				useItem();
				break;
			case SHIELD:
				if(!itemHeld(ITEM.SHIELD)) enterState(STATE.WALKING);
				setVelocityController(SHIELD_VEL);
				break;
			case JUMP:
				setVelocityController(MOVE_VEL);
				if(timerIsDone()) enterState(STATE.WALKING);
			case STAFF:
				if(timerIsDone()) enterState(STATE.WALKING);
			case RAY:
				if(timerIsDone()) enterState(STATE.WALKING);
			default:
				break;
		}
	}
	
	public void enterState(STATE S) {
		switch(currentState) {
			case SWORD:
				doGale = false;
				swordEntity.setParent(null);
				break;
			case WALKING:
				break;
			case SHIELD:
				break;
			case JUMP:
				break;
			case STAFF:
				staffEntity.setParent(null);
				break;
			case RAY:
				break;
			default:
				break;
			
		}
		switch(S) {
			case SWORD:
				if(doGale) {
					swordEntity.getTraitByID(TRAIT.COLOR).unwrap().set(new ColorC(
							ColorSelector.get().getColor(CNAME.TEAL, CTYPE.FADED, false, true)
							));
					((Sword)swordEntity.getTraitByID(TRAIT.AI).unwrap()).setGale(true);
				} else {
					swordEntity.getTraitByID(TRAIT.COLOR).unwrap().set(new ColorC(Color.lightGray));
					((Sword)swordEntity.getTraitByID(TRAIT.AI).unwrap()).setGale(false);
				}
				swordEntity.setParent(owner);
				switch(facingDirection) {
					case DOWN:
						swordEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(-16,16));
						break;
					case LEFT:
						swordEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(-16,-16));
						break;
					case RIGHT:
						swordEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(16,-16));
						break;
					case UP:
						swordEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(16,-16));
						break;
					default:
						break;
					
				}
				setTimer(200);
				break;
			case WALKING:
				break;
			case SHIELD:
				break;
			case JUMP:
				setTimer(1000);
				break;
			case STAFF:
				staffEntity.setParent(owner);
				staffIndex = (staffIndex+1)%4;
				((Staff)staffEntity.getTraitByID(TRAIT.AI).unwrap()).setIndex(staffIndex);
				((Staff)staffEntity.getTraitByID(TRAIT.AI).unwrap()).clearDidAct();
				switch(facingDirection) {
					case DOWN:
						staffEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(0,34f));
						break;
					case LEFT:
						staffEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(-34f,0));
						break;
					case RIGHT:
						staffEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(34f,0));
						break;
					case UP:
						staffEntity.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(0,-34f));
						break;
					default:
						break;
					
				}
				setTimer(500);
				break;
			case RAY:
				Vector2f pos = ((Position)owner.getTraitByID(TRAIT.POSITION).unwrap().getValue()).getPos().unwrap();
				Box tBox = ((Box)tunnelEntity.getTraitByID(TRAIT.BOX).unwrap());
				Tunnel tTun = ((Tunnel)tunnelEntity.getTraitByID(TRAIT.AI).unwrap());
				switch(facingDirection) {
					case DOWN:
						if(pos.y > 23*24) {
							return;
						}
						tTun.setIsVertical(true);
						tBox.set(new Box(
								(float) (24*Math.round((pos.x - 12)/24f)),
								(float) (24*Math.ceil((pos.y+24)/24f)),
								24,
								(float) (22*24-24*Math.ceil((pos.y-24)/24f))
							));
						break;
					case LEFT:
						if(pos.x < 6*24) {
							return;
						}
						tTun.setIsVertical(false);
						tBox.set(new Box(
								5*24, 
								(float) (24*Math.round((pos.y - 12)/24f)) , 
								(float) (24*Math.ceil((pos.x-24)/24f)-5*24-24), 
								24));
						break;
					case RIGHT:
						if(pos.x > 31*24) {
							return;
						}
						tTun.setIsVertical(false);
						tBox.set(new Box(
								(float) (24*Math.ceil((pos.x+24)/24f)), 
								(float) (24*Math.round((pos.y - 12)/24f)) , 
								(float) (32*24-24*Math.ceil((pos.x-24)/24f)-48), 
								24));
						break;
					case UP:
						if(pos.y < 10*24) {
							return;
						}
						tTun.setIsVertical(true);
						tBox.set(new Box(
								(float) (24*Math.round((pos.x - 12)/24f)),
								9*24,
								24,
								(float) (24*Math.ceil((pos.y-24)/24f)-9*24-24)
							));
						break;
					default:
						break;
				}
				setTimer(200);
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
	
	private boolean itemHeld(ITEM I) {
		if(controller.buttonHeld(BUTTON.KEY_A) && button_a == I) {
			return true;
		} else if(controller.buttonHeld(BUTTON.KEY_B) && button_b == I) {
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
	
	private void spice_setTimer(int time) {
		spiceTimer = time;
	}
	private boolean spice_updateTimer(int delta) {
		spiceTimer = Math.max(-1,spiceTimer-delta);
		return spiceTimer <= 0;
	}
	private boolean spice_timerIsDone() {
		return spiceTimer <= 0;
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
				enterState(STATE.JUMP);
				break;
			case NOTHING:
				break;
			case RAY:
				enterState(STATE.RAY);
				break;
			case SHIELD:
				enterState(STATE.SHIELD);
				break;
			case STAFF:
				enterState(STATE.STAFF);
				break;
			case GALE:
				doGale = true;
			case SWORD:
				enterState(STATE.SWORD);
				break;
			case SPICES:
				spice_setTimer(10000);
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
	
	private void setVelocityController(float speed) {
		if(!spice_timerIsDone()) {
			speed *= 2f;
		}
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
		
		newVel.x *= speed;
		newVel.y *= speed;
		
		Result<Component, NoSuchElementException> velResult = owner.getTraitByID(TRAIT.VELOCITY);
		if(velResult.is_ok()) {
			velResult.unwrap().set(new Velocity(newVel.x, newVel.y));
		}
	}

	@Override
	public Component clone() {
		return new Player(controller, null);
	}
	
	@Override
	public AI_TYPE getType() {
		return AI_TYPE.PLAYER;
	}
	
	public String bString() {
		return button_b.toString();
	}
	public String aString() {
		return button_a.toString();
	}
	
}
