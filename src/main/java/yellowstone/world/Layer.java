package yellowstone.world;

import net.minecraft.block.BlockState;

public class Layer {

    private final BlockState block;
    private final int size;
    private final int random;

    public Layer(BlockState block, int size, int random) {
        this.block = block;
        this.size = size;
        this.random = random;
    }

    public BlockState getBlock() {
        return block;
    }

    public int getRandom() {
        return random;
    }

    public int getSize() {
        return size;
    }

}
