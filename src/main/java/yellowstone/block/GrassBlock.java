package yellowstone.block;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import yellowstone.main.BlockRegistry;

import java.util.Random;

public class GrassBlock extends net.minecraft.block.GrassBlock {

    public GrassBlock(Properties properties) {
        super(properties);
    }

    public static boolean isLit(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos blockpos = pos.up();
        BlockState blockstate = world.getBlockState(blockpos);
        if (blockstate.isIn(Blocks.SNOW) && blockstate.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockstate.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = LightEngine.func_215613_a(world, state, pos, blockstate, blockpos, Direction.UP,
                    blockstate.getOpacity(world, blockpos));
            return i < world.getMaxLightLevel();
        }
    }

    public static boolean isLitOrUnderWater(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos blockpos = pos.up();
        return isLit(state, world, pos) && !world.getFluidState(blockpos).isTagged(FluidTags.WATER);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!isLit(state, worldIn, pos)) {
            if (!worldIn.isAreaLoaded(pos, 3))
                return;
            worldIn.setBlockState(pos, BlockRegistry.DIRT.get().getDefaultState());
        } else {
            if (worldIn.getLight(pos.up()) >= 9) {
                BlockState grassState = this.getDefaultState();
                BlockState vanillaGrassState = Blocks.GRASS_BLOCK.getDefaultState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (worldIn.getBlockState(blockpos).isIn(BlockRegistry.DIRT.get()) && isLitOrUnderWater(grassState,
                            worldIn, blockpos)) {
                        worldIn.setBlockState(blockpos,
                                grassState.with(SNOWY, worldIn.getBlockState(blockpos.up()).isIn(Blocks.SNOW)));
                    } else if (worldIn.getBlockState(blockpos).isIn(Blocks.DIRT) && isLitOrUnderWater(vanillaGrassState,
                            worldIn, blockpos)) {
                        worldIn.setBlockState(blockpos,
                                vanillaGrassState.with(SNOWY, worldIn.getBlockState(blockpos.up()).isIn(Blocks.SNOW)));
                    }
                }
            }
        }
    }

}
