package components;

import ecs.Component;
import ecs.TRAIT;

public class Timer extends Component {

	int time;
	
	public Timer(int length) {
		this.time = length;
	}
	
	public boolean update(int delta) {
		time = Math.max(-1,time - delta);
		return time < 0;
	}
	
	public boolean isDone() {
		return time < 0;
	}
	
	public void setTimer(int time) {
		this.time = time;
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
		this.time += ((Timer)other).time;
		return this;
	}

	@Override
	public Component set(Component other) {
		this.time = ((Timer)other).time;
		return this;
	}

	@Override
	public TRAIT ID() {
		return TRAIT.TIMER;
	}

	@Override
	public Component clone() {
		return new Timer(time);
	}

}
