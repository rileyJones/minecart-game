package components;

import ecs.Component;
import ecs.TRAIT;

public class NoFriction extends Component {

	@Override
	protected Component combine(Component other) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	protected Component anticombine(Component other) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Component modify(Component other) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Component set(Component other) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public TRAIT ID() {
		// TODO Auto-generated method stub
		return TRAIT.NO_FRICTION;
	}

	@Override
	public Component clone() {
		// TODO Auto-generated method stub
		return new NoFriction();
	}

}
