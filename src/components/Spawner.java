package components;

import ecs.Component;
import ecs.Entity;
import ecs.TRAIT;

public class Spawner extends Component{

	Entity prototype;
	
	public Spawner(Entity prototype) {
		this.prototype = prototype;
	}
	
	public void spawn() {
		owner.addChild(prototype.clone());
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
		return this;
	}

	@Override
	public Component set(Component other) {
		return this;
	}

	@Override
	public TRAIT ID() {
		return TRAIT.SPAWNER;
	}

	@Override
	public Component clone() {
		return null;
	}

}
