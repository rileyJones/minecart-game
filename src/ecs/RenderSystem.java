package ecs;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class RenderSystem {
	public void renderWorld(Entity world, GameContainer container, StateBasedGame game, Graphics g) {
		if(test(world)) {
			render(world, container, game, g);
		}
		for(Entity e: world.getChildren()) {
			renderWorld(e, container, game, g);
		}
	}
	protected abstract void render(Entity e, GameContainer container, StateBasedGame game, Graphics g);
	protected abstract boolean test(Entity e);
}
