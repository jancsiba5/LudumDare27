package dev.coloniergames.ld27.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapLoader {

	public static Map loadMap(String location) {
		Map m = null;
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("res/maps/" + location));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int mapW = image.getWidth();
		int mapH = image.getHeight();
		
		Block[][] blocks = new Block[mapW][mapH];
		
		for(int x = 0; x < mapW; x++) {
			for(int y = 0; y < mapH; y++) {
				int rgba = image.getRGB(x, y);
				Color c = new Color(rgba);
				int red = c.getRed();
				
				BlockType type = null;
				
				if(red == BlockType.BLOCK_GRASS.r) type = BlockType.BLOCK_GRASS;
				if(red == BlockType.BLOCK_WALL.r) type = BlockType.BLOCK_WALL;
				if(red == BlockType.BLOCK_HOUSE_WALL.r) type = BlockType.BLOCK_HOUSE_WALL;
				if(red == BlockType.BLOCK_FLOOR.r) type = BlockType.BLOCK_FLOOR;
				
				blocks[x][y] = new Block(x, y, type);
				
			}
		}
		
		m = new Map(mapW, mapH, blocks);
		
		return m;
	}
}
