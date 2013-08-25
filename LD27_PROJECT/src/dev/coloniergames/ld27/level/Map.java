package dev.coloniergames.ld27.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.entity.Entity;
import dev.coloniergames.ld27.entity.Player;

public class Map implements Constants {

	public int MAP_WIDTH, MAP_W, MAP_HEIGHT, MAP_H;
	Block[][] blocks;
	List<MapChange> mapChanges = new ArrayList<MapChange>();
	List<Teleport> teleports = new ArrayList<Teleport>();
	List<Point> stars = new ArrayList<Point>();
	List<Text> texts = new ArrayList<Text>();
	public List<Entity> entities = new ArrayList<Entity>();
	
	Random random = new Random();


	public float spawnX, spawnY;
	public Map(int mapW, int mapH, Block[][] blocks, List<MapChange> mapChs, List<Teleport> teles, List<Text> texts, List<Entity> entities, int sX, int sY) {

		this.MAP_WIDTH = mapW;
		this.MAP_W = mapW * BLOCK;

		this.MAP_HEIGHT = mapH;
		this.MAP_H = mapH * BLOCK;

		this.blocks = blocks;
		
		this.entities = entities;

		this.mapChanges = mapChs;
		this.teleports = teles;

		this.texts = texts;

		this.spawnX = sX * BLOCK;
		this.spawnY = sY * BLOCK;

		for(int i = 0; i < 5000; i++) {
			stars.add(new Point(random.nextInt(MAP_W), random.nextInt(MAP_H)));
		}
	}

	public void checkToMap(Entity e) {
		// 1; Set Entity vX
		// 2; Keep entity on map (!!!!VERYIMPORTANT!!!!)
		// 3; Check map collision
		// 4; 1.If we collide, cancel movement 2.Otherwise go


		if(e.position.x <= 0) e.position.x = 0;
		if(e.position.x + e.sprite.getWidth() >= MAP_W) e.position.x = MAP_W - e.sprite.getWidth();

		if(e.position.y <= 0) e.position.y = 0;
		if(e.position.y + e.sprite.getHeight() >= MAP_H) e.position.y = MAP_H - e.sprite.getHeight();

		int x1, x2, y1, y2;


		for(int i = 0; i < e.sprite.getHeight(); i++) {

			// Jobbra-balra

			x1 = (int) Math.floor(((e.position.x + e.vX) / BLOCK));
			x2 = (int) Math.floor(((e.position.x + e.vX + e.sprite.getWidth()) / BLOCK));

			y1 = (int) Math.floor(((e.position.y) / BLOCK));
			y2 = (int) Math.floor(((e.position.y + i) / BLOCK));


			if(x1 >= 0 && x2 < MAP_WIDTH && y1 >= 0 && y2 < MAP_HEIGHT) {

				if(e.vX > 0) {
					// Jobbra megyünk

					if(blocks[x1][y1].type.isSolid || blocks[x2][y1].type.isSolid) {


						e.position.x = x2 * BLOCK;

						e.position.x -= e.sprite.getWidth() + 4;

						e.vX = 0;
					}

				}

				if(e.vX < 0) {

					// Balra megyünk

					if(blocks[x1][y2].type.isSolid || blocks[x2][y2].type.isSolid) {

						e.position.x = (x2) * BLOCK + 4;

						e.vX = 0;

					}
				}
			}
		}

		for(int i = 0; i < e.sprite.getWidth(); i++) {

			// Fel-le

			x1 = (int) Math.floor(((e.position.x) / BLOCK));
			x2 = (int) Math.floor(((e.position.x + i) / BLOCK));

			y1 = (int) Math.floor(((e.position.y + e.vY) / BLOCK));
			y2 = (int) Math.floor(((e.position.y + e.vY + e.sprite.getHeight()) / BLOCK));

			if(x1 >= 0 && x2 < MAP_WIDTH && y1 >= 0 && y2 < MAP_HEIGHT) {

				if(e.vY > 0) {

					// Lefele megyünk

					if(blocks[x2][y1].type.isSolid || blocks[x2][y2].type.isSolid) {


						e.position.y = y2 * BLOCK;

						e.position.y -= e.sprite.getHeight() + 4;

						e.vY = 0;

					}

				}

				if(e.vY < 0) {

					// Felfele megyünk

					if(blocks[x2][y1].type.isSolid || blocks[x2][y2].type.isSolid) {


						e.position.y = (y2) * BLOCK + 4;

						e.vY = 0;

					}
				}
			}
		}

		for(Teleport t : teleports) {
			t.teleport(e);
		}

		for(MapChange mc : mapChanges) {
			mc.changeMap(e);
		}

		if(e instanceof Player) {
			for(Text t : texts) {
				t.pickUp((Player) e);
			}
		}
		
		e.move();

	}

	public void draw(Player p) {

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_POINTS);
		for(Point point : stars) {
			GL11.glColor3f(1, 1, 1);
			GL11.glVertex2f(point.x, point.y);
		}
		GL11.glEnd();

		int xTile = (int) (-p.playerCamera.position.x / BLOCK);
		int yTile = (int) (-p.playerCamera.position.y / BLOCK);

		int W = (int) (w / BLOCK) + 2;
		int H = (int) (h / BLOCK) + 2;

		if(xTile <= 0) xTile = 0;
		if(xTile + W >= MAP_WIDTH) xTile = MAP_WIDTH - W;

		if(yTile <= 0) yTile = 0;
		if(yTile + H >= MAP_HEIGHT) yTile = MAP_HEIGHT - H;

		for(int i = xTile; i < xTile + W; i++) {
			for(int j = yTile; j < yTile + H; j++) {
				blocks[i][j].draw();
			}
		}

		for(Teleport t : teleports) {
			t.draw();
		}

		for(MapChange mc : mapChanges) {
			mc.draw();
		}
		
		for(Text t : texts) {
			t.draw();
		}

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	private class Point {
		public float x, y;

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

}
