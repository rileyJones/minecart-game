package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ai.Enemy;
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
	
	Entity spawnGroup;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		world = new Entity(new Component[] {});
		Entity player = new Entity(new Component[] {
				new Position(12+18*24,12+16*24),
				new Box(-10,-10,20,20),
				new ColorC(Color.blue),
				new Player(((MinecartGame)game).controller),
				new Velocity(0,0)
			}); 
		
		world.addChild(player);
		
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
		Entity enemyPrototype = new Entity(new Component[] {
			new Position(0,0),
			new Box(-10,-10,20,20),
			new ColorC(Color.red),
			new Enemy(player)
		});
		spawnGroup = new Entity(new Component[] {
			new Position(0,0)
		});
		world.addChild(new Entity(new Component[] {
			
		}).addChild(new Entity(new Component[] {
			new Position(12+12*24, 12+20*24),
			new Spawner(enemyPrototype, spawnGroup)
		})).addChild(new Entity(new Component[] {
			new Position(12+24*24, 12+12*24),
			new Spawner(enemyPrototype, spawnGroup)
		})).addChild(new Entity(new Component[] {
			new Position(12+24*24, 12+20*24),
			new Spawner(enemyPrototype, spawnGroup)
		})).addChild(new Entity(new Component[] {
			new Position(12+12*24, 12+12*24),
			new Spawner(enemyPrototype, spawnGroup)
		})).addChild(spawnGroup));
		systems = new ECS_System[] {
			new AISystem(),
			new VelocitySystem(),
			new TileMapCollision(),
			new SpawnEnemiesSystem(spawnGroup)
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
		//System.out.println(((Position)(spawnGroup.getChildren()[0].getTraitByID(TRAIT.POSITION).unwrap().getValue())).getPos().unwrap().x);
	}

	@Override
	public int getID() {
		return 0;
	}

}
