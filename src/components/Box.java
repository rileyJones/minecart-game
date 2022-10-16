package components;

import javax.naming.NoPermissionException;

import org.newdawn.slick.geom.Rectangle;

import ecs.Component;
import ecs.Result;
import ecs.TRAIT;

public class Box extends Component{
	Rectangle box;

	public Box(float x, float y, float w, float h) {
		box = new Rectangle(x, y, w, h);
	}
	
	public Result<Rectangle, NoPermissionException> getBox() {
		if(isOpen()) {
			return new Result<Rectangle, NoPermissionException>(box);
		} else {
			return new Result<Rectangle, NoPermissionException>(new NoPermissionException());
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
		Rectangle otherBox = ((Box)other).box;
		box = new Rectangle(otherBox.getX(), otherBox.getY(), otherBox.getWidth(), otherBox.getHeight());
		return this;
	}
	
	@Override
	public Component set(Component other) {
		Rectangle otherBox = ((Box)other).box;
		box = new Rectangle(otherBox.getX(), otherBox.getY(), otherBox.getWidth(), otherBox.getHeight());
		return this;
	}

	@Override
	public TRAIT ID() {
		return TRAIT.BOX;
	}

	@Override
	public Component clone() {
		return new Box(box.getX(), box.getY(), box.getWidth(), box.getHeight());
	}
	
}
