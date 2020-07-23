package yellowstone.main;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yellowstone.world.WorldGen;

@Mod(YellowStone.MODID)
public class YellowStone {

    public static final String MODID = "yellowstone";

    public static final ItemGroup y_blocks = new ItemGroup("yellowstone_blocks") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockRegistry.LAVASTONE.get());
        }
    };

    public YellowStone() {
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    public void setup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            for (Biome b : Biome.BIOMES) {
                WorldGen.addDouglasTree(b);
            }
        });
    }

}
