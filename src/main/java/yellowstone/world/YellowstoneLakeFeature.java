package yellowstone.world;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

public class YellowstoneLakeFeature extends Feature {
    public YellowstoneLakeFeature(Codec p_i231953_1_) {
        super(p_i231953_1_);
    }

    @Override
    public boolean func_230362_a_(ISeedReader p_230362_1_, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random p_230362_4_, BlockPos p_230362_5_, IFeatureConfig p_230362_6_) {
        return false;
    }
}
