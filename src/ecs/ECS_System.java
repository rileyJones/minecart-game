package ecs;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public interface ECS_System {
	public void updateWorld(Entity world, GameContainer container, StateBasedGame game, int delta);
}
