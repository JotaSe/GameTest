package com.jotase.mygame.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class DummyWorld {
	@SuppressWarnings("rawtypes")
	private Array<Block> blocks = new Array<Block>();
	private Dummy dummy;
	
	public Array<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(Array<Block> blocks) {
		this.blocks = blocks;
	}

	public Dummy getDummy() {
		return dummy;
	}

	public void setDummy(Dummy dummy) {
		this.dummy = dummy;
	}

	public DummyWorld() {
		createWorld();
	}
	

	private void createWorld() {
		dummy = new Dummy(new Vector2(9, 2));
		for (int i = 0; i < 10; i++) {
			blocks.add(new Block(new Vector2(i, 1)));
		}
		blocks.add(new Block(new Vector2(9, 2)));
		blocks.add(new Block(new Vector2(9, 3)));
		blocks.add(new Block(new Vector2(9, 4)));
		blocks.add(new Block(new Vector2(9, 5)));
		blocks.add(new Block(new Vector2(6, 3)));
		blocks.add(new Block(new Vector2(6, 4)));
		blocks.add(new Block(new Vector2(6, 5)));

	}

}
