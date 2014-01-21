package com.jotase.mygame.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Gaming
 * 
 */
public class Dummy {
	public enum State {
		IDLE, WALKING, JUMPING, DYING, SHOOTING
	}
	


	
	
	public static final float SPEED = 4f;// unit per second
	public static final float JUMP_VELOCITY = 1f;
	public static final float size = 0.5f;// size of the actor (half a unit)
	float stateTime = 0;

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	boolean facingLeft = true;

	public Dummy(Vector2 position) {

		this.position = position;
		this.bounds.height = size;
		this.bounds.width = size;
	}

	public float getStateTime() {
		return stateTime;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Vector2 getPosition() {
		return position;
	}

	public State getState() {
		return state;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void update(float delta) {
		stateTime += delta;
		position.add(velocity.cpy().mul(delta));
	}

}
