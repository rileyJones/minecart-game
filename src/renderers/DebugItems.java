package renderers;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import ai.AI_TYPE;
import ai.Player;
import components.AI;
import ecs.Component;
import ecs.Entity;
import ecs.RenderSystem;
import ecs.Result;
import ecs.TRAIT;

public class DebugItems extends RenderSystem {

	TrueTypeFont bigFont;
	
	public DebugItems() {
		java.awt.Font awtFont = new java.awt.Font("Serif", java.awt.Font.PLAIN, 30);
		bigFont =  new TrueTypeFont(awtFont, false);
	}
	
	@Override
	protected void render(Entity e, GameContainer container, StateBasedGame game, Graphics g) {
		Player player = (Player) e.getTraitByID(TRAIT.AI).unwrap();
		
		org.newdawn.slick.Font oldFont = g.getFont();
		g.setFont(bigFont);
		g.drawString("B: "+player.bString()+"   A: "+player.aString(), 24+12, 2*24-12);
		g.setFont(oldFont);
	}

	@Override
	protected boolean test(Entity e) {
		Result<Component, NoSuchElementException> aiTrait = e.getTraitByID(TRAIT.AI);
		return aiTrait.is_ok() && ((AI)aiTrait.unwrap()).getType() == AI_TYPE.PLAYER;
	}

}

