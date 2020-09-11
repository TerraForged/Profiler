package com.terraforged.profiler.mixin;

import com.terraforged.profiler.ProfilerFabric;
import com.terraforged.profiler.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;
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
public class MixinConfiguredCarver<WC extends CarverConfig> {

    @Final
    @Shadow
    private Carver<WC> carver;

    private final Timer timer = Timer.of(() -> ProfilerFabric.getCarverName(this, carver));

    @Inject(method = "carve", at = @At("HEAD"))
    public void carveHead(Chunk chunk, Function<BlockPos, Biome> function, Random random, int i, int j, int k, int l, int m, BitSet bitSet, CallbackInfoReturnable<Boolean> cir) {
        timer.punchIn();
    }

    @Inject(method = "carve", at = @At("RETURN"))
    public void carveTail(Chunk chunk, Function<BlockPos, Biome> function, Random random, int i, int j, int k, int l, int m, BitSet bitSet, CallbackInfoReturnable<Boolean> cir) {
        timer.punchOut();
    }
}
