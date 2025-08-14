package com.nobleradical.nuclearboiler;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = NuclearBoiler.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NuclearBoilerConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue EFFECTIVE_FISSION = BUILDER
            .comment("Whether to make uranium rods last 16x longer in the Nuclear Furnace, as they should")
            .define("effectiveFission", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean effectiveFission;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        effectiveFission = EFFECTIVE_FISSION.get();
    }
}
