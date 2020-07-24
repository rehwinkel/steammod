package yellowstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassPathBlock;
import net.minecraft.item.BlockItemUseContext;
import yellowstone.main.BlockRegistry;

public class PathBlock extends GrassPathBlock {

    public PathBlock(Properties properties) {
        super(properties);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return !this.getDefaultState().isValidPosition(context.getWorld(), context.getPos()) ? Block.nudgeEntitiesWithNewState(this.getDefaultState(), BlockRegistry.DIRT.get().getDefaultState(), context.getWorld(), context.getPos()) : super.getStateForPlacement(context);
    }

}
