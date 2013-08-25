package dev.coloniergames.ld27.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.collision.AABB;
import dev.coloniergames.ld27.entity.Entity;
import dev.coloniergames.ld27.entity.Mob;
import dev.coloniergames.ld27.entity.Player;
import dev.coloniergames.ld27.entity.PlayerMage;
import dev.coloniergames.ld27.entity.PlayerWarrior;
import dev.coloniergames.ld27.gfx.Animator;
import dev.coloniergames.ld27.gfx.ParticleGenerator;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.level.Map;
import dev.coloniergames.ld27.level.MapLoader;
import dev.coloniergames.ld27.sfx.SoundPlayer;
import dev.coloniergames.ld27.util.TextureData;
import dev.coloniergames.ld27.weapon.Projectile;

public class Game implements Constants {

	Sprite s;

	public static Map map;

	static List<Entity> entities = new ArrayList<Entity>();
	static List<Entity> entitiesToRemove = new ArrayList<Entity>();

	public static Player player;

	long lastFrame;

	int delta, fpsTimer, gameTimer, fps;

	Random random = new Random();

	float red = 0, redAdd = 0.01f, blue = 1, blueAdd = 0.01f;

	public static boolean isChangeMapRequired = false;

	static String toBeLoaded = "";

	Animator timerAnimator = new Animator(0, 0, 64, 64, TextureData.timerTextures, 1000);

	public List<Projectile> allProjectiles = new ArrayList<Projectile>();
	public List<Projectile> projsToRemove = new ArrayList<Projectile>();
	public List<ParticleGenerator> generators = new ArrayList<ParticleGenerator>();
	public List<ParticleGenerator> gensToRemove = new ArrayList<ParticleGenerator>();

	// ParticleGenerator testGenerator = new ParticleGenerator(100, 100, 1, 0, 1, 1000, 100);

	public Game(){
		init();
		
		
	}

	public void init() {

		lastFrame = getTime();

		delta = getDelta();

		s = new Sprite(50, 50, TextureData.testTexture);

		newGame();

		// entities.add(player);

	}
	
	public void newGame() {
		player = new Player(0, 0);
		
		changeMap("hub");
		
		SoundPlayer.playSound("res/sound/music.wav", 0, 0, 0, 0, 0.5f, 1, true);
	}

	public void pollInput() {

		player.pollInput();

	}

	public void tick() {
		
		gensToRemove.clear();

		allProjectiles.clear();

		delta = getDelta();

		fpsTimer += delta;
		gameTimer += delta;

		if(fpsTimer >= 1000) {
			System.out.println("FPS: " + fps);
			fps = 0;
			fpsTimer = 0;
		}

		if(gameTimer >= 10000) {

			player.changeRoles();

			System.out.println("CHANGE!");

			gameTimer = 0;

			// SoundData.changeSound.play();
			
			SoundPlayer.playSound("res/sound/change.wav", 0, 0, 0, 0, 0.35f, 1, false);

		}

		// s.rotate(0.01f);
		
		

		for(Entity e : entities) {

			if(e instanceof Player) ((Player) e).act(delta, gameTimer);
			else e.act(delta);

			map.checkToMap(e);

			// e.move();

			allProjectiles.addAll(e.projectiles);

		}

		for(Projectile p : allProjectiles) {

			p.act(delta);
			
			if(p.ticksAlive >= p.type.maxTicks) {
				p.canDamage = false;
				projsToRemove.add(p);
			}

			for(Entity e : entities) {
				if(AABB.collides(p.hitBox, e.hitBox) && p.owner != e) {
					if(p.canDamage) {
						e.health -= p.type.damage;
						
						generators.add(new ParticleGenerator(e.position.x, e.position.y, 0.75f, 0.05f, 0f, 50, 10));
						
						e.knockBack(p.rotation, p.type.knockBack);

						// SoundData.hitSound.play();
						
						SoundPlayer.playSound("res/sound/hit.wav", 0, 0, 0, 0, false);
					}

					p.canDamage = false;
					projsToRemove.add(p);

					System.out.println("HIT AN ENEMY, GOT " + e.health + " HP LEFT.");
				}

				if(e.health <= 0) entitiesToRemove.add(e);
			}
		}

		allProjectiles.removeAll(projsToRemove);


		if(red >= 1) redAdd = -0.001f;
		if(red <= 0 ) redAdd = 0.001f;

		red += redAdd;
		blue -= redAdd;

		if(!entitiesToRemove.isEmpty()) {
			entities.removeAll(entitiesToRemove);

			entitiesToRemove.clear();
		}

		if(isChangeMapRequired) {
			changeMap(toBeLoaded);
		}

		// testGenerator.tick();

		for(ParticleGenerator generator : generators) {
			generator.tick();
			
			if(generator.ticksAlive >= generator.ticksMax) {
				
				gensToRemove.add(generator);
				
			}
		}
		
		generators.removeAll(gensToRemove);
		
		System.out.println(generators.size() + "    " + allProjectiles.size());

	}
	
	

	public void renderGL() {

		GL11.glPushMatrix();

		player.playerCamera.translate();

		map.draw(player);

		for(Entity e : entities) {

			e.draw();

		}

		for(Projectile p : allProjectiles) {
			p.draw();
		}

		// testGenerator.draw();1
		for(ParticleGenerator generator : generators) {
			generator.draw();
		}
		
		GL11.glPopMatrix();

		GL11.glEnd();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureData.healthBar);

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glColor3f(1, 1, 1);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(w / 2 - 84, h - 20);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(w / 2 + 84, h - 20);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(w / 2 + 84, h);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(w / 2 - 84, h);

		GL11.glEnd();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureData.healthIcon);

		GL11.glBegin(GL11.GL_QUADS);
		for(int i = 0; i < player.health; i++) {

			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f((w / 2 - 80) + i * 16, h - 18);

			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f((w / 2 - 80) + i * 16 + 16, h - 18);

			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f((w / 2 - 80) + i * 16 + 16, h - 2);

			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f((w / 2 - 80) + i * 16, h - 2);

		}

		GL11.glEnd();

		if(player.playerClass instanceof PlayerMage) {

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureData.mageText);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(red, random.nextFloat() * 0.3f, blue);

			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(w / 2, 0);

			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(w, 0);

			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(w, h / 6);

			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(w / 2, h / 6);

			GL11.glEnd();
		}

		if(player.playerClass instanceof PlayerWarrior) {

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, TextureData.warriorText);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(random.nextFloat() * 0.3f, red, blue);

			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(w / 2, 0);

			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(w, 0);

			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(w, h / 6);

			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(w / 2, h / 6);

			GL11.glEnd();
		}

		timerAnimator.draw();
		


		fps++;

	}

	public static void changeMap(String location) {

		Map m = MapLoader.loadMap(location);

		player.moveTo(m.spawnX, m.spawnY);

		entities.clear();

		map = m;
		
		entities.add(player);
		entities.addAll(map.entities);
		
		System.out.println(entities);


		isChangeMapRequired = false;

	}

	public static void requireChange(String location) {

		isChangeMapRequired = true;

		toBeLoaded = location;

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


}
