package com.smexywetrat.polis;

import org.apache.logging.log4j.Level;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class PolisConfiguredStructures {
	public static StructureFeature<?,?> CONFIGURED_INTERSECTION = PolisStructures.INTERSECTION.configured(IFeatureConfig.NONE);
	public static StructureFeature<?,?> CONFIGURED_CITY = PolisStructures.CITY.configured(IFeatureConfig.NONE);
	
//	public static ConfiguredFeature<?,?> CONFIGURED_ROAD_FEATURE = PolisStructures.ROAD_FEATURE.configured(IFeatureConfig.NONE);
	
	public static void registerConfiguredStructures() {		
		Registry<StructureFeature<?,?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		
		Registry.register(registry, new ResourceLocation(Polis.MODID, "configured_intersection"),  CONFIGURED_INTERSECTION);
		Registry.register(registry, new ResourceLocation(Polis.MODID, "configured_city"), CONFIGURED_CITY);
		
		FlatGenerationSettings.STRUCTURE_FEATURES.put(PolisStructures.INTERSECTION, CONFIGURED_INTERSECTION);
		FlatGenerationSettings.STRUCTURE_FEATURES.put(PolisStructures.CITY, CONFIGURED_CITY);
	}
}
