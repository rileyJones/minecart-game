package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import ai.AI_TYPE;
import ai.Player;
import components.AI;
import components.Box;
import components.Position;
import components.Timer;
import components.Velocity;
import ecs.Component;
import ecs.Entity;
import ecs.Result;
import ecs.TRAIT;
import ecs.TwoBodySystem;
import etc.ptr;
import physics.Physics;

public class PlayerEnemyCollision extends TwoBodySystem {

	ptr<Integer> HP;
	
	public PlayerEnemyCollision(ptr<Integer> HP) {
		this.HP = HP;
	}

	@Override
	protected void update(Entity primary, Entity secondary, GameContainer container, StateBasedGame game, int delta) {
		Timer primaryPTimer = (Timer) primary.getParent().getTraitByID(TRAIT.TIMER).unwrap();
		Player player = (Player) primary.getTraitByID(TRAIT.AI).unwrap();
		if(primaryPTimer.isDone() && player.getState() != Player.STATE.SHIELD && player.getState() != Player.STATE.JUMP) {
			HP.V --;
			primaryPTimer.reset();
		}
		Result<Component, NoSuchElementException> primaryPVel = primary.getParent().getTraitByID(TRAIT.VELOCITY);
		if(primaryPVel.is_ok() && player.getState() != Player.STATE.JUMP) {
			Position primaryPos = (Position) primary.getTraitByID(TRAIT.POSITION).unwrap().getValue();
			
			Position secondaryPos = (Position) secondary.getTraitByID(TRAIT.POSITION).unwrap().getValue();
			Velocity secondaryPVel = (Velocity) secondary.getParent().getTraitByID(TRAIT.VELOCITY).unwrap();
			
			if(player.getState() == Player.STATE.SHIELD) {
				Physics.doOffsetLaunch(secondaryPos, secondaryPVel, primaryPos, 0.2f);
				Physics.doOffsetLaunch(primaryPos, (Velocity)primaryPVel.unwrap(), secondaryPos, 0.1f);
			} else {
				player.enterState(Player.STATE.WALKING);
				Physics.doOffsetLaunch(primaryPos, (Velocity)primaryPVel.unwrap(), secondaryPos, 0.25f);
			}
			
		}
		
	}

	@Override
	protected boolean test(Entity primary, Entity secondary) {
		Position primaryPos = (Position) primary.getTraitByID(TRAIT.POSITION).unwrap();
		Box primaryBox = (Box) primary.getTraitByID(TRAIT.BOX).unwrap();
		
		Vector2f primaryPosVec = ((Position)primaryPos.getValue()).getPos().unwrap();
		Rectangle primaryRect = ((Box)primaryBox.getValue()).getBox().unwrap();
		
		primaryRect.setX(primaryRect.getX()+primaryPosVec.x);
		primaryRect.setY(primaryRect.getY()+primaryPosVec.y);
		
		Position secondaryPos = (Position) secondary.getTraitByID(TRAIT.POSITION).unwrap();
		Box secondaryBox = (Box) secondary.getTraitByID(TRAIT.BOX).unwrap();
		
		Vector2f secondaryPosVec = ((Position)secondaryPos.getValue()).getPos().unwrap();
		Rectangle secondaryRect = ((Box)secondaryBox.getValue()).getBox().unwrap();
		
		secondaryRect.setX(secondaryRect.getX()+secondaryPosVec.x);
		secondaryRect.setY(secondaryRect.getY()+secondaryPosVec.y);
		
		Timer primaryPTimer = (Timer) primary.getParent().getTraitByID(TRAIT.TIMER).unwrap();
		
		return primaryRect.intersects(secondaryRect) && primary != secondary && primaryPTimer.isDone();
	}

	@Override
	protected boolean testPrimary(Entity primary) {
		Result<Component, NoSuchElementException> posTrait = primary.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> boxTrait = primary.getTraitByID(TRAIT.BOX);
		Result<Component, NoSuchElementException> velTrait = primary.getTraitByID(TRAIT.VELOCITY);
		Result<Component, NoSuchElementException> playerTrait = primary.getTraitByID(TRAIT.AI);
		
		Result<Component, NoSuchElementException> pTimerTrait = new Result<Component, NoSuchElementException>(new NoSuchElementException());
		if(primary.getParent() != null) {
			 pTimerTrait = primary.getParent().getTraitByID(TRAIT.TIMER);
		}
		
		return posTrait.is_ok() && boxTrait.is_ok() && velTrait.is_ok() && playerTrait.is_ok() &&
				(((AI)playerTrait.unwrap()).getType() == AI_TYPE.PLAYER) && pTimerTrait.is_ok();
	}

	@Override
	protected boolean testSecondary(Entity secondary) {
		Result<Component, NoSuchElementException> posTrait = secondary.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> boxTrait = secondary.getTraitByID(TRAIT.BOX);
		Result<Component, NoSuchElementException> velTrait = secondary.getTraitByID(TRAIT.VELOCITY);
		Result<Component, NoSuchElementException> enemyTrait = secondary.getTraitByID(TRAIT.AI);
		
		Result<Component, NoSuchElementException> pVelTrait = new Result<Component, NoSuchElementException>(new NoSuchElementException());
		if(secondary.getParent() != null) {
			 pVelTrait = secondary.getParent().getTraitByID(TRAIT.VELOCITY);
		}
		
		return posTrait.is_ok() && boxTrait.is_ok() && velTrait.is_ok() && enemyTrait.is_ok() &&
				(((AI)enemyTrait.unwrap()).getType() == AI_TYPE.ENEMY) && pVelTrait.is_ok();
	}

}
