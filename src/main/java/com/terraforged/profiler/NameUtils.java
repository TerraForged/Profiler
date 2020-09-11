package com.terraforged.profiler;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.function.Function;

public class NameUtils {

    public static String getSurfaceName(Object object) {
        ConfiguredSurfaceBuilder<?> surface = null;
        if (object instanceof ConfiguredSurfaceBuilder) {
            surface = (ConfiguredSurfaceBuilder<?>) object;
        }
        return getName(surface, s -> s.builder, Registry.SURFACE_BUILDER, WorldGenRegistries.field_243651_c);
    }

    public static String getFeatureName(Object object) {
        ConfiguredFeature<?, ?> feature = null;
        if (object instanceof ConfiguredFeature) {
            feature = (ConfiguredFeature<?, ?>) object;
        }
        return getName(feature, c -> c.feature, Registry.FEATURE, WorldGenRegistries.field_243653_e);
    }

    public static String getStructureName(Object object) {
        Structure<?> structure = null;
        if (object instanceof Structure) {
            structure = (Structure<?>) object;
        }
        return getName(structure, s -> null, Registry.STRUCTURE_FEATURE, Registry.STRUCTURE_FEATURE);
    }

    public static String getCarverName(Object object, WorldCarver<?> worldCarver) {
        ConfiguredCarver<?> carver = null;
        if (object instanceof ConfiguredCarver) {
            carver = (ConfiguredCarver<?>) object;
        }
        return getName(carver, c -> worldCarver, Registry.CARVER, WorldGenRegistries.field_243652_d);
    }

    private static <T, CT> String getName(CT configured, Function<CT, T> getter, Registry<T> typeRegistry, Registry<CT> configuredRegistry) {
        ResourceLocation configuredName = configuredRegistry.getKey(configured);
        if (configuredName != null) {
            return configuredName.toString();
        }

        T t = getter.apply(configured);
        if (t == null) {
            return null;
        }

        ResourceLocation typeName = typeRegistry.getKey(t);
        if (typeName != null) {
            return typeName.toString();
        }

        return null;
    }
}
