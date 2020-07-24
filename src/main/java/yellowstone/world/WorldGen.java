package yellowstone.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import yellowstone.main.BlockRegistry;
import yellowstone.main.WorldRegistry;

public class WorldGen {

    public static final BaseTreeFeatureConfig DOUGLAS_TREE_CONFIG = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.DOUGLAS_LOG.get().getDefaultState()), new SimpleBlockStateProvider(BlockRegistry.DOUGLAS_LEAVES.get().getDefaultState()), new SpruceFoliagePlacer(4, 1, 1, 0, 4, 1), //radius, radius_rand, off, off_rand, height, height_rand
            new StraightTrunkPlacer(7, 3, 0), // Height from 7 to 12 //height, height_off_1, height_off_2
            new TwoLayerFeature(2, 0, 2)).func_236700_a_().build();

    public static void addDouglasTree(Biome b) {
        b.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(DOUGLAS_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(2, 0.1f, 1))));
    }

    public static void addModOres(Biome b) {
        b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.COPPER_ORE.get().getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 64))));
        b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.NICKEL_ORE.get().getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 64))));
    }

    public static void addSomeBirchTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(DefaultBiomeFeatures.field_230129_h_).withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(3))));
    }

    public static void addYellowstoneLakes(Biome b) {
        b.addFeature(GenerationStage.Decoration.LAKES, WorldRegistry.LAKE.get().withConfiguration(new YellowstoneLakeConfig(10)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(16))));
    }

}