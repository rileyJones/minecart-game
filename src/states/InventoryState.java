package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ai.Player;
import controller.BUTTON;
import game.MinecartGame;

public class InventoryState extends BasicGameState {

	TrueTypeFont bigFont;
	
	int pos;
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Serif", java.awt.Font.PLAIN, 30);
		bigFont =  new TrueTypeFont(awtFont, false);
		pos = 0;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		pos = 0;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		MinecartGame mGame = (MinecartGame) game;
		org.newdawn.slick.Font oldFont = g.getFont();
		g.setFont(bigFont);
		g.drawString("B: "+mGame.button_b.toString()+"   A: "+mGame.button_a.toString(), 24+12, 2*24-12);
		
		int o = 0;
		for(Player.ITEM I: Player.ITEM.values()) {
			if(I == Player.ITEM.GALE) {
				continue;
			}
			int x = o%2;
			int y = o/2+1;
			if(o == pos) {
				g.draw(new Rectangle(24+x*8*24, 2*24-12+y*4*24,7*24,2*24));
			}
			g.drawString(I.toString(), 24+12+x*8*24, 2*24-12+y*4*24);
			o++;
		}
		g.setFont(oldFont);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		MinecartGame mGame = (MinecartGame) game;
		if(mGame.controller.buttonPressed(BUTTON.KEY_PAUSE) &&
				!mGame.controller.buttonHeld(BUTTON.KEY_A) &&
				!mGame.controller.buttonHeld(BUTTON.KEY_B)) {
			game.enterState(0);
		}
		if(mGame.controller.buttonPressed(BUTTON.KEY_LEFT)) {
			pos = (pos+Player.ITEM.values().length-1-1)%(Player.ITEM.values().length-1);
		}
		if(mGame.controller.buttonPressed(BUTTON.KEY_RIGHT)) {
			pos = (pos+Player.ITEM.values().length-1+1)%(Player.ITEM.values().length-1);		
		}
		if(mGame.controller.buttonPressed(BUTTON.KEY_UP)) {
			pos = (pos+Player.ITEM.values().length-1-2)%(Player.ITEM.values().length-1);
		}
		if(mGame.controller.buttonPressed(BUTTON.KEY_DOWN)) {
			pos = (pos+Player.ITEM.values().length-1+2)%(Player.ITEM.values().length-1);
		}
		if(mGame.controller.buttonPressed(BUTTON.KEY_B)) {
			mGame.button_b = Player.ITEM.values()[pos];
		}
		if(mGame.controller.buttonPressed(BUTTON.KEY_A)) {
			mGame.button_a = Player.ITEM.values()[pos];
		}
		if(mGame.controller.buttonPressed(BUTTON.KEY_PAUSE)) {
			pos = Player.ITEM.values().length-1;
		}
	}

	@Override
	public int getID() {
		return 2;
	}

}
