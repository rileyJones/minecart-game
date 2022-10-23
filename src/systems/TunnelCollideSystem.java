package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import ai.AI_TYPE;
import ai.Tunnel;
import components.AI;
import components.Box;
import components.Position;
import components.Velocity;
import ecs.Component;
import ecs.Entity;
import ecs.Result;
import ecs.TRAIT;
import ecs.TwoBodySystem;
import physics.Physics;

public class TunnelCollideSystem extends TwoBodySystem {

	@Override
	protected void update(Entity primary, Entity secondary, GameContainer container, StateBasedGame game, int delta) {
		Position primaryPos = (Position) primary.getTraitByID(TRAIT.POSITION).unwrap();
		
		Vector2f primaryPosVec = ((Position)primaryPos.getValue()).getPos().unwrap();
		
		Vector2f primaryVelVec = ((Velocity)primary.getTraitByID(TRAIT.VELOCITY).unwrap().getValue()).getVel().unwrap();
		
		Rectangle primaryRect = new Rectangle(primaryPosVec.x, primaryPosVec.y, 0, 0);
				
		Position secondaryPos = (Position) secondary.getTraitByID(TRAIT.POSITION).unwrap();
		Box secondaryBox = (Box) secondary.getTraitByID(TRAIT.BOX).unwrap();
		
		Vector2f secondaryPosVec = ((Position)secondaryPos.getValue()).getPos().unwrap();
		Rectangle secondaryRect = ((Box)secondaryBox.getValue()).getBox().unwrap();
		
		secondaryRect.setX(secondaryRect.getX()+secondaryPosVec.x);
		secondaryRect.setY(secondaryRect.getY()+secondaryPosVec.y);
		
		
		Rectangle oldPrimaryRect = new Rectangle(primaryPosVec.x-delta*primaryVelVec.x, primaryPosVec.y-delta*primaryVelVec.y, 0, 0);
		
		if(oldPrimaryRect.intersects(secondaryRect)) {
			return;
		}
		
		Tunnel secondaryTun = (Tunnel) secondary.getTraitByID(TRAIT.AI).unwrap();
		switch(Physics.getBoxCollideDirection(primaryRect, primaryVelVec, secondaryRect, new Vector2f(0,0))) {
			case X_MINUS:
				if(secondaryTun.getIsVertical() && primaryPosVec.x > secondaryRect.getCenterX()) {
					return;
				}
				primaryPos.modify(new Position(-secondaryRect.getWidth()-0.001f,0));
				break;
			case X_PLUS:
				if(secondaryTun.getIsVertical() && primaryPosVec.x < secondaryRect.getCenterX()) {
					return;
				}
				primaryPos.modify(new Position(secondaryRect.getWidth()+0.001f,0));
				break;
			case Y_MINUS:
				if(!secondaryTun.getIsVertical() && primaryPosVec.y > secondaryRect.getCenterY()) {
					return;
				}
				primaryPos.modify(new Position(0,-secondaryRect.getHeight()-0.001f));
				break;
			case Y_PLUS:
				if(!secondaryTun.getIsVertical() && primaryPosVec.y < secondaryRect.getCenterY()) {
					return;
				}
				primaryPos.modify(new Position(0,secondaryRect.getHeight()+0.001f));
				break;
			default:
				break;
		}
		
	}

	@Override
	protected boolean test(Entity primary, Entity secondary) {
		Position primaryPos = (Position) primary.getTraitByID(TRAIT.POSITION).unwrap();
		
		Vector2f primaryPosVec = ((Position)primaryPos.getValue()).getPos().unwrap();
		
		Rectangle primaryRect = new Rectangle(primaryPosVec.x, primaryPosVec.y, 0, 0);
		
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
		Result<Component, NoSuchElementException> velTrait = primary.getTraitByID(TRAIT.VELOCITY);
		Result<Component, NoSuchElementException> aiTrait = primary.getTraitByID(TRAIT.AI);
		return posTrait.is_ok() && boxTrait.is_ok() && velTrait.is_ok() && aiTrait.is_ok() && 
				(((AI)aiTrait.unwrap()).getType() == AI_TYPE.PLAYER || ((AI)aiTrait.unwrap()).getType() == AI_TYPE.ENEMY);
	}

	@Override
	protected boolean testSecondary(Entity secondary) {
		Result<Component, NoSuchElementException> posTrait = secondary.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> boxTrait = secondary.getTraitByID(TRAIT.BOX);
		Result<Component, NoSuchElementException> aiTrait = secondary.getTraitByID(TRAIT.AI);
		return posTrait.is_ok() && boxTrait.is_ok() && aiTrait.is_ok() &&
				((AI)aiTrait.unwrap()).getType() == AI_TYPE.TUNNEL;
	}

}
