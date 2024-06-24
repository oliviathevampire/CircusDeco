package com.oliviathevampire.circus_deco.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TransparentRotatedPillarBlock extends RotatedPillarBlock {
	public static final MapCodec<TransparentRotatedPillarBlock> CODEC = simpleCodec(TransparentRotatedPillarBlock::new);

	public TransparentRotatedPillarBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public MapCodec<? extends TransparentRotatedPillarBlock> codec() {
		return CODEC;
	}

	protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
		return 1.0F;
	}

	protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return true;
	}

	@Override
	protected boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
		return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
	}
}
