package ecs;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class TwoBodySystem implements ECS_System {
	public void updateWorld(Entity world, GameContainer container, StateBasedGame game, int delta) {
		ArrayList<Entity> primaryEntities = new ArrayList<Entity>();
		ArrayList<Entity> secondaryEntities = new ArrayList<Entity>();
		getValidEntities(world, primaryEntities, secondaryEntities);
		ArrayList<Tuple<Entity,Entity>> windows = new ArrayList<Tuple<Entity,Entity>>();
		for(Entity a: primaryEntities) {
			for(Entity b: secondaryEntities) {
				if(test(a,b) && a != b) {
					windows.add(new Tuple<Entity,Entity>(a,b));
				}
			}
		}
		for(Tuple<Entity, Entity> t: windows) {
			update(t._1, t._2, container, game, delta);
		}
	}
	private void getValidEntities(Entity world, ArrayList<Entity> primaryEntities, ArrayList<Entity> secondaryEntities) {
		if(testPrimary(world)) {
			primaryEntities.add(world);
		}
		if(testSecondary(world)) {
			secondaryEntities.add(world);
		}
		for(Entity e: world.getChildren()) {
			getValidEntities(e, primaryEntities, secondaryEntities);
		}
	}
	protected abstract void update(Entity primary, Entity secondary, GameContainer container, StateBasedGame game, int delta);
	protected abstract boolean test(Entity primary, Entity secondary);
	protected abstract boolean testPrimary(Entity primary);
	protected abstract boolean testSecondary(Entity secondary);
}
