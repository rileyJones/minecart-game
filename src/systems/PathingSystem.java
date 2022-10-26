package systems;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import ai.AI_TYPE;
import ai.Player;
import ai.Tunnel;
import components.AI;
import components.Box;
import components.Position;
import components.TileMap;
import components.TileMap.DIRECTION;
import components.TileMap.TILE;
import ecs.Component;
import ecs.Entity;
import ecs.Result;
import ecs.TRAIT;
import ecs.TwoBodySystem;
import etc.tup2;

public class PathingSystem extends TwoBodySystem {

	
	@Override
	protected void update(Entity primary, Entity secondary, GameContainer container, StateBasedGame game, int delta) {
		TileMap tilemap = (TileMap) secondary.getTraitByID(TRAIT.TILEMAP).unwrap();
		Position secondaryPos = (Position) secondary.getTraitByID(TRAIT.POSITION).unwrap();
		Vector2f secPosVec = ((Position)secondaryPos.getValue()).getPos().unwrap();
		
		for(int x = 0; x < tilemap.getWidth(); x++) {
			for(int y = 0; y < tilemap.getHeight(); y++) {
				tilemap.distance[x][y] = Double.POSITIVE_INFINITY;
				tilemap.paths[x][y] = TileMap.DIRECTION.NONE;
			}
		}
		Position primaryPos = (Position) primary.getTraitByID(TRAIT.POSITION).unwrap();
		
		Vector2f pPosVec = ((Position)primaryPos.getValue()).getPos().unwrap();
		 
		Queue<tup2<Integer,Integer>> tiles = new LinkedList<tup2<Integer,Integer>>();
		tiles.add(new tup2<Integer,Integer>((int)Math.floor((pPosVec.x-secPosVec.x)/tilemap.getTileWidth()),(int)Math.floor((pPosVec.y-secPosVec.y)/tilemap.getTileHeight())));
		tilemap.distance[tiles.peek()._1][tiles.peek()._2] = 0;
		Player player = (Player) primary.getTraitByID(TRAIT.AI).unwrap();
		Tunnel tunnel = (Tunnel) player.tunnelEntity.getTraitByID(TRAIT.AI).unwrap();
		Vector2f tunPos = ((Position)player.tunnelEntity.getTraitByID(TRAIT.POSITION).unwrap().getValue()).getPos().unwrap();
		Rectangle tunRec = ((Box)player.tunnelEntity.getTraitByID(TRAIT.BOX).unwrap().getValue()).getBox().unwrap();
		tunRec.setX(tunRec.getX()+tunPos.x);
		tunRec.setY(tunRec.getY()+tunPos.y);
		int i = 0;
		while(tiles.peek() != null) {
			i++;
			tup2<Integer,Integer> pos = tiles.poll();
			for(int X = -1; X <= 1; X++) {
				for(int Y = -1; Y <= 1; Y++) {
					int x = X;
					int y = Y;
					if(x == 0 && y == 0) continue;
					Vector2f tilePos = new Vector2f(secPosVec.x+(pos._1+x+1/2f)*tilemap.getTileWidth(), secPosVec.y+(pos._2+y+1/2f)*tilemap.getTileHeight());
					Vector2f oldTilePos = new Vector2f(secPosVec.x+(pos._1+1/2f)*tilemap.getTileWidth(), secPosVec.y+(pos._2+1/2f)*tilemap.getTileHeight());
					if(x != 0 && y != 0) {
						if(!shouldAddTile(tilemap,pos._1+x,pos._2, -1) && !shouldAddTile(tilemap,pos._1,pos._2+y, -1)) {
							continue;
						}
						//if(tunRec.contains(oldTilePos.x + x*tilemap.getTileWidth(),oldTilePos.y) || 
						//		tunRec.contains(oldTilePos.x,oldTilePos.y + y*tilemap.getTileHeight())) {
						//	continue;
						//}
					}
					
					
					if(x != 0 && y == 0 && !tunnel.getIsVertical()) {
						
						if(tunRec.contains(tilePos.x,tilePos.y) && !tunRec.contains(oldTilePos.x,oldTilePos.y)) {
							x += x*tunRec.getWidth()/tilemap.getTileWidth();
						} else if(tunRec.contains(oldTilePos.x,oldTilePos.y)) {
							continue;
						}
					} else if(y != 0 && x == 0 && tunnel.getIsVertical()) {
						if(tunRec.contains(tilePos.x,tilePos.y) && !tunRec.contains(oldTilePos.x,oldTilePos.y)) {
							y += y+tunRec.getHeight()/tilemap.getTileHeight();
						} else if(tunRec.contains(oldTilePos.x,oldTilePos.y)) {
							continue;
						}
					}
					if(tunRec.contains(oldTilePos.x,oldTilePos.y) && i != 1) {
						if(tunRec.contains(tilePos.x,tilePos.y)) {
							
						} else {
							if(tunnel.getIsVertical()) {
								x = 0;
							} else {
								y = 0;
							}
						}
					}
					if(shouldAddTile(tilemap, pos._1+x, pos._2+y, tilemap.distance[pos._1][pos._2]+Math.sqrt(Math.abs(x)+Math.abs(y)))) {
						tiles.add(new tup2<Integer,Integer>(pos._1+x, pos._2+y));
						tilemap.distance[pos._1+x][pos._2+y] = tilemap.distance[pos._1][pos._2]+Math.sqrt(Math.abs(x)+Math.abs(y));
						int _x = 0;
						if(x < 0) {
							_x = -1;
						} else if(x > 0) {
							_x = 1;
						}
						int _y = 0;
						if(y < 0) {
							_y = -1;
						} else if(y > 0) {
							_y = 1;
						}
						switch(_x) {
							case -1:
								switch(_y) {
									case -1:
										tilemap.paths[pos._1+x][pos._2+y] = DIRECTION.DOWN_RIGHT;
										break;
									case 0:
										tilemap.paths[pos._1+x][pos._2+y] = DIRECTION.RIGHT;
										break;
									case 1:
										tilemap.paths[pos._1+x][pos._2+y] = DIRECTION.UP_RIGHT;
										break;
								}
								break;
							case 0:
								switch(_y) {
									case -1:
										tilemap.paths[pos._1+x][pos._2+y] = DIRECTION.DOWN;
										break;
									case 0:
										System.err.println("SHOULDN'T OCCUR");
										break;
									case 1:
										tilemap.paths[pos._1+x][pos._2+y] = DIRECTION.UP;
										break;
								}
								break;
							case 1:
								switch(_y) {
									case -1:
										tilemap.paths[pos._1+x][pos._2+y] = DIRECTION.DOWN_LEFT;
										break;
									case 0:
										tilemap.paths[pos._1+x][pos._2+y] = DIRECTION.LEFT;
										break;
									case 1:
										tilemap.paths[pos._1+x][pos._2+y] = DIRECTION.UP_LEFT;
										break;
								}
								break;
						}
					}
				}
			}
		}
	}
	boolean shouldAddTile(TileMap tilemap, int x, int y, double newDistance) {
		if(x <= -1 || x >= tilemap.getWidth() || y <= -1 || y >= tilemap.getHeight()) {
			return false;
		}
		TILE t = tilemap.getTile(x,y);
		double oldDistance = tilemap.distance[x][y];
		boolean pathWithin = true;
		switch(t) {
			case BLOCK:
				pathWithin &= false;
				break;
			case BLOCK_SPECIAL_0:
				pathWithin &= false;
				break;
			case BLOCK_SPECIAL_1:
				pathWithin &= false;
				break;
			case BLOCK_SPECIAL_2:
				pathWithin &= false;
				break;
			case BLOCK_SPECIAL_3:
				pathWithin &= false;
				break;
			case BUTTON:
				pathWithin &= true;
				break;
			case EMPTY:
				pathWithin &= true;
				break;
			case GROUND:
				pathWithin &= true;
				break;
			case HOLE:
				pathWithin &= false;
				break;
			case SPAWN_ENEMY:
				pathWithin &= true;
				break;
			case SPAWN_PLAYER:
				pathWithin &= true;
				break;
			case TRACK_DL:
				pathWithin &= true;
				break;
			case TRACK_DR:
				pathWithin &= true;
				break;
			case TRACK_HOR:
				pathWithin &= true;
				break;
			case TRACK_STOP:
				pathWithin &= true;
				break;
			case TRACK_SWAP:
				pathWithin &= true;
				break;
			case TRACK_UL:
				pathWithin &= true;
				break;
			case TRACK_UR:
				pathWithin &= true;
				break;
			case TRACK_VERT:
				pathWithin &= true;
				break;
			case UI:
				pathWithin &= false;
				break;
			default:
				pathWithin &= false;
				break;
		}
		return pathWithin && newDistance < oldDistance;
	}

	@Override
	protected boolean test(Entity primary, Entity secondary) {
		Position primaryPos = (Position) primary.getTraitByID(TRAIT.POSITION).unwrap();
		
		Vector2f primaryPosVec = ((Position)primaryPos.getValue()).getPos().unwrap();
		Rectangle primaryRect = new Rectangle(0,0,0,0);
		
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
		Result<Component, NoSuchElementException> pPos = primary.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> pAI = primary.getTraitByID(TRAIT.AI);
		return pPos.is_ok() && pAI.is_ok() && ((AI)pAI.unwrap()).getType() == AI_TYPE.PLAYER;
	}

	@Override
	protected boolean testSecondary(Entity secondary) {
		Result<Component, NoSuchElementException> tileMapTrait = secondary.getTraitByID(TRAIT.TILEMAP);
		Result<Component, NoSuchElementException> posTrait = secondary.getTraitByID(TRAIT.POSITION);
		return tileMapTrait.is_ok() && posTrait.is_ok();
	}

}
