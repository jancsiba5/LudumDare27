package dev.coloniergames.ld27.weapon;

import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.Vector;

public class Projectile {

	public Vector position;
	public float rotation;
	Sprite sprite;
	public ProjectileType type;
	
	public int ticksAlive = 0;
	
	public Projectile(float x, float y, float rotation, ProjectileType type) {
		
		this.position = new Vector(x, y);
		this.rotation = rotation;
		
		this.type = type;
		
		this.sprite = new Sprite(x, y, type.texture, type.width, type.height);
		
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
		
	}
}
