package yellowstone.main;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yellowstone.item.AddPathingShovelItem;
import yellowstone.item.AddStrippingAxeItem;
import yellowstone.network.PacketHandler;
import yellowstone.world.FeatureRegistry;
import yellowstone.world.WorldGen;

@Mod(Yellowstone.MODID)
public class Yellowstone {

    public static final String MODID = "yellowstone";

    public static final ItemGroup y_blocks = new ItemGroup("yellowstone_blocks") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockRegistry.LAVASTONE.get());
        }
    };

    private static final IProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Yellowstone() {
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FeatureRegistry.FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityRegistry.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ContainerRegistry.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    public void clientSetup(FMLClientSetupEvent event) {
        proxy.clientSetup();
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            for (Biome b : Biome.BIOMES) {
                WorldGen.addModOres(b);
            }
            WorldGen.addDouglasTree(WorldRegistry.YELLOWSTONE_BIOME);
            WorldGen.addYellowstoneLakes(WorldRegistry.YELLOWSTONE_BIOME);
        });
        AddPathingShovelItem.addPathing();
        AddStrippingAxeItem.addStripping();
        PacketHandler.registerPackets();
        proxy.commonSetup();
    }

}
