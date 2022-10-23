package renderers;

import java.util.NoSuchElementException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import ai.AI_TYPE;
import ai.Player;
import components.AI;
import components.Box;
import components.Position;
import components.ColorC;
import ecs.Component;
import ecs.Entity;
import ecs.RenderSystem;
import ecs.Result;
import ecs.TRAIT;

public class DebugDraw extends RenderSystem {

	@Override
	protected void render(Entity e, GameContainer container, StateBasedGame game, Graphics g) {
		Vector2f pos = ((Position)(e.getTraitByID(TRAIT.POSITION).unwrap().getValue())).getPos().unwrap();
		Rectangle rect = ((Box)(e.getTraitByID(TRAIT.BOX).unwrap().getValue())).getBox().unwrap();
		Color col = ((ColorC)(e.getTraitByID(TRAIT.COLOR).unwrap().getValue())).getColor().unwrap();
		Color oldColor = g.getColor();
		if(e.getTraitByID(TRAIT.AI).is_ok() && ((AI)e.getTraitByID(TRAIT.AI).unwrap()).getType() == AI_TYPE.PLAYER) {
			if(((Player)e.getTraitByID(TRAIT.AI).unwrap()).getState() == Player.STATE.JUMP) {
				rect.setY(rect.getY() - 20);
			}
		}
		
		g.setColor(col);
		g.fill(new Rectangle(pos.x+rect.getX(), pos.y + rect.getY(), rect.getWidth(), rect.getHeight()));
		g.setColor(oldColor);
		g.fill(new Circle(pos.x, pos.y, 2));
	}
	
	@Override
	protected boolean test(Entity e) {
		Result<Component, NoSuchElementException> posTrait = e.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> boxTrait = e.getTraitByID(TRAIT.BOX);
		Result<Component, NoSuchElementException> colorTrait = e.getTraitByID(TRAIT.COLOR);
		return posTrait.is_ok() && boxTrait.is_ok() && colorTrait.is_ok();
	}

}
