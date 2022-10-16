package physics;

import org.newdawn.slick.geom.Vector2f;

import components.Position;
import components.Velocity;

public class Physics {
	public static void doVelocity(Position pos, Velocity vel, float x, float y, float delta) {
		vel.modify(new Velocity(x,y));
		Velocity velVal = (Velocity) vel.getInternalValue();
		Vector2f velVec = velVal.getVel().unwrap();
		Position posVec = new Position(velVec.x * delta, velVec.y * delta);
		pos.modify(posVec);
	}
}
