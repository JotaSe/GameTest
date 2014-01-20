package com.jotase.mygame.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.jotase.mygame.model.Dummy;
import com.jotase.mygame.model.DummyWorld;
import com.jotase.mygame.model.Dummy.State;

public class WorldController {
	enum Keys
	{
		LEFT,RIGHT,JUMP,FIRE
	}
	private DummyWorld world;
	private Dummy dummy ;
	
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.FIRE, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
	}
	
	/*
	 * key pressed and touch
	 */

	public WorldController(DummyWorld world, Dummy dummy) {
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
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}
	// method that update main
	public void update(float delta)
	{
		processInput();
		dummy.update(delta);
	}

	private void processInput() {
		//if left is pressed
		if(keys.get(Keys.LEFT))
		{
			dummy.setFacingLeft(true);
			dummy.setState(State.WALKING);
			dummy.getVelocity().x = -dummy.SPEED;
			
		}
		//if right is pressed
		if(keys.get(Keys.RIGHT)){
			dummy.setFacingLeft(false);
			dummy.setState(State.WALKING);
			dummy.getVelocity().x = dummy.SPEED;
		}
		if((keys.get(Keys.LEFT))&&keys.get(Keys.RIGHT)
				||
				(!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT))))
		{
			dummy.setState(State.IDLE);
			//acceleration is now 0 on the x AXIs
			dummy.getAcceleration().x=0;
			//horizontal speed is now 0
			dummy.getVelocity().x=0;
			
		}
	}
}
