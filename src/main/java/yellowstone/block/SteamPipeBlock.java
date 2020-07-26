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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
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

    private final VoxelShape[] shapes;

    public SteamPipeBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false)
                .with(WEST, false).with(UP, false).with(DOWN, false).with(WATERLOGGED, false));
        shapes = new VoxelShape[64];
        for (byte i = 0; i < 64; i++) {
            shapes[i] = makeShape(6.0, (i & 0b1) > 0, (i & 0b10) > 0, (i & 0b100) > 0, (i & 0b1000) > 0,
                    (i & 0b10000) > 0, (i & 0b100000) > 0);
        }
    }

    private VoxelShape makeShape(double thickness, boolean north, boolean south, boolean east, boolean west, boolean up, boolean down) {
        double tStart = (16.0 - thickness) / 2.0;
        double tEnd = (16.0 - thickness) / 2.0 + thickness;
        VoxelShape shapeCore = Block.makeCuboidShape(tStart, tStart, tStart, tEnd, tEnd, tEnd);
        VoxelShape shapeWest = Block.makeCuboidShape(0, tStart, tStart, tStart, tEnd, tEnd);
        VoxelShape shapeEast = Block.makeCuboidShape(tEnd, tStart, tStart, 16, tEnd, tEnd);
        VoxelShape shapeNorth = Block.makeCuboidShape(tStart, tStart, 0, tEnd, tEnd, tStart);
        VoxelShape shapeSouth = Block.makeCuboidShape(tStart, tStart, tEnd, tEnd, tEnd, 16);
        VoxelShape shapeDown = Block.makeCuboidShape(tStart, 0, tStart, tEnd, tStart, tEnd);
        VoxelShape shapeUp = Block.makeCuboidShape(tStart, tEnd, tStart, tEnd, 16, tEnd);
        if (west) {
            shapeCore = VoxelShapes.or(shapeCore, shapeWest);
        }
        if (east) {
            shapeCore = VoxelShapes.or(shapeCore, shapeEast);
        }
        if (south) {
            shapeCore = VoxelShapes.or(shapeCore, shapeSouth);
        }
        if (north) {
            shapeCore = VoxelShapes.or(shapeCore, shapeNorth);
        }
        if (down) {
            shapeCore = VoxelShapes.or(shapeCore, shapeDown);
        }
        if (up) {
            shapeCore = VoxelShapes.or(shapeCore, shapeUp);
        }
        return shapeCore;
    }

    private VoxelShape getFromShapes(boolean north, boolean south, boolean east, boolean west, boolean up, boolean down) {
        byte i = 0;
        if (north) {
            i |= 0b1;
        }
        if (south) {
            i |= 0b10;
        }
        if (east) {
            i |= 0b100;
        }
        if (west) {
            i |= 0b1000;
        }
        if (up) {
            i |= 0b10000;
        }
        if (down) {
            i |= 0b100000;
        }
        return shapes[i];
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getFromShapes(state.get(NORTH), state.get(SOUTH), state.get(EAST), state.get(WEST), state.get(UP),
                state.get(DOWN));
    }

    public boolean canConnect(BlockState state) {
        return this.isBlockPipe(state.getBlock());
    }

    private boolean isBlockPipe(Block block) {
        ITag<Block> tag = BlockTags.getCollection()
                .getOrCreate(new ResourceLocation(Yellowstone.MODID, "pipe_connect"));
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
        return super.getStateForPlacement(context).with(NORTH, this.canConnect(northState))
                .with(EAST, this.canConnect(eastState)).with(SOUTH, this.canConnect(southState))
                .with(WEST, this.canConnect(westState)).with(DOWN, this.canConnect(downState))
                .with(UP, this.canConnect(upState)).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
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
