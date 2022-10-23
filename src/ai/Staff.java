package ai;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import components.AI;
import ecs.Component;

public class Staff extends AI{

	private int index;
	private boolean didAct = true;
	
	public Staff(int index) {
		this.index = index;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub
		
	}
	
	public int index() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean didAct() {
		return didAct;
	}
	public void clearDidAct() {
		didAct = false;
	}
	public void setDidAct() {
		didAct = true;
	}

	@Override
	public AI_TYPE getType() {
		return AI_TYPE.STAFF;
	}

	@Override
	public Component clone() {
		return new Staff(index);
	}

}
