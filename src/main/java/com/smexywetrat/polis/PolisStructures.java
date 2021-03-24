package com.smexywetrat.polis;

import java.util.function.Supplier;

import org.apache.logging.log4j.Level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smexywetrat.polis.structures.AbstractCityManager;
import com.smexywetrat.polis.structures.CityStructure;
//import com.smexywetrat.polis.structures.AbstractCityManager;
//import com.smexywetrat.polis.structures.CityStructure;
import com.smexywetrat.polis.structures.IntersectionStructure;
import com.smexywetrat.polis.structures.IntersectionStructurePiece;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PolisStructures {
//	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES,  Polis.MODID);
	
//	public static Feature<NoFeatureConfig> ROAD_FEATURE = new RoadFeature(NoFeatureConfig.CODEC);
//	public static final RegistryObject<Feature<NoFeatureConfig>> ROADFEATUREREGISTRY = createFeature("road_feature",() -> ROAD_FEATURE);
	
	public static Structure<NoFeatureConfig> INTERSECTION /*junction?*/ = new IntersectionStructure(NoFeatureConfig.CODEC);
	public static Structure<NoFeatureConfig> CITY = new CityStructure(NoFeatureConfig.CODEC);
	public static IStructurePieceType INTERSECTION_PIECE = IntersectionStructurePiece.Piece::new;
	public static IStructurePieceType CITY_PIECE = AbstractCityManager.Piece::new;
	
	public static void registerStructures(Register<Structure<?>> event) {
		Polis.register(event.getRegistry(), INTERSECTION, "intersection");
		Polis.register(event.getRegistry(), CITY, "city");
		registerStructure(INTERSECTION, new StructureSeparationSettings(15, 7, 70031124), true);
		registerStructure(CITY, new StructureSeparationSettings(60, 40, 42113007), false);
		
		PolisStructures.registerAllPieces();
	}
	
	public static <F extends Structure<?>> void registerStructure(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
		
		if(transformSurroundingLand) {
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>> builder()
					.addAll(Structure.NOISE_AFFECTING_FEATURES)
					.add(structure)
					.build();
		}
		
		DimensionStructuresSettings.DEFAULTS = 
				ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
					.putAll(DimensionStructuresSettings.DEFAULTS)
					.put(structure, structureSeparationSettings)
					.build();
	}
	
	public static void registerAllPieces() {
		registerStructurePiece(INTERSECTION_PIECE, new ResourceLocation(Polis.MODID, "intersection_piece"));
		registerStructurePiece(CITY_PIECE, new ResourceLocation(Polis.MODID, "city_piece"));
	}
	
	static void registerStructurePiece(IStructurePieceType structurePiece, ResourceLocation rl) {
		Registry.register(Registry.STRUCTURE_PIECE,  rl,  structurePiece);
	}
	
//	private static <F extends Feature<?>> RegistryObject<F> createFeature(String name, Supplier<F> feature) {
//		return FEATURES.register(name, feature);
//	}
}
