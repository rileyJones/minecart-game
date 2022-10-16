package components;

import javax.naming.NoPermissionException;

import org.newdawn.slick.geom.Vector2f;

import ecs.Component;
import ecs.Result;
import ecs.TRAIT;

public class Velocity extends Component{

	private Vector2f vel;
	
	public Velocity(float x, float y) {
		vel = new Vector2f(x, y);
	}

	public Result<Vector2f, NoPermissionException> getVel() {
		if(isOpen()) {
			return new Result<Vector2f, NoPermissionException>(vel);
		} else {
			return new Result<Vector2f, NoPermissionException>(new NoPermissionException());
		}
	}
	
	@Override
	protected Component combine(Component other) {
		vel.add(((Velocity)other).vel);
		return this;
	}

	@Override
	protected Component anticombine(Component other) {
		vel.sub(((Velocity)other).vel);
		return this;
	}

	@Override
	public Component modify(Component other) {
		vel.add(((Velocity)other).vel);
		return this;
	}
	
	@Override
	public Component set(Component other) {
		vel.x = ((Velocity)other).vel.x;
		vel.y = ((Velocity)other).vel.y;
		return this;
	}

	@Override
	public TRAIT ID() {
		return TRAIT.VELOCITY;
	}

	@Override
	public Component clone() {
		return new Velocity(vel.x, vel.y);
	}

}
