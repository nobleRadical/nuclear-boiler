package com.nobleradical.nuclearboiler;

import com.github.alexmodguy.alexscaves.server.block.NuclearFurnaceBlock;
import com.mojang.logging.LogUtils;
import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.content.fluids.tank.BoilerHeaters;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import static com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry.NUCLEAR_FURNACE;
import static com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry.NUCLEAR_FURNACE_COMPONENT;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NuclearBoiler.MODID)
public class NuclearBoiler
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "nuclearboiler";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public NuclearBoiler(FMLJavaModLoadingContext context)
    {

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, NuclearBoilerConfig.SPEC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

        if (NuclearBoilerConfig.effectiveFission) {
            LOGGER.info("Fission has been made effective.");
        }

        BoilerHeater.REGISTRY.register(NUCLEAR_FURNACE.get(), new NuclearBoilerHeater());
        BoilerHeater.REGISTRY.register(NUCLEAR_FURNACE_COMPONENT.get(), new NuclearBoilerComponentHeater());
    }
}
