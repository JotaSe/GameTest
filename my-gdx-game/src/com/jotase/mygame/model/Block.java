package com.jotase.mygame.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
	public Block(Vector2 position) {
		super();
		this.position = position;
		this.bounds.height=size;
		this.bounds.width=size;
	}
	static final float size =1f ; 
	public static float getSize() {
		return size;
	}
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	public Rectangle getBounds() {
		return bounds;
	}
	public Vector2 getPosition() {
		return position;
	}
	
}
