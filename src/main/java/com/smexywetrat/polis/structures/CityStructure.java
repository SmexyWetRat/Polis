//package com.smexywetrat.polis.structures;
//
//import java.util.Map;
//
//import com.google.common.collect.ImmutableMap;
//import com.mojang.serialization.Codec;
//
//import net.minecraft.world.gen.feature.structure.Structure;
//import net.minecraft.world.gen.feature.structure.StructureStart;
//import net.minecraft.world.gen.feature.template.TemplateManager;
//import net.minecraft.util.SharedSeedRandom;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.ChunkPos;
//import net.minecraft.util.math.MutableBoundingBox;
//import net.minecraft.util.registry.DynamicRegistries;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.provider.BiomeProvider;
//import net.minecraft.world.gen.ChunkGenerator;
//import net.minecraft.world.gen.GenerationStage;
//import net.minecraft.world.gen.Heightmap;
//import net.minecraft.world.gen.feature.NoFeatureConfig;
//
//public class CityStructure extends Structure<NoFeatureConfig> {
//	private static final Map<String, Integer> biomeMap = ImmutableMap.<String, Integer>builder().put("NONE", 0)
//			.put("TAIGA", 1).put("EXTREME_HILLS", 2).put("JUNGLE", 3).put("MESA", 4).put("PLAINS", 5).put("SAVANNA", 6)
//			.put("ICY", 7).put("THE_END", 8).put("BEACH", 9).put("FOREST", 10).put("OCEAN", 11).put("DESERT", 12)
//			.put("RIVER", 13).put("SWAMP", 14).put("MUSHROOM", 15).put("NETHER", 16).build();
//	private static int[][] map = new int[176][176];
//	
//	public CityStructure(Codec<NoFeatureConfig> codec) {
//		super(codec);
//	}
//	
//	@Override
//	public IStartFactory<NoFeatureConfig> getStartFactory() {
//		return CityStructure.Start::new;
//	}
//	
////	@Override
////	public GenerationStage.Decoration getDecorationStage() {
////		return GenerationStage.Decoration.SURFACE_STRUCTURES;
////	}
//	
//	@Override
//   protected boolean isFeatureChunk(ChunkGenerator generator, BiomeProvider biomeProviderIn, long seed,
//			SharedSeedRandom seedRand, int chunkX, int chunkZ, Biome biomeIn, ChunkPos chunkPos,
//			NoFeatureConfig config) {
//	      return true;
//	   }
//	
//	public static class Start extends StructureStart<NoFeatureConfig> {
//		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ,
//				MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
//			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
//		}
//		
//		@Override
//		public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator generator,
//				TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
//			
//			//Rotation rotation = Rotation.NONE;
//			
//			int x = (chunkX << 4) + 7;
//			int z = (chunkZ << 4) + 7;
//			
//			int y = generator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE);
//			
//			this.pieces.add(new AbstractCityManager.Piece(new BlockPos(x,y,z), chunkX, chunkZ, map));//CityHashMap.get(chunkX, chunkZ)));
//			this.calculateBoundingBox();
//		}
//	}
//}