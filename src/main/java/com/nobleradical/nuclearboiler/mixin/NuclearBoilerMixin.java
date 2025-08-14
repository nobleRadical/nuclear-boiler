package com.nobleradical.nuclearboiler.mixin;

import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import com.nobleradical.nuclearboiler.NuclearBoiler;
import com.nobleradical.nuclearboiler.NuclearBoilerConfig;
import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(value=NuclearFurnaceBlockEntity.class, remap = false)
public abstract class NuclearBoilerMixin extends BaseContainerBlockEntity {

    protected NuclearBoilerMixin(BlockEntityType<?> p_155076_, BlockPos p_155077_, BlockState p_155078_) {
        super(p_155076_, p_155077_, p_155078_);
    }

    @Inject(method="getMaxFissionTime",at=@At("RETURN"), cancellable = true)
    private static void maxFissionTimeCorrection(CallbackInfoReturnable<Integer> cir) {
        if (NuclearBoilerConfig.effectiveFission)
            cir.setReturnValue(cir.getReturnValue()*16);
    }

    @Inject(method="tick",at=@At("HEAD"))
    private static void tickUpdate(Level level, BlockPos blockPos, BlockState state, NuclearFurnaceBlockEntity entity, CallbackInfo ci) {
        if (level.getGameTime() % 20 == 0) {
            updateBoilers(level, blockPos);
        }
    }

    private static void updateBoilers(Level level, BlockPos blockPos) {
        // NuclearBoiler.LOGGER.debug("[[NUCLEARBOILER]] tick updating!");
        BlockPos aboveFurnace = blockPos.above(2);
        BlockPos[] boilerOptions = {aboveFurnace,aboveFurnace.south(),aboveFurnace.east(),aboveFurnace.south().east()};
        for (BlockPos boilerPos: boilerOptions) {
            BlockEntity be = level.getBlockEntity(boilerPos);
            if ((be != null) && be.getClass().equals(FluidTankBlockEntity.class))
                ((FluidTankBlockEntity)be).updateBoilerTemperature();
        }
    }

}
