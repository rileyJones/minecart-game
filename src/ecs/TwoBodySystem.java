package ecs;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class TwoBodySystem implements ECS_System {
	public void updateWorld(Entity world, GameContainer container, StateBasedGame game, int delta) {
		ArrayList<Entity> validEntities = new ArrayList<Entity>();
		getValidEntities(world, validEntities);
		for(Entity a: validEntities) {
			if(testPrimary(a)) {
				for(Entity b: validEntities.subList(validEntities.indexOf(a)+1,validEntities.size())) {
					if(testSecondary(b)) {
						if(test(a,b)) {
							update(a, b, container, game, delta);
						}
					}
				}
			} else {
				for(Entity b: validEntities.subList(validEntities.indexOf(a)+1,validEntities.size())) {
					if(testPrimary(b)) {
						if(test(b, a)) {
							update(b, a, container, game, delta);
						}
					}
				}
			}
		}
	}
	private void getValidEntities(Entity world, ArrayList<Entity> validEntities) {
		if(testPrimary(world) || testSecondary(world)) {
			validEntities.add(world);
		}
		for(Entity e: world.getChildren()) {
			getValidEntities(e, validEntities);
		}
	}
	protected abstract void update(Entity primary, Entity secondary, GameContainer container, StateBasedGame game, int delta);
	protected abstract boolean test(Entity primary, Entity secondary);
	protected abstract boolean testPrimary(Entity primary);
	protected abstract boolean testSecondary(Entity secondary);
}
