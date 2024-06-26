package com.oliviathevampire.circus_deco;

import com.oliviathevampire.circus_deco.init.CDBlocks;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.*;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CircusDecoDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(CDGBlockStateDefinitionProvider::new);
		pack.addProvider(CDTranslationProvider::new);
		pack.addProvider(CDLootTableProvider::new);
		pack.addProvider(CDRecipeProvider::new);
		pack.addProvider(CDBlockTagsProvider::new);
	}

	private static class CDGBlockStateDefinitionProvider extends FabricModelProvider {

		public CDGBlockStateDefinitionProvider(FabricDataOutput output) {
			super(output);
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
			ModelTemplate template = new ModelTemplate(Optional.of(CircusDeco.id("block/circus_cloth")), Optional.empty(), TextureSlot.END, TextureSlot.SIDE);
			ModelTemplate template1 = new ModelTemplate(Optional.of(CircusDeco.id("block/circus_cloth_window")), Optional.empty(), TextureSlot.END, TextureSlot.SIDE);
			TexturedModel.Provider provider = TexturedModel.createDefault(TextureMapping::column, template);
			TexturedModel.Provider provider1 = TexturedModel.createDefault(TextureMapping::column, template1);
			for (Block block : CDBlocks.CIRCUS_CLOTH) {
				ResourceLocation resourceLocation = provider.create(block, blockStateModelGenerator.modelOutput);
				blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(block, resourceLocation));
			}
			for (Block block : CDBlocks.CIRCUS_CLOTH_WINDOW) {
				ResourceLocation resourceLocation = provider1
						.updateTexture(textureMapping -> textureMapping.put(TextureSlot.END, CircusDeco.id(ModelLocationUtils.getModelLocation(block).getPath().replace("_window", "_top"))))
						.create(block, blockStateModelGenerator.modelOutput);
				blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(block, resourceLocation));
			}
		}

		@Override
		public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		}
	}

	private static class CDTranslationProvider extends FabricLanguageProvider {

		protected CDTranslationProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
			for (Block block : CDBlocks.CIRCUS_CLOTH) {
				translationBuilder.add(block, WordUtils.capitalizeFully(BuiltInRegistries.BLOCK.getKey(block).getPath().replace("_", " ")));
			}
			for (Block block : CDBlocks.CIRCUS_CLOTH_WINDOW) {
				translationBuilder.add(block, WordUtils.capitalizeFully(BuiltInRegistries.BLOCK.getKey(block).getPath().replace("_", " ")));
			}
		}
	}

	private static class CDLootTableProvider extends FabricBlockLootTableProvider {
		public CDLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(output, registryLookup);
		}

		@Override
		public void generate() {
			for (Block block : CDBlocks.CIRCUS_CLOTH) {
				this.dropSelf(block);
			}
			for (Block block : CDBlocks.CIRCUS_CLOTH_WINDOW) {
				this.dropSelf(block);
			}
		}
	}

	private static class CDRecipeProvider extends FabricRecipeProvider {
		public CDRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(output, registryLookup);
		}

		@Override
		public void buildRecipes(RecipeOutput exporter) {
			for (Block block : CDBlocks.CIRCUS_CLOTH) {
				String dyeColor = BuiltInRegistries.BLOCK.getKey(block).getPath().replace("_circus_cloth", "");
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block, 8)
						.define('W', Items.WHITE_WOOL)
						.define(dyeColor.charAt(0), BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(dyeColor + "_wool")))
						.pattern(dyeColor.charAt(0) + "W")
						.pattern(dyeColor.charAt(0) + "W")
						.unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
						.unlockedBy("has_" + dyeColor + "_wool", has(BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(dyeColor + "_wool"))))
						.save(exporter, getSimpleRecipeName(block));
			}
			for (Block block : CDBlocks.CIRCUS_CLOTH_WINDOW) {
				String dyeColor = BuiltInRegistries.BLOCK.getKey(block).getPath().replace("_circus_cloth_window", "");
				String pattern = dyeColor.charAt(0) + "W" + dyeColor.charAt(0);
				ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block, 8)
						.define('W', Items.WHITE_WOOL)
						.define('G', Items.GLASS_PANE)
						.define(dyeColor.charAt(0), BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(dyeColor + "_wool")))
						.pattern(pattern)
						.pattern("WGW")
						.pattern(pattern)
						.unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
						.unlockedBy("has_glass_pane", has(Items.GLASS_PANE))
						.unlockedBy("has_" + dyeColor + "_wool", has(BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(dyeColor + "_wool"))))
						.save(exporter, getSimpleRecipeName(block));
			}
		}
	}

	private static class CDBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
		public CDBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(output, registryLookup);
		}

		@Override
		protected void addTags(HolderLookup.Provider wrapperLookup) {
			for (Block block : CDBlocks.CIRCUS_CLOTH) {
				this.tag(BlockTags.WOOL).add(reverseLookup(block));
			}
			for (Block block : CDBlocks.CIRCUS_CLOTH_WINDOW) {
				this.tag(BlockTags.WOOL).add(reverseLookup(block));
			}
		}
	}
}
