package systems;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import ai.Heart;
import components.Box;
import components.ColorC;
import components.Position;
import components.TileMap;
import components.TileMap.TILE;
import ecs.Component;
import ecs.Entity;
import ecs.OneBodySystem;
import ecs.Result;
import ecs.TRAIT;
import etc.ptr;
import etc.tup2;

public class AfterWaveSystem extends OneBodySystem {

	Entity heart_prototype = new Entity(new Component[] {
			new Position(0,0),
			new Box(-5,-5,10,10),
			new ColorC(Color.red),
			new Heart()
	});
	ptr<Integer> waveCount;
	public AfterWaveSystem(ptr<Integer> waveCount) {
		this.waveCount = waveCount;
	}
	
	@Override
	protected void update(Entity e, GameContainer container, StateBasedGame game, int delta) {
		waveCount.V++;
		Vector2f tPos = ((Position)e.getTraitByID(TRAIT.POSITION).unwrap().getValue()).getPos().unwrap();
		TileMap tilemap = (TileMap)e.getTraitByID(TRAIT.TILEMAP).unwrap();
		
		for(int x = 0; x < tilemap.getWidth(); x++) {
			for(int y = 0; y < tilemap.getHeight(); y++) {
				if(tilemap.getTile(x,y) == TileMap.TILE.SPAWN_PLAYER) {
					if(waveCount.V%25 == 0) {
						Entity heart_clone = heart_prototype.clone();
						heart_clone.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(
								tPos.x+(x+1/2f)*tilemap.getTileWidth(), tPos.y+(y+1/2f)*tilemap.getTileHeight()
								));
						heart_clone.setParent(e);
					}
					tryAdd(x,y,tilemap);
					tryAdd(x,y,tilemap);
					tryAdd(x,y,tilemap);
					tryAdd(x,y,tilemap);
				}
			}
		}
	}

	@Override
	protected boolean test(Entity e) {
		Result<Component, NoSuchElementException> tileMapTrait = e.getTraitByID(TRAIT.TILEMAP);
		Result<Component, NoSuchElementException> posTrait = e.getTraitByID(TRAIT.POSITION);
		return tileMapTrait.is_ok() && posTrait.is_ok() && waveCount.V%5 == 4;
	}
	
	int getArea(int x, int y, TileMap tilemap) {
		int area = 0;
		boolean[][] within = new boolean[tilemap.getWidth()][tilemap.getHeight()];
		Queue<tup2<Integer,Integer>> areaQueue = new LinkedList<tup2<Integer,Integer>>();
		areaQueue.add(new tup2<Integer,Integer>(x,y));
		within[x][y] = true;
		while(areaQueue.peek() != null) {
			area++;
			tup2<Integer,Integer> val = areaQueue.poll();
			int x2 = val._1, y2 = val._2;
			int[] xs = new int[] {x2+1,x2,x2-1,x2};
			int[] ys = new int[] {y2,y2-1,y2,y2+1};
			for(int i = 0; i < 4; i++) {
				int x3 = xs[i];
				int y3 = ys[i];
				boolean shouldAdd = true;
				switch(tilemap.getTile(x3,y3)) {
					case BLOCK:
						shouldAdd = false;
						break;
					case BLOCK_SPECIAL_0:
						break;
					case BLOCK_SPECIAL_1:
						break;
					case BLOCK_SPECIAL_2:
						break;
					case BLOCK_SPECIAL_3:
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
				if(shouldAdd && !within[x3][y3]) {
					areaQueue.add(new tup2<Integer,Integer>(x3,y3));
					within[x3][y3] = true;
				}
			}
			
			
		}
		return area;
	}
	void tryAdd(int x, int y, TileMap tilemap) {
		int oldArea = getArea(x, y, tilemap);
		int oldX = (int) (Math.random() * tilemap.getWidth());
		int oldY = (int) (Math.random() * tilemap.getHeight());
		TILE oldValue = tilemap.getTile(oldX, oldY);
		if(oldX == x && oldY == y) {
			return;
		}
		if(oldValue == TILE.GROUND || oldValue == TILE.BLOCK_SPECIAL_0 || oldValue == TILE.BLOCK_SPECIAL_1
				|| oldValue == TILE.BLOCK_SPECIAL_2 || oldValue == TILE.BLOCK_SPECIAL_3) {
		
			tilemap.setTile(oldX, oldY, TILE.BLOCK);
			int newArea = getArea(x,y,tilemap);
			if(newArea + 1 == oldArea) {
			} else {
				tilemap.setTile(oldX,oldY,oldValue);
			}
		} else {
		}
	}

}
