package com.terraforged.profiler.mixin;

import com.terraforged.profiler.ProfilerForge;
import com.terraforged.profiler.Timer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructureStart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(StructureStart.class)
public class MixinStructureStart<SC extends IFeatureConfig> {

    @Shadow
    @Final
    private Structure<SC> structure;

    private final Timer timer = Timer.of(() -> ProfilerForge.getStructureName(this.structure));

    @Inject(method = "func_230366_a_", at = @At("HEAD"))
    public void placeHead(ISeedReader world, StructureManager structures, ChunkGenerator generator, Random rand, MutableBoundingBox bounds, ChunkPos chunk, CallbackInfo ci) {
        timer.punchIn();
    }

    @Inject(method = "func_230366_a_", at = @At("RETURN"))
    public void placeTail(ISeedReader world, StructureManager structures, ChunkGenerator generator, Random rand, MutableBoundingBox bounds, ChunkPos chunk, CallbackInfo ci) {
        timer.punchOut();
    }
}
