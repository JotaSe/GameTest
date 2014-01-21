package com.jotase.mygame.controller;

import java.util.HashMap;
import java.util.Map;

import com.jotase.mygame.model.Dummy;
import com.jotase.mygame.model.DummyWorld;
import com.jotase.mygame.model.Dummy.State;
import java.util.HashMap;
import javax.media.DurationUpdateEvent;

public class DummyController {
	enum Keys {
		LEFT, RIGHT, JUMP, FIRE
	}

	private DummyWorld world;
	private Dummy dummy;
	private long jumpPressedTime;
	private boolean jumpingPressed;

	private static final long LONG_JUMP_PRESS = 150l;
	private static final float ACCELERATION = 10f;
	private static final float GRAVITY = -25f;
	private static final float MAX_JUMP_SPEED = 5f;
	private static final float DAMP = 0.96f;
	private static final float MAX_VEL = 4f;
	private static final float WIDTH = 10f;

	static Map<Keys, Boolean> keys = new HashMap<DummyController.Keys, Boolean>();
	static {
		keys.put(Keys.FIRE, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
	}

	/*
	 * key pressed and touch
	 */

	public DummyController(DummyWorld world, Dummy dummy) {
		super();
		this.world = world;
		this.dummy = dummy;
	}

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
		jumpingPressed = false;

	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));

	}

	// method that update main
	public void update(float delta) {
		processInput();
		setAcceleration(delta);
		dummy.update(delta);
		setPosition();

	}

	private void setPosition() {
		if (dummy.getPosition().y < 0) {
			dummy.getPosition().y = 0f;
			dummy.setPosition(dummy.getPosition());
			if (dummy.getState().equals(State.JUMPING)) {
				dummy.setState(State.IDLE);
			}
		}
		if (dummy.getPosition().x < 0) {
			dummy.getPosition().x = 0;
			dummy.setPosition(dummy.getPosition());
			if (!dummy.getState().equals(State.JUMPING)) {
				dummy.setState(State.IDLE);
			}
		}
		if (dummy.getPosition().x > WIDTH - dummy.getBounds().width) {
			dummy.getPosition().x = WIDTH - dummy.getBounds().width;
			dummy.setPosition(dummy.getPosition());
			if (!dummy.getState().equals(State.JUMPING)) {
				dummy.setState(State.IDLE);
			}
		}

	}

	private void setAcceleration(float delta) {
		float vel;
		if(jumpingPressed)
		{
			vel = MAX_VEL/2;
		}
		else
			vel = MAX_VEL;
		dummy.getAcceleration().y = GRAVITY;
		dummy.getAcceleration().mul(delta);
		dummy.getVelocity().add(dummy.getAcceleration().x,
				dummy.getAcceleration().y);
		if (dummy.getAcceleration().x == 0)
			dummy.getVelocity().x *= DAMP;
		if (dummy.getAcceleration().x > vel) {
			dummy.getAcceleration().x = vel;
		}
		if (dummy.getAcceleration().y < -vel) {
			dummy.getAcceleration().y = -vel;
		}
		
	}

	private boolean processInput() {
		// if Jump
		if (keys.get(Keys.JUMP)) {
			jumpAction();
		}
		// if left is pressed
		if (keys.get(Keys.LEFT)) {
			leftAction();
		}
		// if right is pressed
		if (keys.get(Keys.RIGHT)) {
			rightAction();
		}
		if ((keys.get(Keys.LEFT)) && keys.get(Keys.RIGHT)
				|| (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			idleAction();

		}
		return false;
	}

	private void idleAction()
	{
		if(!dummy.getState().equals(State.JUMPING))
		{
			dummy.setState(State.IDLE);
		}
		dummy.getAcceleration().x = 0;
	}
	private void rightAction()
	{
		dummy.setFacingLeft(false);
		if(!dummy.getState().equals(State.JUMPING))
		{
			dummy.setState(State.WALKING);
		}
		dummy.getAcceleration().x = ACCELERATION;
	}
	private void leftAction() {
		dummy.setFacingLeft(true);
		if (!dummy.getState().equals(State.JUMPING)) {
			dummy.setState(State.WALKING);
		}
		dummy.getAcceleration().x = -ACCELERATION;

	}

	private void jumpAction() {
		if (!dummy.getState().equals(State.JUMPING)) {
			jumpingPressed = true;
			jumpPressedTime = System.currentTimeMillis();
			dummy.setState(State.JUMPING);
			dummy.getVelocity().y = MAX_JUMP_SPEED;

			} else {
			if (jumpingPressed
					&& ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
				jumpingPressed = false;
			} else {
				if (jumpingPressed) {
					dummy.getVelocity().y = MAX_JUMP_SPEED;
				}
			}
		}
	}
}
