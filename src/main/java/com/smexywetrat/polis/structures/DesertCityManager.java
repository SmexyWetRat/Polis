package com.smexywetrat.polis.structures;

import java.awt.Point;

import org.apache.logging.log4j.Level;

import com.smexywetrat.polis.Polis;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;

public class DesertCityManager extends AbstractCityManager{

	public static int[][] generateMap(ChunkGenerator generator, BiomeProvider biomeProvider, int chunkX, int chunkZ) {
		int[][] map = new int[176][176];
		for (int i=0;i<176;i++) {
			for (int j=0;j<176;j++) {
				map[i][j] = 1;
			}
		}
		return map;
	}
	
	//temp
	public static Point[][] generateMap(ChunkGenerator generator, BiomeProvider biomeProvider, int chunkX, int chunkZ, Point[][] mapIn) {
		Point[][] map = new Point[176][176];
		for (int i=0;i<176;i++) {
			for (int j=0;j<176;j++) {
				map[i][j] = mapIn[i/2][j/2];
//				System.out.print(map[i][j].y + " ");
			}
//			System.out.println();
		}
		return map;
	}
	
	public int generatePieces() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
