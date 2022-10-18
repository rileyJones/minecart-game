package ai;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import components.AI;
import components.Position;
import components.Velocity;
import ecs.Component;
import ecs.Entity;
import ecs.Result;
import ecs.TRAIT;

public class Enemy extends AI{

	private Entity player;
	private float speed;
	
	
	public Enemy(Entity player, float speed) {
		this.player = player;
		this.speed = speed;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Result<Component, NoSuchElementException> playerPosR = player.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> ownerPosR = owner.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> ownerVelR = owner.getTraitByID(TRAIT.VELOCITY);
		if(playerPosR.is_ok() && ownerPosR.is_ok() && ownerVelR.is_ok()) {
			Position playerPos = (Position) playerPosR.unwrap();
			Position ownerPos = (Position) ownerPosR.unwrap();
			Velocity ownerVel = (Velocity) ownerVelR.unwrap();
			Vector2f posDifVec = ((Position)(playerPos.getValueDifference(ownerPos))).getPos().unwrap().normalise();
			ownerVel.set(new Velocity(speed * posDifVec.x,speed * posDifVec.y));
		}
	}

	@Override
	public Component clone() {
		return new Enemy(player, speed);
	}
	
	@Override
	public AI_TYPE getType() {
		return AI_TYPE.ENEMY;
	}

}
