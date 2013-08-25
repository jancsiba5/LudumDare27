package dev.coloniergames.ld27.weapon;

import dev.coloniergames.ld27.collision.AABB;
import dev.coloniergames.ld27.entity.Entity;
import dev.coloniergames.ld27.gfx.Animator;
import dev.coloniergames.ld27.util.Vector;

public class Projectile {

	public Vector position;
	public float rotation;
	Animator sprite;
	public ProjectileType type;
	
	public boolean canDamage = true;
	
	public int ticksAlive = 0;
	
	public AABB hitBox;
	
	public Entity owner;
	
	public Projectile(float x, float y, float rotation, ProjectileType type, Entity owner) {
		
		this.position = new Vector(x, y);
		this.rotation = rotation;
		
		this.owner = owner;
		
		this.type = type;
		
		this.sprite = new Animator(x, y, type.width, type.height, type.textures, type.animationSpeed);
		
		this.hitBox = new AABB(position, new Vector(sprite.getWidth(), sprite.getHeight()));
		
		
	}
	
	public void draw() {
		
		sprite.draw();
		
	}
	
	public void act(int delta) {
		
		ticksAlive++;
		
		position.x -= (float) (Math.sin(rotation) * -type.speed * delta);
		position.y += (float) (Math.cos(rotation) * -type.speed * delta);
		
		sprite.moveTo(position.x, position.y);
		
		sprite.setRotation(rotation);
		
		hitBox.update(position.x, position.y);
		
	}
}
