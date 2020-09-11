package com.terraforged.profiler.mixin;

import com.terraforged.profiler.ProfilerFabric;
import com.terraforged.profiler.Timer;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ConfiguredSurfaceBuilder.class)
public class MixinConfiguredSurface {

    private final Timer timer = Timer.of(() -> ProfilerFabric.getSurfaceName(this));

    @Inject(method = "generate", at = @At("HEAD"))
    public void buildHead(Random random, Chunk chunk, Biome biome, int i, int j, int k, double d, BlockState defaultBlock, BlockState defaultFluid, int l, long seed, CallbackInfo ci) {
        timer.punchIn();
    }

    @Inject(method = "generate", at = @At("RETURN"))
    public void buildTail(Random random, Chunk chunk, Biome biome, int i, int j, int k, double d, BlockState defaultBlock, BlockState defaultFluid, int l, long seed, CallbackInfo ci) {
        timer.punchOut();
    }
}
