package yellowstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import yellowstone.tile_entities.SmelteryTileEntity;

import javax.annotation.Nullable;

public class SmelteryBlock extends Block {

    public SmelteryBlock(Properties p) {
        super(p);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SmelteryTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof SmelteryTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity, pos);
            }
            return ActionResultType.CONSUME;
        }
    }

    @Override
    public void onReplaced(BlockState state_old, World world, BlockPos pos, BlockState state_new, boolean isMoving) {
        if (!state_old.isIn(state_new.getBlock())) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof SmelteryTileEntity) {
                ((SmelteryTileEntity) tileentity).dropItems(world, pos);
            }

            super.onReplaced(state_old, world, pos, state_new, isMoving);
        }
    }
}
