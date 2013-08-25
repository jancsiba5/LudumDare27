package dev.coloniergames.ld27.gfx;

import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.util.Vector;

public class Animator extends Sprite implements Constants {

	int[] textures;
	int currentFrame;
	int speed;
	int length;
	int delta;
	int frameTimer;

	long lastFrame;

	Vector position;
	float width, height;

	float rotation;

	boolean isPaused;

	public Animator(float x, float y, int texture) {
		super(x, y, texture);
	}

	public Animator(float x, float y, int[] textures, int speed) {
		super(x, y, textures[0]);
		this.currentFrame = 0;
		this.textures = textures;
		this.speed = speed;
		this.length = textures.length;
		this.position = new Vector(x, y);
		this.width = BLOCK;
		this.height = BLOCK;
	}

	public Animator(float x, float y, float width, float height, int[] textures, int speed) {
		super(x, y, textures[0]);
		this.currentFrame = 0;
		this.textures = textures;
		this.speed = speed;
		this.length = textures.length;
		this.position = new Vector(x, y);
		this.width = width;
		this.height = height;

	}

	public void setRotation(float newRot) {
		this.rotation = newRot;
	}

	public void setPosition(float newX, float newY) {
		this.position.setPosition(newX, newY);
	}

	public void moveTo(float newX, float newY) {
		setPosition(newX, newY);
	}

	public long getTime() {
		return System.nanoTime() / 1000000;
	}

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setAnimationSet(int[] newSet) {
		this.textures = newSet;

		this.length = newSet.length;
	}

	public void pauseAt(int frameNum) {

		isPaused = true;
		currentFrame = 0;

	}
	
	public void play() {
		
		isPaused = false;
		
	}

	@Override
	public void draw() {
		if(!isPaused) {
			frameTimer += getDelta();
			if(frameTimer >= speed) {
				currentFrame += 1;
				frameTimer = 0;
			}

			if(currentFrame >= length) {
				currentFrame = 0;
			}
		}

		GL11.glPushMatrix();

		GL11.glTranslatef(position.x, position.y, 0);
		GL11.glTranslatef(width / 2, height / 2, 0);
		GL11.glRotated(Math.toDegrees(rotation), 0, 0, 1);
		GL11.glTranslatef(-width / 2, -height / 2, 0);


		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[currentFrame]);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(1, 1, 1);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(width, 0);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(width, height);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(0, height);
		GL11.glEnd();

		GL11.glPopMatrix();
	}

}
