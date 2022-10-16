package components;

import javax.naming.NoPermissionException;

import org.newdawn.slick.geom.Vector2f;

import ecs.Component;
import ecs.Result;
import ecs.TRAIT;

public class Position extends Component{
	
	private Vector2f pos;
	
	public Position(float x, float y) {
		pos = new Vector2f(x, y);
	}

	public Result<Vector2f, NoPermissionException> getPos() {
		if(isOpen()) {
			return new Result<Vector2f, NoPermissionException>(pos);
		} else {
			return new Result<Vector2f, NoPermissionException>(new NoPermissionException());
		}
	}
	
	@Override
	protected Component combine(Component other) {
		pos.add(((Position)other).pos);
		return this;
	}

	@Override
	protected Component anticombine(Component other) {
		pos.sub(((Position)other).pos);
		return this;
	}

	@Override
	public Component modify(Component other) {
		pos.sub(((Position)other).pos);
		return this;
	}

	@Override
	public TRAIT ID() {
		return TRAIT.POSITION;
	}

	@Override
	public Component clone() {
		return new Position(pos.x, pos.y);
	}
}
