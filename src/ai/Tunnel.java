package ai;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import components.AI;
import ecs.Component;

public class Tunnel extends AI {

	private boolean isVertical = false;
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
	}
	
	public void setIsVertical(boolean b) {
		isVertical = b;
	}
	public boolean getIsVertical() {
		return isVertical;
	}

	@Override
	public AI_TYPE getType() {
		// TODO Auto-generated method stub
		return AI_TYPE.TUNNEL;
	}

	@Override
	public Component clone() {
		return new Tunnel();
	}

}
