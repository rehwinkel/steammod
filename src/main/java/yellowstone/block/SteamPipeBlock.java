package yellowstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import yellowstone.main.Yellowstone;

import java.util.Map;

public class SteamPipeBlock extends Block {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP;

    public SteamPipeBlock(Properties properties) {
        super(properties);
    }

    public boolean canConnect(BlockState state) {
        return this.isBlockPipe(state.getBlock());
    }

    private boolean isBlockPipe(Block block) {
        ITag<Block> tag = BlockTags.getCollection().getOrCreate(new ResourceLocation(Yellowstone.MODID, "pipe_connect"));
        return block.isIn(tag);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos northPos = blockpos.north();
        BlockPos eastPos = blockpos.east();
        BlockPos southPos = blockpos.south();
        BlockPos westPos = blockpos.west();
        BlockPos downPos = blockpos.down();
        BlockPos upPos = blockpos.up();
        BlockState northState = iblockreader.getBlockState(northPos);
        BlockState eastState = iblockreader.getBlockState(eastPos);
        BlockState southState = iblockreader.getBlockState(southPos);
        BlockState westState = iblockreader.getBlockState(westPos);
        BlockState downState = iblockreader.getBlockState(downPos);
        BlockState upState = iblockreader.getBlockState(upPos);
        return super.getStateForPlacement(context).with(NORTH, this.canConnect(northState)).with(EAST, this.canConnect(eastState)).with(SOUTH, this.canConnect(southState)).with(WEST, this.canConnect(westState)).with(DOWN, this.canConnect(downState)).with(UP, this.canConnect(upState)).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), this.canConnect(facingState));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN, WATERLOGGED);
    }
}
