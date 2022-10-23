package ecs;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;


public class Entity {
	Entity parent;
	private ArrayList<Entity> children;
	private Component[] traits;
	
	public Entity(Component[] traits) {
		this.parent = null;
		this.traits = traits;
		for(Component c: traits) {
			c.setOwner(this);
		}
		this.children = new ArrayList<Entity>();
	}
	private Entity(Entity parent, Component[] traits, ArrayList<Entity> children) {
		this.parent = parent;
		this.traits = traits;
		for(Component c: traits) {
			c.setOwner(this);
		}
		this.children = new ArrayList<Entity>();
		for(Entity child: children) {
			child.setParent(this);
		}
	}
	
	public Entity closestCommonParent(Entity other) {
		Stack<Entity> thisStack = this.getParentStack();
		Stack<Entity> otherStack = other.getParentStack();
		Entity E_this = thisStack.pop();
		Entity E_other = otherStack.pop();
		Entity E_common = null;
		while(E_this == E_other) {
			E_common = E_this;
			E_this = thisStack.pop();
			E_other = otherStack.pop();
			if(E_this == null || E_other == null) {
				break;
			}
		}
		return E_common;
	}
	
	private Stack<Entity> getParentStack() {
		Stack<Entity> parentStack = new Stack<Entity>();
		parentStack.push(this);
		Entity currentSelf = this;
		while(currentSelf.parent != null) {
			currentSelf = currentSelf.parent; 
			parentStack.push(currentSelf);
		}
		return parentStack;
	}
	
	
	public void setParent(Entity parent) {
		if(this.parent != null) {
			this.parent.children.remove(this);
		}
		this.parent = parent;
		if(this.parent != null) {
			this.parent.children.add(this);
		}
	}
	public Entity getParent() {
		return parent;
	}
	
	public Entity addChild(Entity child) {
		child.setParent(this);
		return this;
	}
	
	public Entity[] getChildren() {
		return children.toArray(new Entity[children.size()]);
	}
	
	public Result<Component,NoSuchElementException> getTraitByID(TRAIT ID) {
		for(Component c: traits) {
			if(c.ID() == ID) {
				return new Result<Component,NoSuchElementException>(c);
			}
		}
		return new Result<Component,NoSuchElementException>(new NoSuchElementException());
	}
	public Entity clone() {
		Component[] cloneTraits = new Component[traits.length];
		for(int i = 0; i < traits.length; i++) {
			cloneTraits[i] = traits[i].clone();
		}
		ArrayList<Entity> cloneChildren = new ArrayList<Entity>();
		for(Entity child: children) {
			cloneChildren.add(child.clone());
		}
		return new Entity(parent, cloneTraits, cloneChildren);
	}
}
