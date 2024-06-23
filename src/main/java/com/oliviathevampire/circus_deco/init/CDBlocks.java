package com.oliviathevampire.circus_deco.init;

import com.oliviathevampire.circus_deco.CircusDeco;
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
	public static final Block[] CIRCUS_CLOTH = registerDyeColorBlocks("circus_cloth");

	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> {
			for (Block block : CIRCUS_CLOTH) {
				entries.addAfter(Items.PINK_WOOL, block);
			}
//			entries.addAfter(Items.PINK_WOOL, getDyeColorBlock(CIRCUS_CLOTH, DyeColor.WHITE));
//			entries.accept(Items.LIGHT_GRAY_WOOL);
//			entries.accept(Items.GRAY_WOOL);
//			entries.accept(Items.BLACK_WOOL);
//			entries.accept(Items.BROWN_WOOL);
//			entries.accept(Items.RED_WOOL);
//			entries.accept(Items.ORANGE_WOOL);
//			entries.accept(Items.YELLOW_WOOL);
//			entries.accept(Items.LIME_WOOL);
//			entries.accept(Items.GREEN_WOOL);
//			entries.accept(Items.CYAN_WOOL);
//			entries.accept(Items.LIGHT_BLUE_WOOL);
//			entries.accept(Items.BLUE_WOOL);
//			entries.accept(Items.PURPLE_WOOL);
//			entries.accept(Items.MAGENTA_WOOL);
//			entries.accept(Items.PINK_WOOL);
		});
	}

	private static Block register(String name, Block block) {
		Registry.register(BuiltInRegistries.BLOCK, CircusDeco.id(name), block);
		Registry.register(BuiltInRegistries.ITEM, CircusDeco.id(name), new BlockItem(block, new Item.Properties()));
		return block;
	}

	public static Block[] registerDyeColorBlocks(String baseName) {
		List<Block> blocks = new ArrayList<>();
		for (DyeColor value : DyeColor.values()) {
			blocks.add(register(value.getName() + "_" + baseName, new RotatedPillarBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.WHITE_WOOL))));
		}
		return blocks.toArray(new Block[0]);
	}

	public static Block getDyeColorBlock(Block[] blocks, DyeColor dyeColor) {
		return blocks[dyeColor.getId()];
	}
}
