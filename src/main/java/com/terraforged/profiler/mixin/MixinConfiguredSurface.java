package com.terraforged.profiler.mixin;

import com.google.common.base.Suppliers;
import com.terraforged.profiler.NameUtils;
import com.terraforged.profiler.profiler.Profiler;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.Supplier;

@Mixin(ConfiguredSurfaceBuilder.class)
public class MixinConfiguredSurface {

    private final Supplier<String> nameGetter = Suppliers.memoize(() -> NameUtils.getSurfaceName(this));

    @Inject(method = "buildSurface", at = @At("HEAD"))
    public void buildHead(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, CallbackInfo ci) {
        Profiler.push(nameGetter.get());
    }

    @Inject(method = "buildSurface", at = @At("RETURN"))
    public void buildTail(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, CallbackInfo ci) {
        Profiler.pop();
    }
}
