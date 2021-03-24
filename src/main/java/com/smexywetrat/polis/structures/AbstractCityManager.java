package com.smexywetrat.polis.structures;

import java.util.Random;

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
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class AbstractCityManager {
	
	public int[][] generateMap;
	
	public int generatePieces;
	
	public static class Piece extends StructurePiece {
		private int [][][][] map = new int[11][11][16][16];
		private int chunkX;
		private int chunkZ;
		
		public Piece(BlockPos blockPos, int chunkXin, int chunkZin, int[][] mapIn) {
			super(PolisStructures.CITY_PIECE, 0);
			this.boundingBox = new MutableBoundingBox(blockPos.getX()-90, blockPos.getY()-30, blockPos.getZ()-90,
					blockPos.getX() + 90, blockPos.getY()+30, blockPos.getZ() + 90);
			
			chunkX = chunkXin;
			chunkZ = chunkZin;
			for(int i=0;i<176;i++) 
				for (int j=0;j<176;j++) 
					map[i/16][j/16][i%16][j%16] = mapIn[i/8][j/8];			
		}
		
		public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
			super(PolisStructures.CITY_PIECE, tagCompound);
		}
		
		@Override
		public boolean postProcess(ISeedReader worldIn, StructureManager structureManager,
		ChunkGenerator chunkGenerator, Random rand, MutableBoundingBox mbb, ChunkPos chunkPos,
		BlockPos blockPos) {
			
			int x = chunkPos.x << 4;
			int z = chunkPos.z << 4;
			
			//Iterates through chunks in map, centered around chunkX and chunkZ
			for(int i=0;i<11;i++) {
				for(int j=0;j<11;j++) {
					if((chunkPos.x == chunkX+i-5) && (chunkPos.z == chunkZ+j-5)) {
						
						//Once selected chunk is found, iterates through blocks in chunk, applying info from map
						for(int k=0;k<16;k++) {
							for(int l=0;l<16;l++) {
								//int color = map[i][j][k][l];
//								int y = (int)map[i][j][k][l];
								int y = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG,x+k,z+l) + 5;
//								if (y<10) y = 70;
								BlockState color = map[i][j][k][l] == 2 ? Blocks.RED_WOOL.defaultBlockState() : Blocks.GREEN_WOOL.defaultBlockState();
								String biome = worldIn.getBiome(new BlockPos(x+k, y, z+l)).getBiomeCategory().toString();
								
								if (biome.equals("OCEAN")) color = (color.equals(Blocks.GREEN_WOOL.defaultBlockState())?Blocks.CYAN_WOOL.defaultBlockState():Blocks.LIGHT_BLUE_WOOL.defaultBlockState());
								else if (biome.equals("RIVER")) color = (color.equals(Blocks.GREEN_WOOL.defaultBlockState())?Blocks.PURPLE_WOOL.defaultBlockState():Blocks.BLUE_WOOL.defaultBlockState());
								if (map[i][j][k][l] == -1)
									color = Blocks.YELLOW_WOOL.defaultBlockState();
								this.placeBlock(worldIn, color, x+k, y-1, z+l, this.boundingBox);
								if(y>50)
									for(int m=0;m<worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING, x+k, z+l);m++) 
									this.placeBlock(worldIn, Blocks.AIR.defaultBlockState(), x+k, y+m, z+l, this.boundingBox);
//								UVMod.LOGGER.log(Level.DEBUG, "Just played a block at " + x+k + "," + y+5 + "," + z+l);
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
