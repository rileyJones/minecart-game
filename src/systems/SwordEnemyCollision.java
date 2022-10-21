package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import ai.AI_TYPE;
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
import physics.Physics;

public class SwordEnemyCollision extends TwoBodySystem {

	

	@Override
	protected void update(Entity primary, Entity secondary, GameContainer container, StateBasedGame game, int delta) {
		
		Position secondaryPos = (Position)secondary.getTraitByID(TRAIT.POSITION).unwrap();
		Velocity secondaryVel = (Velocity) secondary.getParent().getTraitByID(TRAIT.VELOCITY).unwrap();
		Timer secondaryTimer = (Timer) secondary.getParent().getTraitByID(TRAIT.TIMER).unwrap();
		
		Position primaryPPos = (Position)primary.getParent().getTraitByID(TRAIT.POSITION).unwrap();
		if(secondaryTimer.isDone()) {
			Physics.doOffsetLaunch(secondaryPos, secondaryVel, primaryPPos, 0.5f);
			secondaryTimer.reset();
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
		
		return primaryRect.intersects(secondaryRect) && primary != secondary;
	}

	@Override
	protected boolean testPrimary(Entity primary) {
		Result<Component, NoSuchElementException> posTrait = primary.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> boxTrait = primary.getTraitByID(TRAIT.BOX);
		Result<Component, NoSuchElementException> playerTrait = primary.getTraitByID(TRAIT.AI);
		
		Result<Component, NoSuchElementException> pPosTrait = new Result<Component, NoSuchElementException>(new NoSuchElementException());
		if(primary.getParent() != null) {
			pPosTrait = primary.getParent().getTraitByID(TRAIT.POSITION);
		}
		
		return posTrait.is_ok() && boxTrait.is_ok() && playerTrait.is_ok() &&
				(((AI)playerTrait.unwrap()).getType() == AI_TYPE.SWORD) && pPosTrait.is_ok();
	}

	@Override
	protected boolean testSecondary(Entity secondary) {
		Result<Component, NoSuchElementException> posTrait = secondary.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> boxTrait = secondary.getTraitByID(TRAIT.BOX);
		Result<Component, NoSuchElementException> enemyTrait = secondary.getTraitByID(TRAIT.AI);
		
		Result<Component, NoSuchElementException> pvelTrait = new Result<Component, NoSuchElementException>(new NoSuchElementException());
		Result<Component, NoSuchElementException> pposTrait = new Result<Component, NoSuchElementException>(new NoSuchElementException());
		Result<Component, NoSuchElementException> pTimTrait = new Result<Component, NoSuchElementException>(new NoSuchElementException());
		if(secondary.getParent() != null) {
			pvelTrait = secondary.getParent().getTraitByID(TRAIT.POSITION);
			pposTrait = secondary.getParent().getTraitByID(TRAIT.VELOCITY);
			pTimTrait = secondary.getParent().getTraitByID(TRAIT.TIMER);
		}
		
		return posTrait.is_ok() && boxTrait.is_ok() && enemyTrait.is_ok() &&
				(((AI)enemyTrait.unwrap()).getType() == AI_TYPE.ENEMY) && pvelTrait.is_ok() && 
				pposTrait.is_ok() && pTimTrait.is_ok();
	}

}
