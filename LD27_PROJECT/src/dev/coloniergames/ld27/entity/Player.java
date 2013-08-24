package dev.coloniergames.ld27.entity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.gfx.Camera;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.TextureData;
import dev.coloniergames.ld27.weapon.Projectile;

public class Player extends Entity {

	public static Sprite playerSprite = new Sprite(0, 0, TextureData.testTexture, BLOCK / 2, BLOCK / 2);

	public Camera playerCamera = new Camera(0, 0, 0);

	public boolean mUp, mDown, mRight, mLeft, mNo, trackCamera = true;

	public float speed = 0.2f;

	public PlayerClass playerClass;
	
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Projectile> projectilesToRemove = new ArrayList<Projectile>();
	
	public Player(float x, float y) {

		super(x, y, playerSprite, 10);

		playerSprite.moveTo(x, y);

		playerClass = new PlayerMage(this);
		
		playerSprite.setTexture(playerClass.texture);
	}

	public void pollInput() {

		float mX = Mouse.getX();
		float mY = h - Mouse.getY();
		
		mX -= playerCamera.position.x;
		mY -= playerCamera.position.y;
		
		float xDistance = position.x - mX;
		float yDistance = position.y - mY;
		
		float rot = (float) Math.atan2(xDistance, yDistance);
		
		setRotation(-rot);
		
		while(Keyboard.next()) {

			int key = Keyboard.getEventKey();

			if(key == Keyboard.KEY_W) {
				if(Keyboard.getEventKeyState()) mUp = true;
				else mUp = false;
			}

			if(key == Keyboard.KEY_S) {
				if(Keyboard.getEventKeyState()) mDown = true;
				else mDown = false;
			}

			if(key == Keyboard.KEY_A) {
				if(Keyboard.getEventKeyState()) mLeft = true;
				else mLeft = false;
			}

			if(key == Keyboard.KEY_D) {
				if(Keyboard.getEventKeyState()) mRight = true;
				else mRight = false;
			}
			
			if(key == Keyboard.KEY_1) {
				if(Keyboard.getEventKeyState()) playerClass.spell1();
			}
			
			if(key == Keyboard.KEY_2) {
				if(Keyboard.getEventKeyState()) playerClass.spell2();
			}
			
			if(key == Keyboard.KEY_3) {
				if(Keyboard.getEventKeyState()) playerClass.spell3();
			}
			
			if(key == Keyboard.KEY_4) {
				if(Keyboard.getEventKeyState()) playerClass.spell4();
			}

			if(key == Keyboard.KEY_T) {
				if(Keyboard.getEventKeyState()) trackCamera = !trackCamera;
			}

			if(key == Keyboard.KEY_SPACE) {
				if(Keyboard.getEventKeyState()) playerClass.jump();
			}

			if(mUp || mDown || mRight || mLeft) mNo = false;
			else mNo = true;
		}

	}

	public void act(int delta, int gameTimer) {

		if(mUp) setVelocity((float) -(Math.sin(rotation)) * -speed * delta, (float) (Math.cos(rotation)) * -speed * delta);
		if(mDown) setVelocity((float) (Math.sin(rotation)) * -speed * delta, (float) -(Math.cos(rotation)) * -speed * delta);
		if(mLeft) setVelocity((float) -(Math.sin(rotation - Math.toRadians(90))) * -speed * delta, (float) (Math.cos(rotation - Math.toRadians(90))) * -speed * delta);
		if(mRight) setVelocity((float) (Math.sin(rotation - Math.toRadians(90))) * -speed * delta, (float) -(Math.cos(rotation - Math.toRadians(90))) * -speed * delta);
		if(mNo) setVelocity(0, 0);

		for(Projectile p : projectiles) {
			p.act(delta);
			
			if(p.ticksAlive >= p.type.maxTicks) projectilesToRemove.add(p);
		}
		
		projectiles.removeAll(projectilesToRemove);
		
		playerClass.act(delta, gameTimer);
	}

	public void move() {

		this.position.x += vX;
		this.position.y += vY;

		updateSprite();

		if(trackCamera) {
			playerCamera.setPosition(-position.x + w / 2, -position.y + h / 2);
			// playerCamera.setRotation(rotation);

		}
	}
	
	public void changeRoles() {
		
		if(playerClass instanceof PlayerMage) {
			playerClass = new PlayerWarrior(this);
		} else {
			playerClass = new PlayerMage(this);
		}
	
		sprite.setTexture(playerClass.texture);
	}

	public void draw() {

		sprite.draw();

		for(Projectile p : projectiles) {
			p.draw();
		}
	}

	@Override
	public void act(int delta) {
		// TODO Auto-generated method stub

	}


}
