package yellowstone.main;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yellowstone.world.YellowstoneBiome;
import yellowstone.world.YellowstoneSurfaceBuilder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldRegistry {

    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Yellowstone.MODID);
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Yellowstone.MODID);

    public static YellowstoneSurfaceBuilder YELLOWSTONE_SURFACE;
    public static YellowstoneBiome YELLOWSTONE_BIOME;

    @SubscribeEvent
    public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event) {
        YELLOWSTONE_SURFACE.setRegistryName("yellowstone");
        event.getRegistry().registerAll(YELLOWSTONE_SURFACE);
    }

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {
        YELLOWSTONE_SURFACE = new YellowstoneSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_);
        YELLOWSTONE_BIOME = new YellowstoneBiome();
        YELLOWSTONE_BIOME.setRegistryName("yellowstone");
        event.getRegistry().registerAll(YELLOWSTONE_BIOME);
    }

}
