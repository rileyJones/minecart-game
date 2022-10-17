package physics;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import components.Box;
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
	
	public static float doBoxClip(Position aPos, Box aBox, Velocity aVel, Position bPos, Box bBox, Velocity bVel, float delta) {
		Vector2f aPosVec = ((Position)aPos.getValue()).getPos().unwrap();
		Rectangle aRect = ((Box)aBox.getValue()).getBox().unwrap();
		
		aRect.setX(aRect.getX()+aPosVec.x);
		aRect.setY(aRect.getY()+aPosVec.y);
		
		Vector2f bPosVec = ((Position)bPos.getValue()).getPos().unwrap();
		Rectangle bRect = ((Box)bBox.getValue()).getBox().unwrap();
		
		bRect.setX(bRect.getX()+bPosVec.x);
		bRect.setY(bRect.getY()+bPosVec.y);
		
		Velocity velDif = (Velocity) bVel.getValueDifference(aVel);
		Vector2f velDifVec = ((Velocity)velDif.getValue()).getVel().unwrap();
		
		float tempDelta = Physics.getBoxCollideDelta(aRect, new Vector2f(0,0), bRect, velDifVec);
		
		Physics.doVelocity(aPos, aVel, 0, 0, tempDelta);
		Physics.doVelocity(bPos, bVel, 0, 0, tempDelta);
		return -tempDelta;
	}
	
	private static float getBoxCollideDelta(Rectangle aRect, Vector2f aVel, Rectangle bRect, Vector2f bVel) {
		return (Math.max(Math.min(
				(aRect.getMaxX()-bRect.getMinX())/(bVel.x-aVel.x)
				,
				(aRect.getMinX()-bRect.getMaxX())/(bVel.x-aVel.x)
				),Math.min(
				(aRect.getMaxY()-bRect.getMinY())/(bVel.y-aVel.y)
				,
				(aRect.getMinY()-bRect.getMaxY())/(bVel.y-aVel.y)		
				)))-0.001f;
	}
}
