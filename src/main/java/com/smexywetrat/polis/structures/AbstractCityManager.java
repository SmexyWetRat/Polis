package com.smexywetrat.polis.structures;

import java.awt.Point;
import java.util.Random;

import org.apache.logging.log4j.Level;

import com.smexywetrat.polis.Polis;
import com.smexywetrat.polis.PolisStructures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class AbstractCityManager {
	
//	public abstract int[][] generateMap(ChunkGenerator generator, BiomeManager biomeManager, int chunkX, int chunkZ);
//	
//	public abstract int generatePieces();
	
	public static class Piece extends StructurePiece {
		private static final BlockState[] colorMap= {
				Blocks.BLACK_STAINED_GLASS.defaultBlockState(),
				Blocks.LIGHT_GRAY_STAINED_GLASS.defaultBlockState(),
				Blocks.GRAY_STAINED_GLASS.defaultBlockState(),
				Blocks.GREEN_STAINED_GLASS.defaultBlockState(),
				Blocks.ORANGE_STAINED_GLASS.defaultBlockState(),
				Blocks.LIME_STAINED_GLASS.defaultBlockState(),
				Blocks.BROWN_STAINED_GLASS.defaultBlockState(),
				Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState(),
				Blocks.BLACK_STAINED_GLASS.defaultBlockState(),
				Blocks.YELLOW_STAINED_GLASS.defaultBlockState(),
				Blocks.GREEN_STAINED_GLASS.defaultBlockState(),
				Blocks.CYAN_STAINED_GLASS.defaultBlockState(),
				Blocks.YELLOW_STAINED_GLASS.defaultBlockState(),
				Blocks.BLUE_STAINED_GLASS.defaultBlockState(),
				Blocks.GREEN_STAINED_GLASS.defaultBlockState(),
				Blocks.RED_STAINED_GLASS.defaultBlockState(),
				Blocks.BLACK_STAINED_GLASS.defaultBlockState()
			};
		private static Point [][][][] map = new Point[11][11][16][16];
		private static int chunkX;
		private static int chunkZ;
		
		public Piece(BlockPos blockPos, int chunkXin, int chunkZin, Point[][] mapIn) {
			super(PolisStructures.CITY_PIECE, 0);
			this.boundingBox = new MutableBoundingBox(blockPos.getX()-90, blockPos.getY()-30, blockPos.getZ()-90,
					blockPos.getX() + 90, blockPos.getY()+30, blockPos.getZ() + 90);
			
			chunkX = chunkXin;
			chunkZ = chunkZin;
			Polis.LOGGER.log(Level.DEBUG, "Chunk x is " + chunkX + " and chunkz is + " + chunkZ);
			
			for(int i=0;i<176;i++) 
				for (int j=0;j<176;j++) 
//					map[i/16][j/16][i%16][j%16] = mapIn[i/8][j/8];
					map[i/16][j/16][i%16][j%16] = mapIn[i][j];
		}
		
		public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
			super(PolisStructures.CITY_PIECE, tagCompound);
		}
		
		@Override
		public boolean postProcess(ISeedReader worldIn, StructureManager structureManager,
		ChunkGenerator chunkGenerator, Random rand, MutableBoundingBox mbb, ChunkPos chunkPos,
		BlockPos blockPos) {
//			Polis.LOGGER.log(Level.DEBUG, "post process called. chunkx is " + chunkX + " and chunkz is " + chunkZ);
			
			int x = chunkPos.x << 4;
			int z = chunkPos.z << 4;
			
			//Iterates through chunks in map, centered around chunkX and chunkZ
			for(int i=0;i<11;i++) {
				for(int j=0;j<11;j++) {
//					Polis.LOGGER.log(Level.DEBUG, "Checking if " + chunkPos.x + "," + chunkPos.z + "  " + (chunkX+i-5) + "," + (chunkZ+j-5));
					if((chunkPos.x == chunkX+i-5) && (chunkPos.z == chunkZ+j-5)) {
						
						//Once selected chunk is found, iterates through blocks in chunk, applying info from map
						for(int k=0;k<16;k++) {
							for(int l=0;l<16;l++) {
								//int color = map[i][j][k][l];
//								int y = (int)map[i][j][k][l];
								int y = map[i][j][k][l].x + 5;//worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG,x+k,z+l) + 5;
//								if (y<10) y = 70;
								BlockState color = colorMap[map[i][j][k][l].y];//map[i][j][k][l] == 1 ? Blocks.YELLOW_STAINED_GLASS.defaultBlockState() : Blocks.RED_STAINED_GLASS.defaultBlockState();
//								String biome = worldIn.getBiome(new BlockPos(x+k, y, z+l)).getBiomeCategory().toString();
								
//								if (biome.equals("OCEAN")) color = (color.equals(Blocks.RED_STAINED_GLASS.defaultBlockState())?Blocks.CYAN_STAINED_GLASS.defaultBlockState():Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState());
//								else if (biome.equals("RIVER")) color = (color.equals(Blocks.RED_STAINED_GLASS.defaultBlockState())?Blocks.PURPLE_STAINED_GLASS.defaultBlockState():Blocks.BLUE_STAINED_GLASS.defaultBlockState());
//								if (map[i][j][k][l] == -1)
//									color = Blocks.YELLOW_WOOL.defaultBlockState();
								this.placeBlock(worldIn, color, x+k, y-1, z+l, this.boundingBox);
								if(y>50)
									for(int m=0;m<worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, x+k, z+l);m++) 
									this.placeBlock(worldIn, Blocks.AIR.defaultBlockState(), x+k, y+m, z+l, this.boundingBox);
//								Polis.LOGGER.log(Level.DEBUG, "Just played a block " + color + " at " + x+k + "," + y+5 + "," + z+l);
//								UVMod.LOGGER.log(Level.DEBUG,"Hi! I'm block " + (x+k) + "," + (z+l) + " and my average height difference is " + map[i][j][k][l]);
								//this.setBlockState(worldIn, colorMap[map[i][j][k][l]], x+k, y+5, z+l, this.boundingBox);
//								if (color == 0) 
//									this.setBlockState(worldIn, Blocks.LIGHT_GRAY_WOOL.getDefaultState(),x+k, y+5, z+l, this.boundingBox);
//								if (color == 1)
//									this.setBlockState(worldIn, Blocks.YELLOW_WOOL.getDefaultState(),x+k, y+5, z+l, this.boundingBox);
//								if (color == 2)
//									this.setBlockState(worldIn, Blocks.BLUE_WOOL.getDefaultState(),x+k, y+5, z+l, this.boundingBox);
							}
						}
						
						return true;
					}
				}
			}
			
			return true;
		}

		@Override
		protected void addAdditionalSaveData(CompoundNBT tagCompound) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
