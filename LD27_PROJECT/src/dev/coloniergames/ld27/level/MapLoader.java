package dev.coloniergames.ld27.level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.entity.Entity;
import dev.coloniergames.ld27.entity.Mob;
import dev.coloniergames.ld27.entity.NPC;
import dev.coloniergames.ld27.game.Game;

public class MapLoader implements Constants {

	@SuppressWarnings("resource")
	public static Map loadMap(String location) {
		Map m = null;
		
		BufferedImage image = null;
		BufferedReader mapReader = null;
		
		try {
			image = ImageIO.read(new File("res/maps/" + location + ".png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			mapReader = new BufferedReader(new FileReader("res/maps/" + location + ".map"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int mapW = image.getWidth();
		int mapH = image.getHeight();
		
		int sX = 0, sY = 0;
		
		Block[][] blocks = new Block[mapW][mapH];
		
		List<MapChange> mChs = new ArrayList<MapChange>();
		List<Teleport> teles = new ArrayList<Teleport>();
		List<Text> texts = new ArrayList<Text>();
		List<Entity> entities = new ArrayList<Entity>();
		
		for(int x = 0; x < mapW; x++) {
			for(int y = 0; y < mapH; y++) {
				int rgba = image.getRGB(x, y);
				Color c = new Color(rgba);
				int red = c.getRed();
				int green = c.getGreen();
				// int blue = c.getBlue();
				
				BlockType type = null;
				
				if(red == BlockType.BLOCK_GRASS.r) type = BlockType.BLOCK_GRASS;
				if(red == BlockType.BLOCK_WALL.r) type = BlockType.BLOCK_WALL;
				if(red == BlockType.BLOCK_HOUSE_WALL.r) type = BlockType.BLOCK_HOUSE_WALL;
				if(red == BlockType.BLOCK_FLOOR.r) type = BlockType.BLOCK_FLOOR;
				if(red == BlockType.BLOCK_WOOD.r) type = BlockType.BLOCK_WOOD;
				if(red == BlockType.BLOCK_SPACE_FLOOR.r) type = BlockType.BLOCK_SPACE_FLOOR;
				if(red == BlockType.BLOCK_SPACE_WINDOW.r) type = BlockType.BLOCK_SPACE_WINDOW;
				if(red == BlockType.BLOCK_TREE.r) type = BlockType.BLOCK_TREE;
				
				if(green == 100) { sX = x; sY = y; }
				if(green == 20) entities.add(new Mob(x * BLOCK, y * BLOCK, Game.player));
				if(green == 50) entities.add(new NPC(x * BLOCK, y * BLOCK));
				
				blocks[x][y] = new Block(x, y, type);
				
			}
		}
		
		String line = "";
		try {
			while((line = mapReader.readLine()) != null) {
				
				if(line.startsWith("#")) continue;
				
				String[] firstSplitted = line.split(":");
				
				String[] splitted = firstSplitted[0].split(" ");
				if(splitted[0].startsWith("teleport")) {
					int tX = Integer.parseInt(splitted[1]);
					int tY = Integer.parseInt(splitted[2]);
					int tDestX = Integer.parseInt(splitted[3]);
					int tDestY = Integer.parseInt(splitted[4]);
					
					teles.add(new Teleport(tX, tY, tDestX, tDestY));
				}
				
				if(splitted[0].startsWith("mapChange")) {
					int x = Integer.parseInt(splitted[1]);
					int y = Integer.parseInt(splitted[2]);
					String asd = splitted[3];
					
					mChs.add(new MapChange(x, y, asd));
				}
				
				if(firstSplitted.length >= 2) {
					if(splitted[0].startsWith("text")) {
						int tX = Integer.parseInt(splitted[1]);
						int tY = Integer.parseInt(splitted[2]);
						
						String msg = firstSplitted[1];
						
						texts.add(new Text(tX, tY, msg, true));
						
					}
					
					if(splitted[0].startsWith("textnd")) {
						int tX = Integer.parseInt(splitted[1]);
						int tY = Integer.parseInt(splitted[2]);
						
						String msg = firstSplitted[1];
						
						texts.add(new Text(tX, tY, msg, false));
					}
				}
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		m = new Map(mapW, mapH, blocks, mChs, teles, texts, entities, sX, sY);
		
		return m;
	}
}
