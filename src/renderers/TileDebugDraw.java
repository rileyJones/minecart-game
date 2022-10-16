package renderers;

import java.util.NoSuchElementException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import color.CNAME;
import color.CTYPE;
import color.ColorSelector;
import components.Position;
import components.TileMap;
import ecs.Component;
import ecs.Entity;
import ecs.RenderSystem;
import ecs.Result;
import ecs.TRAIT;

public class TileDebugDraw extends RenderSystem{

	@Override
	protected void render(Entity e, GameContainer container, StateBasedGame game, Graphics g) {
		TileMap map = (TileMap) e.getTraitByID(TRAIT.TILEMAP).unwrap();
		Position pos = (Position) e.getTraitByID(TRAIT.POSITION).unwrap();
		Vector2f posVec = ((Position)pos.getValue()).getPos().unwrap();
		Color oldColor = g.getColor();
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				Color color;
				switch(map.getTile(x, y)) {
					case BLOCK:
						color = ColorSelector.get().getColor(CNAME.RED, CTYPE.LIGHT, true, true);
						break;
					case BUTTON:
						color = ColorSelector.get().getColor(CNAME.GREY, CTYPE.LIGHT, true, true);
						break;
					case EMPTY:
						color = g.getBackground();
						break;
					case GROUND:
						//color = ColorSelector.get().getColor(CNAME.ORANGE, CTYPE.LIGHT, true, true);
						color = ColorSelector.get().getColor(CNAME.ORANGE, CTYPE.DARK, false, true);
						break;
					case HOLE:
						color = ColorSelector.get().getColor(CNAME.GREY, CTYPE.DARK, true, false);
						break;
					case SPAWN_ENEMY:
						color = ColorSelector.get().getColor(CNAME.ORANGE, CTYPE.LIGHT, false, true);
						break;
					case SPAWN_PLAYER:
						color = ColorSelector.get().getColor(CNAME.ORANGE, CTYPE.BRIGHT, false, true);
						break;
					case TRACK_DL:
					case TRACK_DR:
					case TRACK_HOR:
					case TRACK_UL:
					case TRACK_UR:
					case TRACK_VERT:
						color = ColorSelector.get().getColor(CNAME.CYAN, CTYPE.LIGHT, false, true);
						break;
					case TRACK_STOP:
						color = ColorSelector.get().getColor(CNAME.GREY, CTYPE.LIGHT, true, true);
						break;
					case TRACK_SWAP:
						color = ColorSelector.get().getColor(CNAME.CYAN, CTYPE.LIGHT, true, false);
						break;
					case UI:
						color = ColorSelector.get().getColor(CNAME.ORANGE, CTYPE.BRIGHT, false, false);
						break;
					default:
						color = g.getBackground();
						break;
				}
				g.setColor(color);
				g.fill(new Rectangle(posVec.x + x*map.getTileWidth(), posVec.y + y*map.getTileHeight(),
						map.getTileWidth(), map.getTileHeight()));
			}
		}
		g.setColor(oldColor);
	}

	@Override
	protected boolean test(Entity e) {
		Result<Component, NoSuchElementException> mapTrait = e.getTraitByID(TRAIT.TILEMAP);
		Result<Component, NoSuchElementException> posTrait = e.getTraitByID(TRAIT.POSITION);
		return mapTrait.is_ok() && posTrait.is_ok();
	}

}
