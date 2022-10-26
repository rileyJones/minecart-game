package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import ai.Heart;
import components.Box;
import components.ColorC;
import components.Position;
import components.TileMap;
import ecs.Component;
import ecs.Entity;
import ecs.OneBodySystem;
import ecs.Result;
import ecs.TRAIT;
import etc.ptr;

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
		if(waveCount.V%25 == 0) {
			for(int x = 0; x < tilemap.getWidth(); x++) {
				for(int y = 0; y < tilemap.getHeight(); y++) {
					if(tilemap.getTile(x,y) == TileMap.TILE.SPAWN_PLAYER) {
						Entity heart_clone = heart_prototype.clone();
						heart_clone.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(
								tPos.x+(x+1/2f)*tilemap.getTileWidth(), tPos.y+(y+1/2f)*tilemap.getTileHeight()
								));
						heart_clone.setParent(e);
					}
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

}
