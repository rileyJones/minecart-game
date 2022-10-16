package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ai.Player;
import components.*;
import ecs.*;
import game.MinecartGame;
import renderers.DebugDraw;
import renderers.TileDebugDraw;
import systems.*;

public class GameState extends BasicGameState {
	
	Entity world;
	ECS_System[] systems;
	RenderSystem[] renderers;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		world = new Entity(new Component[] {});
		world.addChild(new Entity(new Component[] {
			new Position(100,100),
			new Box(-10,-10,20,20),
			new ColorC(Color.blue),
			new Player(((MinecartGame)game).controller),
			new Velocity(0,0)
		}).addChild(new Entity(new Component[] {
			new Position(200,100),
			new Box(-10,-10,40,20),
			new ColorC(Color.red)	
		})));
		world.addChild(new Entity(new Component[] {
			new Position(0,0),
			new TileMap(24, 24, 37, new int[] {
					1,1,1,1,1 ,1,1,1,1,1,1,1,1 ,1,1,1,1 ,1 ,1 ,1,1,1,1,1,1 ,1,1,1,1,1,1,1,1 ,1,1,1,1,
					1,1,1,1,1 ,1,1,1,1,1,1,1,1 ,1,1,1,1 ,1 ,1 ,1,1,1,1,1,1 ,1,1,1,1,1,1,1,1 ,1,1,1,1,
					1,1,1,1,1 ,1,1,1,1,1,1,1,1 ,1,1,1,1 ,1 ,1 ,1,1,1,1,1,1 ,1,1,1,1,1,1,1,1 ,1,1,1,1,
					1,1,1,1,1 ,1,1,1,1,1,1,1,1 ,1,1,1,1 ,1 ,1 ,1,1,1,1,1,1 ,1,1,1,1,1,1,1,1 ,1,1,1,1,
					2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2,
					2,7,5,5,5 ,5,5,5,5,5,5,5,5 ,5,5,5,10,5 ,5 ,5,5,5,5,5,5 ,5,5,5,5,5,5,5,5 ,5,5,6,2,
					2,4,2,2,2 ,2,2,2,2,2,2,2,2 ,2,2,2,9 ,5 ,11,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,2,2,4,2,
					2,4,2,3,3 ,3,3,3,3,3,3,3,3 ,3,3,3,3 ,3 ,3 ,3,3,3,3,3,3 ,3,3,3,3,3,3,3,3 ,3,2,4,2,
					2,4,2,3,14,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,14,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,15,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,13,2,2,2,2 ,2 ,2 ,2,2,2,2,2,13,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,12,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,13,2,2,2,2 ,2 ,2 ,2,2,2,2,2,13,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,3,2,4,2,
					2,4,2,3,14,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,14,3,2,4,2,
					2,4,2,3,3 ,3,3,3,3,3,3,3,3 ,3,3,3,3 ,3 ,3 ,3,3,3,3,3,3 ,3,3,3,3,3,3,3,3 ,3,2,4,2,
					2,4,2,2,2 ,2,2,2,2,2,2,2,2 ,2,2,2,2 ,2 ,2 ,2,2,2,2,2,2 ,2,2,2,2,2,2,2,2 ,2,2,4,2,
					2,9,5,5,5 ,5,5,5,5,5,5,5,5 ,5,5,5,5 ,5 ,5 ,5,5,5,5,5,5 ,5,5,5,5,5,5,5,5 ,5,5,8,2,
			})
		}));
		
		systems = new ECS_System[] {
			new AISystem(),
			new VelocitySystem()
		};
		renderers = new RenderSystem[] {
				new TileDebugDraw(),
				new DebugDraw()
		};
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(Color.green);
		for(RenderSystem r: renderers) {
			r.renderWorld(world, container, game, g);
		}
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
