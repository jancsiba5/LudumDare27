package dev.coloniergames.ld27.entity;

import java.util.ArrayList;
import java.util.List;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.collision.AABB;
import dev.coloniergames.ld27.game.Game;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.Vector;
import dev.coloniergames.ld27.weapon.Projectile;

public abstract class Entity implements Constants {

	public Vector position;
	public float vX, vY, rotation;
	
	public Sprite sprite;
	
	public int health;
	
	public AABB hitBox;
	
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Projectile> projectilesToRemove = new ArrayList<Projectile>();
	
	public Entity(float x, float y, Sprite sprite, int health) {
		
		this.position = new Vector(x, y);
		this.sprite = sprite;
		
		this.health = health;
		
		this.rotation = 0;
		
		this.hitBox = new AABB(position, new Vector(sprite.getWidth(), sprite.getHeight()));
		
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
		
		hitBox.update(position.x, position.y);
		
	}
	
	public void setSprite(Sprite s) {
		this.sprite = s;
	}
	
	public abstract void act(int delta);
	
	public void knockBack(float pRot, float distance) {
		
		float vX = (float) Math.sin(pRot) * distance;
		float vY = (float) -Math.cos(pRot) * distance;
		
		setVelocity(vX, vY);
		move();
		
	}
	
	public void updateProjs() {
		
		
		
	}
	
	public boolean isOnScreen(Entity e) {
		
		float pX = Game.player.playerCamera.position.x;
		float pY = Game.player.playerCamera.position.y;
		
		return position.x >= pX && position.y >= pY && position.x <= pX + w + BLOCK && position.y <= pY + h + BLOCK;
	}
	
	public boolean isOnScreen(Projectile p) {
		
		float pX = Game.player.playerCamera.position.x;
		float pY = Game.player.playerCamera.position.y;
		
		return p.position.x >= pX && p.position.y >= pY && p.position.x <= pX + w + BLOCK && p.position.y <= pY + h + BLOCK;
	}
	
	public void draw() {
		sprite.draw();
		
		for(Projectile p : projectiles) {
			p.draw();
		}
	}
}
