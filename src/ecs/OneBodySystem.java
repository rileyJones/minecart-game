package ecs;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class OneBodySystem implements ECS_System {
	public void updateWorld(Entity world, GameContainer container, StateBasedGame game, int delta) {
		if(test(world)) {
			update(world, container, game, delta);
		}
		for(Entity e: world.getChildren()) {
			updateWorld(e, container, game, delta);
		}
	}
	protected abstract void update(Entity e, GameContainer container, StateBasedGame game, int delta);
	protected abstract boolean test(Entity e);
}
