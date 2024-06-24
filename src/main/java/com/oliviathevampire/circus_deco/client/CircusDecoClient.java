package com.oliviathevampire.circus_deco.client;

import com.oliviathevampire.circus_deco.init.CDBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class CircusDecoClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		for (Block block : CDBlocks.CIRCUS_CLOTH_WINDOW) {
			BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.translucent());
		}
	}
}
