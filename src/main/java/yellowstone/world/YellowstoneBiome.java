package yellowstone.world;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import yellowstone.main.WorldRegistry;

public class YellowstoneBiome extends Biome {

    public YellowstoneBiome() {
        super((new Biome.Builder()).surfaceBuilder(WorldRegistry.YELLOWSTONE_SURFACE, SurfaceBuilder.AIR_CONFIG)
                .precipitation(RainType.RAIN).category(Category.EXTREME_HILLS).depth(0.25F).scale(0.1F)
                .temperature(1.2F).downfall(0.1F).func_235097_a_(
                        (new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011)
                                .func_235239_a_(12638463).func_235243_a_(MoodSoundAmbience.field_235027_b_)
                                .func_235238_a_()).parent(null));
        this.func_235063_a_(DefaultBiomeFeatures.field_235182_t_); //villages
        this.func_235063_a_(DefaultBiomeFeatures.field_235134_a_); //pillagers
        DefaultBiomeFeatures.func_235196_b_(this); //stronghold and mineshaft
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
        DefaultBiomeFeatures.addMushrooms(this);
        DefaultBiomeFeatures.addReedsAndPumpkins(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addDoubleFlowers(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addGrass(this);
        DefaultBiomeFeatures.addFreezeTopLayer(this);
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
        Biome.BIOMES.add(this);
    }

}
