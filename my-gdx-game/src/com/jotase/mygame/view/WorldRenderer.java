package com.jotase.mygame.view;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.jotase.mygame.model.Block;
import com.jotase.mygame.model.Dummy;
import com.jotase.mygame.model.DummyWorld;
import com.sun.org.apache.bcel.internal.generic.CPInstruction;

public class WorldRenderer {
	private DummyWorld world;
	private OrthographicCamera cam;
	final Color colorBlock = new Color(0, 1, 0, 1);
	final Color colorDummy = new Color(1, 0, 0, 1);
	ShapeRenderer debuRenderer = new ShapeRenderer();

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	/** Textures **/
	private Texture dummyTexture;
	private Texture blockTexture;

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
	}

	public WorldRenderer(DummyWorld world, boolean debug) {
		this.world = world;
		Texture.setEnforcePotImages(false);
		this.cam = new OrthographicCamera(10, 7);
		this.cam.position.set(5, 3f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	private void loadTextures() {
		dummyTexture = new Texture(Gdx.files.internal("images/dummyp.png"));
		blockTexture = new Texture(Gdx.files.internal("images/block.png"));
	}

	public void render()
	{
		spriteBatch.begin();
		drawBlocks();
		drawDummy();
		spriteBatch.end();
		if(debug)
		{
			drawDebug();
		}
		
	}
	private void drawBlocks()
	{
		for (Block block : world.getBlocks()) {
			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX,
					block.getPosition().y * ppuY,
					Block.getSize() * ppuX, Block.getSize() * ppuY);
		}
	}
	private void drawDummy()
	{
		Dummy dummy = this.world.getDummy();
		spriteBatch.draw(dummyTexture, 
				dummy.getPosition().x * ppuX, 
				dummy.getPosition().y * ppuY, 
				Dummy.size * ppuX, 
				Dummy.size * ppuY);

	}
	
	public void drawDebug() {
		
		float x1, y1;
		Rectangle rect;
		// render blocks
		debuRenderer.setProjectionMatrix(cam.combined);
		debuRenderer.begin(ShapeType.Rectangle);
		for (Block block : world.getBlocks()) {
			rect = block.getBounds();
			x1 = block.getPosition().x + rect.x;
			y1 = block.getPosition().y + rect.y;
			debuRenderer.setColor(colorBlock);
			debuRenderer.rect(x1, y1, rect.width, rect.height);
		}
		// render dummy actor
		Dummy dummy = world.getDummy();
		rect = dummy.getBounds();
		x1 = dummy.getPosition().x + rect.x;
		y1 = dummy.getPosition().y + rect.y;
		debuRenderer.setColor(colorDummy);
		debuRenderer.rect(x1, y1, rect.width, rect.height);
		debuRenderer.end();
	}

}
