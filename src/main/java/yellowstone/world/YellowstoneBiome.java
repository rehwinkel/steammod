package yellowstone.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import yellowstone.main.WorldRegistry;

public class YellowstoneBiome extends Biome {

    public YellowstoneBiome() {
        super((new Biome.Builder()).surfaceBuilder(WorldRegistry.YELLOWSTONE_SURFACE, SurfaceBuilder.AIR_CONFIG)
                .precipitation(RainType.RAIN).category(Category.EXTREME_HILLS).depth(0.2F).scale(0.2F).temperature(25F)
                .downfall(8F).func_235097_a_(
                        (new BiomeAmbience.Builder()).func_235246_b_(0xff0000ff).func_235248_c_(0xffff0000)
                                .func_235239_a_(0xffff00ff).func_235243_a_(MoodSoundAmbience.field_235027_b_)
                                .func_235238_a_()).parent(null)
                .func_235098_a_(ImmutableList.of(new Biome.Attributes(0.0F, 0.0F, 0.0F, 0.0F, 1.0F))));
    }

}
