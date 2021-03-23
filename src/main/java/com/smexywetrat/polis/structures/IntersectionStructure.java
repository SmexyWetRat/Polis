package com.smexywetrat.polis.structures;

import org.apache.logging.log4j.Level;

import com.mojang.serialization.Codec;
import com.smexywetrat.polis.Polis;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class IntersectionStructure extends Structure<NoFeatureConfig>{
	public IntersectionStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}
	
	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return IntersectionStructure.Start::new;
	}
	
//	@Override
//	public GenerationStage.Decoration getDecorationStage() {
//		return GenerationStage.Decoration.UNDERGROUND_STRUCTURES;
//	}
	
	public static class Start extends StructureStart<NoFeatureConfig> {
		
		public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

		@Override
		public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator generator,
				TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn,
				NoFeatureConfig config) {
			Rotation rotation = Rotation.NONE; //values()[this.rand.nextInt(Rotation.values().length)];
			
			int x = (chunkX << 4) + 7;
			int z = (chunkZ << 4) + 7;

			int y = generator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
			BlockPos blockpos = new BlockPos(x, y, z);
			IntersectionStructurePiece.start(templateManagerIn, blockpos, rotation, this.pieces, this.random);
			
			this.calculateBoundingBox();
			
			//UVMod.LOGGER.log(Level.DEBUG, "I'm an intersection at " + blockpos.getX() + "," + blockpos.getZ());
		}
	}
}
