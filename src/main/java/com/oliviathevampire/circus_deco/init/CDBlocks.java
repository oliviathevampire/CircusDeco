package com.oliviathevampire.circus_deco.init;

import com.oliviathevampire.circus_deco.CircusDeco;
import com.oliviathevampire.circus_deco.blocks.TransparentRotatedPillarBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;

public class CDBlocks {
	private static final List<DyeColor> dyeColors = List.of(
			DyeColor.WHITE,
			DyeColor.LIGHT_GRAY,
			DyeColor.GRAY,
			DyeColor.BLACK,
			DyeColor.BROWN,
			DyeColor.RED,
			DyeColor.ORANGE,
			DyeColor.YELLOW,
			DyeColor.LIME,
			DyeColor.GREEN,
			DyeColor.CYAN,
			DyeColor.LIGHT_BLUE,
			DyeColor.BLUE,
			DyeColor.PURPLE,
			DyeColor.MAGENTA,
			DyeColor.PINK
	);

	public static final Block[] CIRCUS_CLOTH = registerDyeColorBlocks("circus_cloth");
	public static final Block[] CIRCUS_CLOTH_WINDOW = registerTransparentDyeColorBlocks("circus_cloth_window");

	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> {
			for (DyeColor dyeColor : dyeColors) {
				entries.addAfter(Items.PINK_WOOL, getDyeColorBlock(CIRCUS_CLOTH, dyeColor));
				entries.addAfter(getDyeColorBlock(CIRCUS_CLOTH, dyeColor), getDyeColorBlock(CIRCUS_CLOTH_WINDOW, dyeColor));
			}
		});
	}

	private static Block register(String name, Block block) {
		Registry.register(BuiltInRegistries.BLOCK, CircusDeco.id(name), block);
		Registry.register(BuiltInRegistries.ITEM, CircusDeco.id(name), new BlockItem(block, new Item.Properties()));
		return block;
	}

	public static Block[] registerDyeColorBlocks(String baseName) {
		List<Block> blocks = new ArrayList<>();
		for (DyeColor value : dyeColors) {
			blocks.add(register(value.getName() + "_" + baseName, new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WHITE_WOOL))));
		}
		return blocks.toArray(new Block[0]);
	}
	public static Block[] registerTransparentDyeColorBlocks(String baseName) {
		List<Block> blocks = new ArrayList<>();
		for (DyeColor value : dyeColors) {
			blocks.add(register(value.getName() + "_" + baseName, new TransparentRotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WHITE_WOOL).noOcclusion())));
		}
		return blocks.toArray(new Block[0]);
	}

	public static Block getDyeColorBlock(Block[] blocks, DyeColor dyeColor) {
		return blocks[dyeColor.getId()];
	}
}
