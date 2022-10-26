package components;

import ecs.Component;
import ecs.TRAIT;

public class Lifetime extends Component{

	private int time;
	
	public Lifetime(int time) {
		this.time = time;
	}
	
	public void update(int delta) {
		this.time -= delta;
		if(this.time <= 0) {
			owner.setParent(null);
		}
	}
	
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
		this.time = Math.min(this.time,((Lifetime)other).time);
		return this;
	}

	@Override
	public Component set(Component other) {
		this.time = ((Lifetime)other).time;
		return this;
	}

	@Override
	public TRAIT ID() {
		return TRAIT.LIFETIME;
	}

	@Override
	public Component clone() {
		return new Lifetime(time);
	}

}
