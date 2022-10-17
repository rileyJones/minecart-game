package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import components.Box;
import components.Position;
import components.TileMap;
import components.Velocity;
import ecs.Component;
import ecs.Entity;
import ecs.Result;
import ecs.TRAIT;
import ecs.TwoBodySystem;
import physics.Physics;

public class TileMapCollision extends TwoBodySystem {

	@Override
	protected void update(Entity primary, Entity secondary, GameContainer container, StateBasedGame game, int delta) {
		Position primaryPos = (Position) primary.getTraitByID(TRAIT.POSITION).unwrap();
		Box primaryBox = (Box) primary.getTraitByID(TRAIT.BOX).unwrap();
		Velocity primaryVel = (Velocity) primary.getTraitByID(TRAIT.VELOCITY).unwrap();
		
		Vector2f primaryPosVec = ((Position)primaryPos.getValue()).getPos().unwrap();
		Rectangle primaryRect = ((Box)primaryBox.getValue()).getBox().unwrap();
		
		primaryRect.setX(primaryRect.getX()+primaryPosVec.x);
		primaryRect.setY(primaryRect.getY()+primaryPosVec.y);
		
		Position secondaryPos = (Position) secondary.getTraitByID(TRAIT.POSITION).unwrap();
		TileMap secondaryTileMap = (TileMap) secondary.getTraitByID(TRAIT.TILEMAP).unwrap();
		
		Vector2f secondaryPosVec = ((Position)secondaryPos.getValue()).getPos().unwrap();
		Rectangle secondaryRect = new Rectangle(
				secondaryPosVec.x, 
				secondaryPosVec.y, 
				secondaryTileMap.getTileWidth() * secondaryTileMap.getWidth(), 
				secondaryTileMap.getTileHeight() * secondaryTileMap.getHeight()
		);
		
		for(int x = (int) Math.max(0, Math.floor((primaryRect.getMinX() - secondaryRect.getX()) / secondaryTileMap.getTileWidth()));
				x < Math.min(secondaryTileMap.getWidth(), Math.ceil((primaryRect.getMaxX() - secondaryRect.getX()) / secondaryTileMap.getTileWidth()));
				x++) {
			for(int y = (int) Math.max(0, Math.floor((primaryRect.getMinY() - secondaryRect.getY()) / secondaryTileMap.getTileHeight()));
					y < Math.min(secondaryTileMap.getHeight(), Math.ceil((primaryRect.getMaxY() - secondaryRect.getY()) / secondaryTileMap.getTileHeight()));
					y++) {
				switch(secondaryTileMap.getTile(x, y)) {
					case BLOCK:
						Box secondaryBox = new Box(x*secondaryTileMap.getTileWidth(), y*secondaryTileMap.getTileHeight(), secondaryTileMap.getTileWidth(), secondaryTileMap.getTileHeight());
						Physics.doBoxClip(primaryPos, primaryBox, primaryVel, secondaryPos, secondaryBox, new Velocity(0,0), delta);
						break;
					case BUTTON:
						break;
					case EMPTY:
						break;
					case GROUND:
						break;
					case HOLE:
						break;
					case SPAWN_ENEMY:
						break;
					case SPAWN_PLAYER:
						break;
					case TRACK_DL:
						break;
					case TRACK_DR:
						break;
					case TRACK_HOR:
						break;
					case TRACK_STOP:
						break;
					case TRACK_SWAP:
						break;
					case TRACK_UL:
						break;
					case TRACK_UR:
						break;
					case TRACK_VERT:
						break;
					case UI:
						break;
					default:
						break;
					
				}
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
		TileMap secondaryTileMap = (TileMap) secondary.getTraitByID(TRAIT.TILEMAP).unwrap();
		
		Vector2f secondaryPosVec = ((Position)secondaryPos.getValue()).getPos().unwrap();
		Rectangle secondaryRect = new Rectangle(
				secondaryPosVec.x, 
				secondaryPosVec.y, 
				secondaryTileMap.getTileWidth() * secondaryTileMap.getWidth(), 
				secondaryTileMap.getTileHeight() * secondaryTileMap.getHeight()
		);
		
		return primaryRect.intersects(secondaryRect);
	}

	@Override
	protected boolean testPrimary(Entity primary) {
		Result<Component, NoSuchElementException> posTrait = primary.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> boxTrait = primary.getTraitByID(TRAIT.BOX);
		Result<Component, NoSuchElementException> velTrait = primary.getTraitByID(TRAIT.VELOCITY);
		return posTrait.is_ok() && boxTrait.is_ok() && velTrait.is_ok();
	}

	@Override
	protected boolean testSecondary(Entity secondary) {
		Result<Component, NoSuchElementException> tileMapTrait = secondary.getTraitByID(TRAIT.TILEMAP);
		Result<Component, NoSuchElementException> posTrait = secondary.getTraitByID(TRAIT.POSITION);
		return tileMapTrait.is_ok() && posTrait.is_ok();
	}

}
