package com.oliviathevampire.circus_deco;

import com.oliviathevampire.circus_deco.init.CDBlocks;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CircusDeco implements ModInitializer {
	public static final String MOD_ID = "circus_deco";

    public static final Logger LOGGER = LoggerFactory.getLogger("Circus Deco");

	@Override
	public void onInitialize() {
		LOGGER.info("You're running Circus Deco v0.1.0 for 1.21");
		CDBlocks.init();
	}

	public static ResourceLocation id(String id) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
	}
}