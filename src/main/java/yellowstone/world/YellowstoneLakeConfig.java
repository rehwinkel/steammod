package yellowstone.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class YellowstoneLakeConfig implements IFeatureConfig {

    public static final Codec<YellowstoneLakeConfig> CODEC = RecordCodecBuilder.create((builder) -> builder.group(Codec.INT.fieldOf("size").forGetter((inst) -> inst.size)).apply(builder, YellowstoneLakeConfig::new));

    public final int size;

    public YellowstoneLakeConfig(int size) {
        this.size = size;
    }

}
