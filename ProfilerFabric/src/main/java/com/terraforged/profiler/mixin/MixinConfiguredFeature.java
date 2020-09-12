package com.terraforged.profiler.mixin;

import com.terraforged.profiler.ProfilerFabric;
import com.terraforged.profiler.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ConfiguredFeature.class)
public abstract class MixinConfiguredFeature {

    private final Timer timer = Timer.of(() -> ProfilerFabric.getFeatureName(this));

    @Inject(method = "generate", at = @At("HEAD"))
    public void placeHead(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        timer.punchIn();
    }

    @Inject(method = "generate", at = @At("RETURN"))
    public void placeTail(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        timer.punchOut();
    }
}
