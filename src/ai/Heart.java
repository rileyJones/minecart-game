package ai;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import components.AI;
import ecs.Component;

public class Heart extends AI{

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}

	@Override
	public AI_TYPE getType() {
		return AI_TYPE.HEART;
	}

	@Override
	public Component clone() {
		return new Heart();
	}
	
}
