package yellowstone.main;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
    }

}
