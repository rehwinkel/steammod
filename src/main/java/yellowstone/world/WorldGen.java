package yellowstone.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.PineFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import yellowstone.main.BlockRegistry;

public class WorldGen {

    public static final BaseTreeFeatureConfig DOUGLAS_TREE_CONFIG = new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlockRegistry.DOUGLAS_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(BlockRegistry.DOUGLAS_LEAVES.get().getDefaultState()),
            new PineFoliagePlacer(1, 0, 1, 0, 4, 2),
            new StraightTrunkPlacer(11, 7, 0),
            new TwoLayerFeature(3, 0, 2)
    ).func_236700_a_().build();

    public static void addDouglasTree(Biome b) {
        b.addFeature(
                GenerationStage.Decoration.VEGETAL_DECORATION,
                Feature.field_236291_c_.withConfiguration(DOUGLAS_TREE_CONFIG)
                        .withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(10, 0.1f, 1)))
        );
    }

}