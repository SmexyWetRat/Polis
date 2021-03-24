package com.smexywetrat.polis;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import org.apache.logging.log4j.Logger;

@Mod("polis")
public class Polis {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "polis";
	
	public Polis() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addGenericListener(Structure.class, this::onRegisterStructures);
		
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
		
		forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
	}
	
	public void onRegisterStructures(final RegistryEvent.Register<Structure<?>> event) {
		PolisStructures.registerStructures(event);
		PolisConfiguredStructures.registerConfiguredStructures();
	}
	
	public void biomeModification(final BiomeLoadingEvent event) {
		event.getGeneration().getStructures().add(() -> PolisConfiguredStructures.CONFIGURED_INTERSECTION);
		event.getGeneration().getStructures().add(() -> PolisConfiguredStructures.CONFIGURED_CITY);
	}
	
	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if(event.getWorld() instanceof ServerWorld) {
    		ServerWorld serverWorld = (ServerWorld)event.getWorld();
    		
    		if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
    				serverWorld.dimension().equals(World.OVERWORLD)) {
    			return;
    		}
    		
    		Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
    		tempMap.put(PolisStructures.INTERSECTION, DimensionStructuresSettings.DEFAULTS.get(PolisStructures.INTERSECTION));
    		tempMap.put(PolisStructures.CITY, DimensionStructuresSettings.DEFAULTS.get(PolisStructures.CITY));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
		}
	}
	
	public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) {
		entry.setRegistryName(new ResourceLocation(Polis.MODID, registryKey));
		registry.register(entry);
		return entry;
	}
}
