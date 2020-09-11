package com.terraforged.profiler.mixin;

import com.google.common.base.Suppliers;
import com.terraforged.profiler.NameUtils;
import com.terraforged.profiler.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Supplier;

@Mixin(ConfiguredFeature.class)
public abstract class MixinConfiguredFeature<FC extends IFeatureConfig, F extends Feature<FC>> {

    @Final
    @Shadow
    public F feature;

    private final Supplier<String> nameGetter = Suppliers.memoize(() -> NameUtils.getFeatureName(this));

    @Inject(method = "func_242765_a", at = @At("HEAD"))
    public void placeHead(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (feature instanceof DecoratedFeature) {
            return;
        }
        Profiler.push(nameGetter.get());
    }

    @Inject(method = "func_242765_a", at = @At("RETURN"))
    public void placeTail(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (feature instanceof DecoratedFeature) {
            return;
        }
        Profiler.pop();
    }
}
