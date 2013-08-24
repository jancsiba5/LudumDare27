package dev.coloniergames.ld27.entity;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.Vector;

public abstract class Entity implements Constants {

	public Vector position;
	public float vX, vY, rotation;
	
	public Sprite sprite;
	
	public int health;
	
	public Entity(float x, float y, Sprite sprite, int health) {
		
		this.position = new Vector(x, y);
		this.sprite = sprite;
		
		this.health = health;
		
		this.rotation = 0;
		
	}
	
	public void move() {
		this.position.x += vX;
		this.position.y += vY;
		
		updateSprite();
	}
	
	public void setVelocity(float vX, float vY) {
		this.vX = vX;
		this.vY = vY;
	}
	
	public void moveTo(float newX, float newY) {
		this.position.setPosition(newX, newY);
		
		updateSprite();
	}
	
	public void rotate(float rot) {
		this.rotation += rot;
		
		updateSprite();
	}
	
	public void setRotation(float newRot) {
		this.rotation = newRot;
		
		updateSprite();
	}
	
	public void updateSprite() {
		
		this.sprite.moveTo(position.x, position.y);
		this.sprite.setRotation(rotation);
		
	}
	
	public abstract void act(int delta);
	
	public void draw() {
		sprite.draw();
	}
}
