package components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import ecs.Component;
import ecs.TRAIT;

public abstract class AI extends Component{

	public abstract void update(GameContainer container, StateBasedGame game, int delta);
	
	@Override
	protected Component combine(Component other) {
		return this;
	}

	@Override
	protected Component anticombine(Component other) {
		return this;
	}

	@Override
	public Component modify(Component other) {
		return this;
	}
	
	@Override
	public Component set(Component other) {
		return this;
	}

	@Override
	public TRAIT ID() {
		return TRAIT.AI;
	}

}
