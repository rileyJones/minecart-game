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
import color.CNAME;
import color.CTYPE;
import color.ColorSelector;
import components.*;
import controller.BUTTON;
import ecs.*;
import etc.ptr;
import game.MinecartGame;
import renderers.DebugDraw;
import renderers.DebugHP;
import renderers.DebugItems;
import renderers.TileDebugDraw;
import systems.*;

public class GameState extends BasicGameState {
	
	Entity world;
	ECS_System[] systems;
	RenderSystem[] renderers;
	
	Entity spawnGroup;
	Entity kart;
	
	ptr<Integer> HP;
	Player pAI;
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		pAI.button_b = ((MinecartGame)game).button_b;
		pAI.button_a = ((MinecartGame)game).button_a;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		world = new Entity(new Component[] {});
		pAI = new Player(((MinecartGame)game).controller, world);
		Entity player = new Entity(new Component[] {
				new Position(12+18*24,12+16*24),
				new Box(-10,-10,20,20),
				new ColorC(Color.blue),
				pAI,
				new Velocity(0,0)
			}); 
		HP = new ptr<Integer>(6);
		
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
		
		world.addChild(new Entity(new Component[] {
				new Timer(1000,true),
				new Velocity(0,0),
				new Position(0,0)
		}).addChild(player));
		
		systems = new ECS_System[] {
			new UpdateTimers(),
			new AISystem(),
			new CollideClipSystem(),
			new SwordEnemyCollision(),
			new VelocitySystem(),
			new TileMapCollision(HP),
			new PlayerEnemyCollision(HP),
			new TileMapCollision(HP),
			new TunnelCollideSystem(),
			new FrictionSystem(),
			new SpawnEnemiesSystem(spawnGroup),
		};
		renderers = new RenderSystem[] {
				new TileDebugDraw(),
				new DebugDraw(),
				new DebugHP(HP),
				new DebugItems()
		};
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(ColorSelector.get().getColor(CNAME.ORANGE, CTYPE.LIGHT, true, false));
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
			init(container, game);
			game.enterState(1);
		}
		if(((MinecartGame)game).controller.buttonPressed(BUTTON.KEY_PAUSE)) {
			game.enterState(2);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
