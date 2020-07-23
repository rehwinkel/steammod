package yellowstone.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import yellowstone.main.WorldRegistry;

public class YellowstoneBiome extends Biome {

    public YellowstoneBiome() {
        super((new Biome.Builder()).surfaceBuilder(WorldRegistry.YELLOWSTONE_SURFACE, SurfaceBuilder.AIR_CONFIG).precipitation(RainType.RAIN).category(Category.EXTREME_HILLS).depth(0.125F).scale(0.05F).temperature(1.2F).downfall(0.0F).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).parent(null));
    }

}
