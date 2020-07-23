package yellowstone.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

public class YellowstoneLakeFeature extends Feature<YellowstoneLakeConfig> {

    public YellowstoneLakeFeature(Codec codec) {
        super(codec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader world, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos pos, YellowstoneLakeConfig config) {
        while (pos.getY() > 5 && world.isAirBlock(pos)) {
            pos = pos.down();
        }

        if (pos.getY() <= 4) {
            return false;
        }

        for (int z = 0; z < 2 * config.size; z++) {
            for (int x = 0; x < 2 * config.size; x++) {
                if (MathHelper.sqrt((x - config.size) * (x - config.size) + (z - config.size) * (z - config.size)) < config.size) {
                    world.setBlockState(pos.add(z - config.size, 0, x - config.size), Blocks.WATER.getDefaultState(), 0);
                }
            }
        }


        return true;
    }
}
