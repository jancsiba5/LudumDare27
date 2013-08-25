package dev.coloniergames.ld27.weapon;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.util.TextureData;

public enum ProjectileType implements Constants {

	FIREBALL(0, 2, 1000, 1f, 1 * BLOCK, BLOCK / 2, BLOCK / 2, 50, false), 
	LIGHTNING_BALL(1, 3, 1000, 1f, 0 * BLOCK, BLOCK / 2, BLOCK / 2, 100, true), 
	WATER_BALL(2, 4, 1000, 1f, 1 * BLOCK, BLOCK / 2, BLOCK / 2, 20, false), 
	JAVELIN(4, 3, 2000, 0.75f, 2 * BLOCK, BLOCK / 2, BLOCK, 50, false),
	ARROW(3, 1, 2000, 0.60f, 1 * BLOCK, BLOCK / 2, BLOCK, 50, false);;
	
	
	public int row, damage, maxTicks, animationSpeed;
	public int[] textures;
	public float speed, width, height, knockBack;
	public boolean mTargets;
	
	ProjectileType(int tX, int damage, int maxTicks, float speed, float knockBack, float width, float height, int animationSpeed, boolean mTargets) {
		this.row = tX;
		this.damage = damage;
		this.maxTicks = maxTicks;
		this.animationSpeed = animationSpeed;
		this.mTargets = mTargets;
		
		this.knockBack = knockBack;
		this.width = width;
		this.height = height;
		
		this.speed = speed;
		
		this.textures = TextureData.projectileTextures[row];
	}
}
