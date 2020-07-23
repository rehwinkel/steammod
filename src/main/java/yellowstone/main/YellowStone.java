package yellowstone.main;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(YellowStone.MODID)
public class YellowStone {

    public static final String MODID = "yellowstone";

    public YellowStone() {
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
