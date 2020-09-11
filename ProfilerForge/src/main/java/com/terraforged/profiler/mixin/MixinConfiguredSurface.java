package com.terraforged.profiler.mixin;

import com.terraforged.profiler.ProfilerForge;
import com.terraforged.profiler.Timer;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ConfiguredSurfaceBuilder.class)
public class MixinConfiguredSurface {

    private final Timer timer = Timer.of(() -> ProfilerForge.getSurfaceName(this));

    @Inject(method = "buildSurface", at = @At("HEAD"))
    public void buildHead(Random random, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, CallbackInfo ci) {
        timer.punchIn();
    }

    @Inject(method = "buildSurface", at = @At("RETURN"))
    public void buildTail(Random random, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, CallbackInfo ci) {
        timer.punchOut();
    }
}
