package ai;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import components.AI;
import ecs.Component;
import ecs.Entity;

public class Enemy extends AI{

	Entity player;
	
	public Enemy(Entity player) {
		this.player = player;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}

	@Override
	public Component clone() {
		return new Enemy(player);
	}

}
