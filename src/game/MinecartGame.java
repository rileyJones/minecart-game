package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import color.ColorSelector;
import controller.Controller;
import states.GameState;

public class MinecartGame extends StateBasedGame {

	public Controller controller;
	@SuppressWarnings("unused")
	private ColorSelector color_selector;
	private static final int scale = 1;
	
	public MinecartGame(String name) {
		super(name);
		controller = new Controller(new int[] {
				Input.KEY_LEFT, Input.KEY_DOWN, Input.KEY_UP, Input.KEY_RIGHT, Input.KEY_Z, Input.KEY_X, Input.KEY_ENTER
		});
		color_selector = ColorSelector.get();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new GameState());
	}
	
	@Override
	protected void preUpdateState(GameContainer container, int delta) throws SlickException {
		super.preUpdateState(container, delta);
		controller.update(container.getInput(), delta);
	}
	
	@Override
	protected void preRenderState(GameContainer container, Graphics g) throws SlickException {
		super.preRenderState(container, g);
		g.scale(scale,scale);
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new MinecartGame("Minecart - Riley Jones"));
			app.setDisplayMode(888*scale, 672*scale, false);
			//app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
