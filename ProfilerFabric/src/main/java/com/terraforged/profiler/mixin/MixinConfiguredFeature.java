package com.terraforged.profiler.mixin;

import com.terraforged.profiler.ProfilerFabric;
import com.terraforged.profiler.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ConfiguredFeature.class)
public abstract class MixinConfiguredFeature<FC extends FeatureConfig, F extends Feature<FC>> {

    @Final
    @Shadow
    public F feature;

    private final Timer timer = Timer.of(() -> ProfilerFabric.getFeatureName(this));

    @Inject(method = "generate", at = @At("HEAD"))
    public void placeHead(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (feature instanceof DecoratedFeature) {
            return;
        }
        timer.punchIn();
    }

    @Inject(method = "generate", at = @At("RETURN"))
    public void placeTail(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (feature instanceof DecoratedFeature) {
            return;
        }
        timer.punchOut();
    }
}
