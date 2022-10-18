package components;

import java.util.NoSuchElementException;

import ecs.Component;
import ecs.Entity;
import ecs.Result;
import ecs.TRAIT;

public class Spawner extends Component{

	Entity prototype;
	Entity spawnGroup;
	
	public Spawner(Entity prototype, Entity spawnGroup) {
		this.prototype = prototype;
		this.spawnGroup = spawnGroup;
	}
	
	public void spawn() {
		Entity newEntity = prototype.clone();
		Result<Component, NoSuchElementException> newEntityPosR = newEntity.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> ownerPosR = owner.getTraitByID(TRAIT.POSITION);
		Result<Component, NoSuchElementException> spawnGroupPosR = spawnGroup.getTraitByID(TRAIT.POSITION);
		if(newEntityPosR.is_ok() && ownerPosR.is_ok()) {
			if(spawnGroupPosR.is_ok()) {
				newEntityPosR.unwrap().modify(ownerPosR.unwrap().getValueDifference(spawnGroupPosR.unwrap()));
			} else {
				newEntityPosR.unwrap().modify(ownerPosR.unwrap().getValue());
			}
		}
		spawnGroup.addChild(newEntity);
	}
	
	public Entity getSpawnGroup() {
		return spawnGroup;
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
