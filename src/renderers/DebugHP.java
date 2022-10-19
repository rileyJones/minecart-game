package renderers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import ecs.Entity;
import ecs.RenderSystem;
import etc.ptr;

public class DebugHP extends RenderSystem {

	ptr<Integer> HP;
	TrueTypeFont bigFont;
	
	public DebugHP(ptr<Integer> HP) {
		this.HP = HP;
		java.awt.Font awtFont = new java.awt.Font("Serif", java.awt.Font.PLAIN, 60);
		bigFont =  new TrueTypeFont(awtFont, false);
	}
	
	@Override
	protected void render(Entity e, GameContainer container, StateBasedGame game, Graphics g) {
		org.newdawn.slick.Font oldFont = g.getFont();
		g.setFont(bigFont);
		g.drawString(HP.V.toString(), 24*19, 24-12);
		g.setFont(oldFont);
	}

	@Override
	protected boolean test(Entity e) {
		return true;
	}

}
