package components;

import javax.naming.NoPermissionException;

import org.newdawn.slick.Color;

import ecs.Component;
import ecs.Result;
import ecs.TRAIT;

public class ColorC extends Component {

	Color color;
	
	public ColorC(Color color) {
		this.color = color;
	}
	
	public Result<Color, NoPermissionException> getColor() {
		if(isOpen()) {
			return new Result<Color, NoPermissionException>(color);
		} else {
			return new Result<Color, NoPermissionException>(new NoPermissionException());
		}
	}
	
	@Override
	protected Component combine(Component other) {
		return this;
	}

	@Override
	protected Component anticombine(Component other) {
		return this;
	}

	@Override
	public Component modify(Component other) {
		color = ((ColorC)other).color;
		return this;
	}

	@Override
	public TRAIT ID() {
		return TRAIT.COLOR;
	}

	@Override
	public Component clone() {
		return new ColorC(color);
	}

}
