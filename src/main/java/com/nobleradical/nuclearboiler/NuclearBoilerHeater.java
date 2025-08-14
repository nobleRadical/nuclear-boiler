package com.nobleradical.nuclearboiler;

import com.github.alexmodguy.alexscaves.server.block.blockentity.ACBlockEntityRegistry;
import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.content.fluids.tank.BoilerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NuclearBoilerHeater implements BoilerHeater {

    @Override
    public float getHeat(Level level, BlockPos blockPos, BlockState state) {
        if (level.isClientSide())
            return BoilerHeater.NO_HEAT;

        BlockEntity be = level.getBlockEntity(blockPos);
        if (be != null &&
            be.getType().equals(ACBlockEntityRegistry.NUCLEAR_FURNACE.get()) &&
            ((NuclearFurnaceBlockEntity)be).isUndergoingFission()) {
            return 18;
        } else {
            return BoilerHeater.NO_HEAT;
        }
    }
}
