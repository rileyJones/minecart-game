package ecs;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class OneBodySystem implements ECS_System {
	public void updateWorld(Entity world, GameContainer container, StateBasedGame game, int delta) {
		ArrayList<Entity> validEntities = new ArrayList<Entity>();
		getValidEntities(world, validEntities);
		for(Entity a: validEntities) {
			update(a, container, game, delta);
		}
	}
	
	private void getValidEntities(Entity world, ArrayList<Entity> validEntities) {
		if(test(world)) {
			validEntities.add(world);
		}
		for(Entity e: world.getChildren()) {
			getValidEntities(e, validEntities);
		}
	}
	
	protected abstract void update(Entity e, GameContainer container, StateBasedGame game, int delta);
	protected abstract boolean test(Entity e);
}
