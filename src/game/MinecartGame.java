package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import controller.Controller;
import states.GameState;

public class MinecartGame extends StateBasedGame {

	public static final int KEY_LEFT  = 0;
	public static final int KEY_DOWN  = 1;
	public static final int KEY_UP    = 2;
	public static final int KEY_RIGHT = 3;
	public static final int KEY_B     = 4;
	public static final int KEY_A     = 5;
	public static final int KEY_PAUSE = 6;
	public Controller controller;
	
	public MinecartGame(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		controller = new Controller(new int[] {
				Input.KEY_LEFT, Input.KEY_DOWN, Input.KEY_UP, Input.KEY_RIGHT, Input.KEY_Z, Input.KEY_X, Input.KEY_ENTER
		});
		addState(new GameState());
	}
	
	@Override
	protected void preUpdateState(GameContainer container, int delta) throws SlickException {
		super.preUpdateState(container, delta);
		controller.update(container.getInput(), delta);
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new MinecartGame("Minecart - Riley Jones"));
			app.setDisplayMode(888, 672, false);
			//app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
