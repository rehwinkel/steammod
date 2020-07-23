package yellowstone.world;

import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yellowstone.main.Yellowstone;

public class FeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Yellowstone.MODID);

    public static final RegistryObject<YellowstoneLakeFeature> LAKE = FEATURES.register("lake", () -> new YellowstoneLakeFeature(YellowstoneLakeConfig.CODEC));

}
