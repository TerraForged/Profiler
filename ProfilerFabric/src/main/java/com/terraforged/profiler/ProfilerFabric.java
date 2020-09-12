package com.terraforged.profiler;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

public class ProfilerFabric implements ModInitializer, ServerLifecycleEvents.ServerStarting {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOG.info("Setting up chunk-gen profiler");
        Profiler.attachLogger(LOG::info);
        ServerLifecycleEvents.SERVER_STARTING.register(this);
    }

    @Override
    public void onServerStarting(MinecraftServer minecraftServer) {
        LOG.info("Resetting profiler");
        Profiler.reset();
    }

    public static String getSurfaceName(Object object) {
        ConfiguredSurfaceBuilder<?> surface = null;
        if (object instanceof ConfiguredSurfaceBuilder) {
            surface = (ConfiguredSurfaceBuilder<?>) object;
        }

        return getName(surface, s -> s.surfaceBuilder, Registry.SURFACE_BUILDER, BuiltinRegistries.CONFIGURED_SURFACE_BUILDER);
    }

    public static String getFeatureName(Object object) {
        ConfiguredFeature<?, ?> feature = null;
        if (object instanceof ConfiguredFeature) {
            feature = (ConfiguredFeature<?, ?>) object;
        }
        return getName(feature, c -> c.feature, Registry.FEATURE, BuiltinRegistries.CONFIGURED_FEATURE);
    }

    public static String getStructureName(Object object) {
        StructureFeature<?> structure = null;
        if (object instanceof StructureFeature) {
            structure = (StructureFeature<?>) object;
        }
        return getName(structure, s -> null, Registry.STRUCTURE_FEATURE, Registry.STRUCTURE_FEATURE);
    }

    public static String getCarverName(Object object, Carver<?> worldCarver) {
        ConfiguredCarver<?> carver = null;
        if (object instanceof ConfiguredCarver) {
            carver = (ConfiguredCarver<?>) object;
        }
        return getName(carver, c -> worldCarver, Registry.CARVER, BuiltinRegistries.CONFIGURED_CARVER);
    }

    private static <T, CT> String getName(CT configured, Function<CT, T> getter, Registry<T> typeRegistry, Registry<CT> configuredRegistry) {
        Identifier configuredName = configuredRegistry.getId(configured);
        if (configuredName != null) {
            return toString(configuredName);
        }

        T t = getter.apply(configured);
        if (t == null) {
            return null;
        }

        Identifier typeName = typeRegistry.getId(t);
        if (typeName != null) {
            return toString(typeName);
        }

        return null;
    }

    private static String toString(Identifier identifier) {
        if (identifier.getNamespace().equals("minecraft")) {
            return identifier.getPath();
        }
        return identifier.toString();
    }
}
