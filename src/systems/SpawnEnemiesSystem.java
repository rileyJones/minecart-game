package systems;

import java.util.NoSuchElementException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import components.Spawner;
import ecs.Component;
import ecs.Entity;
import ecs.OneBodySystem;
import ecs.Result;
import ecs.TRAIT;
import etc.ptr;

public class SpawnEnemiesSystem extends OneBodySystem{

	Entity spawnGroup;
	ptr<Integer> waveNumber;
	
	public SpawnEnemiesSystem(Entity spawnGroup, ptr<Integer> waveNumber) {
		this.spawnGroup = spawnGroup;
		this.waveNumber = waveNumber;
	}

	@Override
	protected void update(Entity e, GameContainer container, StateBasedGame game, int delta) {
		Result<Component, NoSuchElementException> spawnerTraitR = e.getTraitByID(TRAIT.SPAWNER);
		if(spawnerTraitR.is_ok()) {
			Spawner eSpawner = (Spawner) spawnerTraitR.unwrap();
			eSpawner.spawn();
			waveNumber.V++;
		}
	}

	@Override
	protected boolean test(Entity e) {
		if(spawnGroup.getChildren().length == 0) {
			Result<Component, NoSuchElementException> spawnerTraitR = e.getTraitByID(TRAIT.SPAWNER);
			if(spawnerTraitR.is_ok()) {
				Spawner eSpawner = (Spawner) spawnerTraitR.unwrap();
				if(eSpawner.getSpawnGroup() == spawnGroup) {
					return true;
				}
			}
		}
		return false;
	}
	

}
