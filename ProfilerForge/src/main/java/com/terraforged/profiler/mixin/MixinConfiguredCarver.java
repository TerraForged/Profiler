package com.terraforged.profiler.mixin;

import com.terraforged.profiler.ProfilerForge;
import com.terraforged.profiler.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

@Mixin(ConfiguredCarver.class)
public class MixinConfiguredCarver<WC extends ICarverConfig> {

    @Final
    @Shadow
    private WorldCarver<WC> carver;

    private final Timer timer = Timer.of(() -> ProfilerForge.getCarverName(this, carver));

    @Inject(method = "carveRegion", at = @At("HEAD"))
    public void carveHead(IChunk chunk, Function<BlockPos, Biome> biomePos, Random rand, int seaLevel, int chunkXOffset, int chunkZOffset, int chunkX, int chunkZ, BitSet carvingMask, CallbackInfoReturnable<Boolean> cir) {
        timer.punchIn();
    }

    @Inject(method = "carveRegion", at = @At("RETURN"))
    public void carveTail(IChunk chunk, Function<BlockPos, Biome> biomePos, Random rand, int seaLevel, int chunkXOffset, int chunkZOffset, int chunkX, int chunkZ, BitSet carvingMask, CallbackInfoReturnable<Boolean> cir) {
        timer.punchOut();
    }
}
