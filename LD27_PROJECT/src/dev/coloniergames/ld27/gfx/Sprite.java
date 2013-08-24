package dev.coloniergames.ld27.gfx;

import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.util.Vector;

public class Sprite implements Constants {

	Vector position;
	private float width, height;
	private float red, green, blue;

	int texture = 0;

	float rotation = 0;

	public boolean visible = true;
	
	

	public Sprite(float x, float y, int texture) {
		this.position = new Vector(x, y);
		this.texture = texture;

		this.width = BLOCK;
		this.height = BLOCK;

		this.red = 1;
		this.green = 1;
		this.blue = 1;

	}

	public Sprite(float x, float y, int texture, float width, float height) {
		this.position = new Vector(x, y);
		this.texture = texture;
		this.width = width;
		this.height = height;

		this.red = 1;
		this.green = 1;
		this.blue = 1;

	}

	public Sprite(float x, float y, int texture, float width, float height, float r, float g, float b) {
		this.position = new Vector(x, y);
		this.texture = texture;
		this.width = width;
		this.height = height;

		this.red = r;
		this.green = g;
		this.blue = b;

	}

	public Sprite(float x, float y, int texture, float r, float g, float b) {
		this.position = new Vector(x, y);
		this.texture = texture;
		this.width = BLOCK;
		this.height = BLOCK;

		this.red = r;
		this.green = g;
		this.blue = b;

	}

	public void rotate(float rotation) {
		this.rotation += rotation;
	}

	public void setRotation(float newRot) {
		this.rotation = newRot;
		
	}

	public void move(float x, float y) {
		this.position.setPosition(this.position.x + x, this.position.y + y);
	}

	public void moveTo(float newX, float newY) {
		this.position.setPosition(newX, newY);
	}

	public Vector getCenter() {
		return new Vector(position.x + getWidth() / 2, position.y + getHeight() / 2);
	}
	
	public void setColors(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRed() {
		return red;
	}

	public float getGreen() {
		return green;
	}

	public float getBlue() {
		return blue;
	}

	public void setTexture(int newTexture) {
		this.texture = newTexture;
	}

	public void draw() {
		GL11.glPushMatrix();

		GL11.glTranslatef(position.x, position.y, 0);
		GL11.glTranslatef(getWidth() / 2, getHeight() / 2, 0);
		GL11.glRotated(Math.toDegrees(rotation), 0, 0, 1);
		GL11.glTranslatef(-getWidth() / 2, -getHeight() / 2, 0);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glColor3f(getRed(), getGreen(), getBlue());

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(getWidth(), 0);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(getWidth(), getHeight());
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(0, getHeight());

		GL11.glEnd();


		GL11.glPopMatrix();
	}

}
