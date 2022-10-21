package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ai.Enemy;
import ai.Kart;
import ai.Player;
import components.*;
import ecs.*;
import etc.ptr;
import game.MinecartGame;
import renderers.DebugDraw;
import renderers.DebugHP;
import renderers.TileDebugDraw;
import systems.*;

public class GameState extends BasicGameState {
	
	Entity world;
	ECS_System[] systems;
	RenderSystem[] renderers;
	
	Entity spawnGroup;
	Entity kart;
	
	ptr<Integer> HP;
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		HP.V = 6;
		kart.getTraitByID(TRAIT.POSITION).unwrap().set(new Position(17*24+12,5*24+12));
		kart.getTraitByID(TRAIT.VELOCITY).unwrap().set(new Velocity(0.1f,0.0f));
		for(Entity child: spawnGroup.getChildren()) {
			child.setParent(null);
		}
	}
	
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
		HP = new ptr<Integer>(6);
		world.addChild(new Entity(new Component[] {
				new Timer(1000,true),
				new Velocity(0,0),
				new Position(0,0)
		}).addChild(player));
		
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
		
		Entity enemyPrototype = new Entity( new Component[] {
			new Position(0,0),
			new Velocity(0,0),
			new Timer(200, true),
		}).addChild(new Entity(new Component[] {
			new Position(0,0),
			new Box(-10,-10,20,20),
			new ColorC(Color.red),
			new Enemy(player, 0.03f),
			new Velocity(0,0)
		}));
		//*/
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
		
		
		kart = new Entity(new Component[] {
				new Position(17*24+12,5*24+12),
				new Box(0,0,0,0),
				new ColorC(Color.lightGray),
				new Velocity(0.1f,0),
				new NoFriction(),
				new Kart()
			}).addChild(new Entity(new Component[] {
				new Position(0,0),
				new Box(-10,-10,20,13),
				new ColorC(Color.lightGray)
			}));
		
		world.addChild(kart);
		
		systems = new ECS_System[] {
			new UpdateTimers(),
			new AISystem(),
			new CollideClipSystem(),
			new SwordEnemyCollision(),
			new VelocitySystem(),
			new TileMapCollision(HP),
			new PlayerEnemyCollision(HP),
			new TileMapCollision(HP),
			new FrictionSystem(),
			new SpawnEnemiesSystem(spawnGroup),
		};
		renderers = new RenderSystem[] {
				new TileDebugDraw(),
				new DebugDraw(),
				new DebugHP(HP)
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
		if(HP.V <= 0) {
			game.enterState(1);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
