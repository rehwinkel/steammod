package yellowstone.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import yellowstone.main.BlockRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YellowstoneSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {

    private final List<Layer> layers;

    public YellowstoneSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
        layers = new ArrayList<>();
        layers.add(new Layer(BlockRegistry.GRASS.get().getDefaultState(), 1, 0));
        layers.add(new Layer(BlockRegistry.DIRT.get().getDefaultState(), 3, 2));
        layers.add(new Layer(BlockRegistry.LAVASTONE.get().getDefaultState(), 6, 3));
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        int height = startHeight - 1;
        for (Layer l : layers) {
            int layerHeight = l.getSize() + random.nextInt(l.getRandom() + 1);
            for (int i = 0; i < layerHeight; i++) {
                chunkIn.setBlockState(new BlockPos(x, height, z), l.getBlock(), false);
                height--;
            }
        }
    }

}
