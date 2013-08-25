package dev.coloniergames.ld27.entity;

import java.util.Random;

import dev.coloniergames.ld27.gfx.Animator;
import dev.coloniergames.ld27.util.TextureData;
import dev.coloniergames.ld27.weapon.Projectile;
import dev.coloniergames.ld27.weapon.ProjectileType;

public class Mob extends Entity {

	static Animator mobSprite_base = new Animator(0, 0, BLOCK / 1.5f, BLOCK / 1.5f, TextureData.entityTextures[1], 200);;
	Animator unique_sprite;
	
	Entity target;
	
	float speed = 0.05f;
	
	Random random = new Random();
	
	public Mob(float x, float y, Entity target) {
		super(x, y, mobSprite_base, 10);

		this.target = target;
		
		unique_sprite = new Animator(0, 0, BLOCK / 1.5f, BLOCK / 1.5f, TextureData.entityTextures[1], 200);
		
		this.setSprite(unique_sprite);
		
		this.hitBox.update(position.x, position.y);
	}

	@Override
	public void act(int delta) {
		
		float sx = position.x - target.position.x;
		float sy = position.y - target.position.y;
		
		float rotation = (float) Math.atan2(sx, sy);
		
		float distance = (float) Math.sqrt(sx * sx + sy * sy);
		
		float multiplier = speed / distance;
		
		float vX = sx * multiplier, vY = sy * multiplier;
		
		if(distance >= 3 * BLOCK && distance <= 5 * BLOCK){
			setVelocity(-vX, -vY);
			// if(random.nextInt(5000) == 42) projectiles.add(new Projectile(position.x, position.y, -rotation, ProjectileType.JAVELIN, this));
		}
		else setVelocity(0, 0);
		
		if(distance <= 3 * BLOCK) setVelocity(vX, vY);
		move();
		
		
		hitBox.update(position.x, position.y);
		
	}

}
