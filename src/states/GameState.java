package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import components.*;
import ecs.*;
import systems.*;

public class GameState extends BasicGameState {
	
	Entity world;
	ECS_System[] systems;
	RenderSystem renderer;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		world = new Entity(new Component[] {});
		world.addChild(new Entity(new Component[] {
			new Position(100,100),
			new Box(-10,-10,20,20),
			new ColorC(Color.blue)
		}).addChild(new Entity(new Component[] {
			new Position(200,100),
			new Box(-10,-10,40,20),
			new ColorC(Color.red)	
		})));
		
		systems = new ECS_System[] {
		};
		renderer = new DebugDraw();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(Color.green);
		renderer.renderWorld(world, container, game, g);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		for(ECS_System s: systems) {
			s.updateWorld(world, container, game, delta);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
