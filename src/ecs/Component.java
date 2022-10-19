package ecs;

import java.util.NoSuchElementException;

public abstract class Component {
	protected Entity owner;
	private boolean open = false;
	protected Component() {}
	public Component getValue() {
		Component parentValue = getParentValue(owner);
		if(parentValue != null) {
			return this.clone().combine(parentValue).open();
		} else {
			return this.clone().open();
		}
	}
	
	public Component getInternalValue() {
		return this.clone().open();
	}
	
	public Component getValueDifference(Component other) {
		return this.getValue().anticombine(other.getValue()).open();
	}
	
	void setOwner(Entity owner) {
		this.owner = owner;
	}
	
	private Component getParentValue(Entity e) {
		if(e != null && e.parent != null) {
			Result<Component, NoSuchElementException> parentComponent = e.parent.getTraitByID(this.ID());
			if(parentComponent.is_ok()) {
				return parentComponent.unwrap().getValue().open();
			} else {
				return getParentValue(e.parent);
			}
		} else {
			return null;
		}
	}
	private Component open() {
		open = true;
		return this;
	}
	protected boolean isOpen() {
		return open;
	}
	protected abstract Component combine(Component other);
	protected abstract Component anticombine(Component other);
	public abstract Component modify(Component other);
	public abstract Component set(Component other);
	public abstract TRAIT ID();
	public abstract Component clone();
}
