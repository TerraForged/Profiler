package com.terraforged.profiler.mixin;

import com.mojang.datafixers.util.Either;
import com.terraforged.profiler.profiler.Profiler;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.ServerWorldLightManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Mixin(ChunkStatus.class)
public abstract class MixinChunkStatus {

    @Shadow
    public abstract String getName();

    @Inject(method = "doGenerationWork", at = @At("HEAD"))
    public void onDoWorkHead(ServerWorld worldIn, ChunkGenerator chunkGeneratorIn, TemplateManager templateManagerIn, ServerWorldLightManager lightManager, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> p_223198_5_, List<IChunk> chunks, CallbackInfoReturnable<CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> cir) {
        Profiler.push(getName());
    }

    @Inject(method = "doGenerationWork", at = @At("RETURN"))
    public void onDoWorkTail(ServerWorld worldIn, ChunkGenerator chunkGeneratorIn, TemplateManager templateManagerIn, ServerWorldLightManager lightManager, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> p_223198_5_, List<IChunk> chunks, CallbackInfoReturnable<CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> cir) {
        Profiler.pop();
    }
}
