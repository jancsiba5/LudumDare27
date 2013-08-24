package dev.coloniergames.ld27.level;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.entity.Entity;
import dev.coloniergames.ld27.entity.Player;

public class Map implements Constants {

	public int MAP_WIDTH, MAP_W, MAP_HEIGHT, MAP_H;
	Block[][] blocks;
	public Map(int mapW, int mapH, Block[][] blocks) {

		this.MAP_WIDTH = mapW;
		this.MAP_W = mapW * BLOCK;

		this.MAP_HEIGHT = mapH;
		this.MAP_H = mapH * BLOCK;

		this.blocks = blocks;

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

					if(blocks[x1][y2].type.isSolid || blocks[x2][y2].type.isSolid) {


						e.position.x = x2 * BLOCK;

						e.position.x -= e.sprite.getWidth() + 1;

						e.vX = 0;
					}

				}

				if(e.vX < 0) {

					// Balra megyünk

					if(blocks[x1][y2].type.isSolid || blocks[x2][y2].type.isSolid) {


						e.position.x = (x2) * BLOCK;

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

						e.position.y -= e.sprite.getHeight();

						e.vY = 0;

					}

				}

				if(e.vY < 0) {

					// Felfele megyünk

					if(blocks[x2][y1].type.isSolid || blocks[x2][y2].type.isSolid) {


						e.position.y = (y2) * BLOCK;

						e.vY = 0;

					}
				}
			}
		}

		
	}

	public void draw(Player p) {

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

	}

}
