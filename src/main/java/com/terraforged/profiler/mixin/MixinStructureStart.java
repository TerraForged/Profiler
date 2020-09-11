package com.terraforged.profiler.mixin;

import com.google.common.base.Suppliers;
import com.terraforged.profiler.NameUtils;
import com.terraforged.profiler.profiler.Profiler;
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
import java.util.function.Supplier;

@Mixin(StructureStart.class)
public class MixinStructureStart<SC extends IFeatureConfig> {

    @Shadow @Final private Structure<SC> structure;

    private final Supplier<String> nameGetter = Suppliers.memoize(() -> NameUtils.getStructureName(this.structure));

    @Inject(method = "func_230366_a_", at = @At("HEAD"))
    public void placeHead(ISeedReader p_230366_1_, StructureManager p_230366_2_, ChunkGenerator p_230366_3_, Random p_230366_4_, MutableBoundingBox p_230366_5_, ChunkPos p_230366_6_, CallbackInfo ci) {
        Profiler.push(nameGetter.get());
    }

    @Inject(method = "func_230366_a_", at = @At("RETURN"))
    public void placeTail(ISeedReader p_230366_1_, StructureManager p_230366_2_, ChunkGenerator p_230366_3_, Random p_230366_4_, MutableBoundingBox p_230366_5_, ChunkPos p_230366_6_, CallbackInfo ci) {
        Profiler.pop();
    }
}
