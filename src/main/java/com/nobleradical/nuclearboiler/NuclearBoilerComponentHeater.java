package com.nobleradical.nuclearboiler;

import com.github.alexmodguy.alexscaves.server.block.NuclearFurnaceComponentBlock;
import com.github.alexmodguy.alexscaves.server.block.blockentity.ACBlockEntityRegistry;
import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import com.simibubi.create.api.boiler.BoilerHeater;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry.NUCLEAR_FURNACE_COMPONENT;

public class NuclearBoilerComponentHeater implements BoilerHeater {

    @Override
    public float getHeat(Level level, BlockPos blockPos, BlockState state) {
        if (level.isClientSide())
            return BoilerHeater.NO_HEAT;


        BlockPos furnacePos = NuclearFurnaceComponentBlock.getCornerForFurnace(level, blockPos, true);
        if (furnacePos != null &&
            level.getBlockState(furnacePos).hasBlockEntity() &&
            level.getBlockEntity(furnacePos).getType().equals(ACBlockEntityRegistry.NUCLEAR_FURNACE.get())) {
            NuclearFurnaceBlockEntity be = (NuclearFurnaceBlockEntity) level.getBlockEntity(furnacePos);
            if (be.isUndergoingFission()) {
                return 18;
            } else {
                return BoilerHeater.NO_HEAT;
            }
        }
        return BoilerHeater.NO_HEAT;
    }
}
