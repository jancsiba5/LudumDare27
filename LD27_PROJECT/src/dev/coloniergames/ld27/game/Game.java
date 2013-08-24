package dev.coloniergames.ld27.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.entity.Entity;
import dev.coloniergames.ld27.entity.Player;
import dev.coloniergames.ld27.entity.PlayerMage;
import dev.coloniergames.ld27.entity.PlayerWarrior;
import dev.coloniergames.ld27.gfx.Animator;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.level.Map;
import dev.coloniergames.ld27.level.MapLoader;
import dev.coloniergames.ld27.util.TextureData;

public class Game implements Constants {

	Sprite s;

	public static Map map;

	static List<Entity> entities = new ArrayList<Entity>();
	static List<Entity> entitiesToRemove = new ArrayList<Entity>();

	static Player player;

	long lastFrame;

	int delta, fpsTimer, gameTimer, fps;

	Random random = new Random();

	float red = 0, redAdd = 0.01f, blue = 1, blueAdd = 0.01f;
	
	public static boolean isChangeMapRequired = false;
	
	static String toBeLoaded = "";
	
	Animator timerAnimator = new Animator(0, 0, 32, 32, TextureData.timerTextures, 1000);

	public Game(){
		init();
	}

	public void init() {

		lastFrame = getTime();

		delta = getDelta();

		s = new Sprite(50, 50, TextureData.testTexture);

		player = new Player(0, 0);

		changeMap("testMap");

		// entities.add(player);

	}

	public void pollInput() {

		player.pollInput();

	}

	public void tick() {

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

		}

		// s.rotate(0.01f);

		for(Entity e : entities) {

			if(e instanceof Player) ((Player) e).act(delta, gameTimer);
			else e.act(delta);

			map.checkToMap(e);

			e.move();

		}


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

	}

	public void renderGL() {

		GL11.glPushMatrix();

		player.playerCamera.translate();

		map.draw(player);

		for(Entity e : entities) {

			e.draw();

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


		entities.clear();

		Map m = MapLoader.loadMap(location);

		player.moveTo(m.spawnX, m.spawnY);

		entities.add(player);

		map = m;
		
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
