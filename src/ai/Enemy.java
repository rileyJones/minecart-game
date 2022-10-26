package ai;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import components.AI;
import components.Box;
import components.Position;
import components.TileMap;
import components.TileMap.DIRECTION;
import components.Velocity;
import ecs.Component;
import ecs.Entity;
import ecs.Result;
import ecs.TRAIT;

public class Enemy extends AI{

	private Entity tilemap;
	private float speed;
	
	
	public Enemy(Entity tilemap, float speed) {
		this.tilemap = tilemap;
		this.speed = speed;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Result<Component, NoSuchElementException> tilemapPosR = tilemap.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> tilemapMapR = tilemap.getTraitByID(TRAIT.TILEMAP);
		Result<Component, NoSuchElementException> ownerPosR = owner.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> ownerVelR = owner.getTraitByID(TRAIT.VELOCITY);
		Result<Component, NoSuchElementException> ownerBoxR = owner.getTraitByID(TRAIT.BOX);
		if(tilemapPosR.is_ok() && tilemapMapR.is_ok() && ownerPosR.is_ok() && ownerVelR.is_ok() && ownerBoxR.is_ok()) {
			Position tilemapPos = (Position) tilemapPosR.unwrap();
			TileMap tilemapMap = (TileMap) tilemapMapR.unwrap(); 
			Position ownerPos = (Position) ownerPosR.unwrap();
			Velocity ownerVel = (Velocity) ownerVelR.unwrap();
			Vector2f ownerVelVec = ((Velocity)ownerVel.getInternalValue()).getVel().unwrap();
			
			Vector2f secPosVec = ((Position)tilemapPos.getValue()).getPos().unwrap();
			Vector2f pPosVec = ((Position)ownerPos.getValue()).getPos().unwrap();
			
			Vector2f moveDir = new Vector2f(0,0);
			
			Rectangle primaryRect = ((Box)ownerBoxR.unwrap().getValue()).getBox().unwrap();
			
			primaryRect.setX(primaryRect.getX()+pPosVec.x);
			primaryRect.setY(primaryRect.getY()+pPosVec.y);
			Rectangle secondaryRect = new Rectangle(
					secPosVec.x, 
					secPosVec.y, 
					tilemapMap.getTileWidth() * tilemapMap.getWidth(), 
					tilemapMap.getTileHeight() * tilemapMap.getHeight()
			);
			
			int minX = (int) Math.max(0, Math.floor((primaryRect.getMinX() - secondaryRect.getX()) / tilemapMap.getTileWidth()));
			int maxX = (int) Math.min(tilemapMap.getWidth(), Math.ceil((primaryRect.getMaxX() - secondaryRect.getX()) / tilemapMap.getTileWidth()));
			int minY = (int) Math.max(0, Math.floor((primaryRect.getMinY() - secondaryRect.getY()) / tilemapMap.getTileHeight()));
			int maxY = (int) Math.min(tilemapMap.getHeight(), Math.ceil((primaryRect.getMaxY() - secondaryRect.getY()) / tilemapMap.getTileHeight()));
			
			
			int startX;
			int endX;
			int dirX;
			int startY;
			int endY;
			int dirY;
			startX = minX;
			endX = maxX;
			dirX = 1;
			startY = minY;
			endY = maxY;
			dirY = 1;
			for(int x = startX; x != endX; x+=dirX) {
				for(int y = startY; y != endY; y+=dirY) {
					DIRECTION dir = tilemapMap.paths[x][y];
					switch(dir) {
						case DOWN:
							moveDir.add(new Vector2f(0,1).normalise());
							break;
						case DOWN_LEFT:
							moveDir.add(new Vector2f(-1,1).normalise());
							break;
						case DOWN_RIGHT:
							moveDir.add(new Vector2f(1,1).normalise());
							break;
						case LEFT:
							moveDir.add(new Vector2f(-1,0).normalise());
							break;
						case NONE:
							moveDir.add(new Vector2f(0,0).normalise());
							break;
						case RIGHT:
							moveDir.add(new Vector2f(1,0).normalise());
							break;
						case UP:
							moveDir.add(new Vector2f(0,-1).normalise());
							break;
						case UP_LEFT:
							moveDir.add(new Vector2f(-1,-1).normalise());
							break;
						case UP_RIGHT:
							moveDir.add(new Vector2f(1,-1).normalise());
							break;
						default:
							moveDir.add(new Vector2f(0,0).normalise());
							break;
					}
					
				}
				
			}
			moveDir.normalise();
			ownerVel.set(new Velocity(0.5f*ownerVelVec.x+0.5f*speed*moveDir.x,0.5f*ownerVelVec.y+0.5f*speed*moveDir.y));
		}
	}

	@Override
	public Component clone() {
		return new Enemy(tilemap, speed);
	}
	
	@Override
	public AI_TYPE getType() {
		return AI_TYPE.ENEMY;
	}

}
