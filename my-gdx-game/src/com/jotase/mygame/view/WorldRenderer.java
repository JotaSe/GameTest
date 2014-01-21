package com.jotase.mygame.view;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.jotase.mygame.model.Block;
import com.jotase.mygame.model.Dummy;
import com.jotase.mygame.model.DummyWorld;
import com.jotase.mygame.model.Dummy.State;
import com.sun.org.apache.bcel.internal.generic.CPInstruction;

public class WorldRenderer {
	
	
	private DummyWorld world;
	private OrthographicCamera cam;
	final Color colorBlock = new Color(0, 1, 0, 1);
	final Color colorDummy = new Color(1, 0, 0, 1);
	ShapeRenderer debuRenderer = new ShapeRenderer();

	private static final float RUNNING_FRAME_DURATION = 0.06f;

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	/** Textures **/
	// private Texture dummyTexture;
	// private Texture blockTexture;
    private TextureRegion jumpLeft;
    private TextureRegion jumpRight;
    private TextureRegion fallLeft;
    private TextureRegion fallRight;
	private TextureRegion idleLeft;
	private TextureRegion idleRight;
	private Texture blockTexture;
	private TextureRegion dummyFrame;
	private Texture backgroundImage;
	private Sprite backgroundSprite;
	/** Animations **/

	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

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
		// initiate atlas with the dummy pack
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/pack/dummy.atlas"));
				
		// In this time, I haven't add the block texture to the pack, so,
		// I'm using a normal png
		blockTexture = new Texture(Gdx.files.internal("images/block.png"));
		
		// Creating the idle state facing right cuz, is the original image
		idleRight = atlas.findRegion("61");

		// for idle state facing left we just flip it out
		idleLeft = new TextureRegion(idleRight);
		idleLeft.flip(true, false);
		
		//Adding jump and fall textures
		jumpRight = atlas.findRegion("54");
		jumpLeft = new TextureRegion(jumpRight);
		jumpLeft.flip(true, false);
		
		fallRight = atlas.findRegion("75");
		fallLeft = new TextureRegion(fallRight);
		fallLeft.flip(true,false);
		TextureRegion[] rightWalkFrame = new TextureRegion[6];
		for (int i = 0; i < rightWalkFrame.length; i++) {
			rightWalkFrame[i] = atlas.findRegion("" +(59 + i));
		}

		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION,
				rightWalkFrame);

		// creating the animation, calling a number of images from the pack
		// in this case, we going to use the images from 60 to 66
		TextureRegion[] leftWalkFrame = new TextureRegion[6];
		for (int i = 0; i < leftWalkFrame.length; i++) {
			leftWalkFrame[i] = new TextureRegion(rightWalkFrame[i]);
			leftWalkFrame[i].flip(true, false);
		}

		// now we instance the walk left animation
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, leftWalkFrame);

		// Now we do the same action, but this time for right wall animation


		setBackground();
	}

	/** Method to set a background image**/
	private void setBackground() {
		backgroundImage = new Texture("images/background.jpg");
		backgroundSprite = new Sprite(backgroundImage);
	}

	public void render() {
		spriteBatch.begin();
		backgroundSprite.draw(spriteBatch); // this is when we set the background
		drawBlocks();
		drawDummy();
		spriteBatch.end();
		if (debug) {
			drawDebug();
		}

	}

	private void drawBlocks() {
		for (Block block : world.getBlocks()) {
			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX,
					block.getPosition().y * ppuY, Block.getSize() * ppuX,
					Block.getSize() * ppuY);
		}
	}

	private void drawDummy() {
		Dummy dummy = this.world.getDummy();
		dummyFrame = dummy.isFacingLeft() ? idleLeft : idleRight;
		if (dummy.getState().equals(State.WALKING)) {
			dummyFrame = dummy.isFacingLeft() ? walkLeftAnimation.getKeyFrame(
					dummy.getStateTime(), true) : walkRightAnimation
					.getKeyFrame(dummy.getStateTime(), true);
		}
		else if (dummy.getState().equals(State.JUMPING))
		{
			if(dummy.getVelocity().y > 0)
			{
				dummyFrame = dummy.isFacingLeft() ? jumpLeft : jumpRight;
			}
				else
				{
					dummyFrame = dummy.isFacingLeft() ? fallLeft : fallRight;
				}
			
		}
		spriteBatch.draw(dummyFrame, dummy.getPosition().x * ppuX,
				dummy.getPosition().y * ppuY, dummy.size * ppuX, dummy.size
						* ppuY);
		
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
