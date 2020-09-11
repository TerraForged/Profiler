package com.terraforged.profiler.mixin;

import com.terraforged.profiler.ProfilerFabric;
import com.terraforged.profiler.Timer;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(StructureStart.class)
public class MixinStructureStart<SC extends FeatureConfig> {

    @Shadow
    @Final
    private StructureFeature<SC> feature;

    private final Timer timer = Timer.of(() -> ProfilerFabric.getStructureName(this.feature));

    @Inject(method = "generateStructure", at = @At("HEAD"))
    public void placeHead(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos, CallbackInfo ci) {
        timer.punchIn();
    }

    @Inject(method = "generateStructure", at = @At("RETURN"))
    public void placeTail(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox blockBox, ChunkPos chunkPos, CallbackInfo ci) {
        timer.punchOut();
    }
}
