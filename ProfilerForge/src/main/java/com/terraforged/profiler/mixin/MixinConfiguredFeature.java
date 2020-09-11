package com.terraforged.profiler.mixin;

import com.terraforged.profiler.ProfilerForge;
import com.terraforged.profiler.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ConfiguredFeature.class)
public abstract class MixinConfiguredFeature {

    private final Timer timer = Timer.of(() -> ProfilerForge.getFeatureName(this));

    @Inject(method = "func_242765_a", at = @At("HEAD"))
    public void placeHead(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        timer.punchIn();
    }

    @Inject(method = "func_242765_a", at = @At("RETURN"))
    public void placeTail(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        timer.punchOut();
    }
}
