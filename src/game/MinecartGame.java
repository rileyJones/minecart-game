package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;

public class MinecartGame extends StateBasedGame {

	public MinecartGame(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new GameState());
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
