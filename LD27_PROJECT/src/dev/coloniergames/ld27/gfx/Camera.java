package dev.coloniergames.ld27.gfx;

import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.util.Vector;

public class Camera implements Constants {

	public Vector position;
	float rotation;
	
	public Camera(float x, float y, float rotation) {
		this.position = new Vector(x, y);
		this.rotation = rotation;
	}
	
	public void setPosition(float newX, float newY) {
		this.position.setPosition(newX, newY);
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void translate() {
		GL11.glTranslatef(position.x, position.y, 0);
		GL11.glTranslatef(w / 2, h / 2, 0);
		GL11.glRotated(Math.toDegrees(rotation), 0, 0, 1);
		GL11.glTranslatef(-w / 2, -h / 2, 0);
	}
	
}
