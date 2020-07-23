package yellowstone.main;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yellowstone.screen.SmelteryScreen;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = Yellowstone.MODID)
public class ClientProxy implements IProxy {

    @Override
    public void commonSetup() {

    }

    @SubscribeEvent
    public static void onColorHandler(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((block, reader, pos, i) -> reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : GrassColors.get(0.5D, 1.0D), BlockRegistry.DOUGLAS_LEAVES.get(), BlockRegistry.GRASS.get());
    }

    @SubscribeEvent
    public static void onColorHandler(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, i) -> event.getBlockColors().getColor(((BlockItem) stack.getItem()).getBlock().getDefaultState(), null, null, i), BlockRegistry.DOUGLAS_LEAVES.get(), BlockRegistry.GRASS.get());
    }

    @Override
    public void clientSetup() {
        RenderTypeLookup.setRenderLayer(BlockRegistry.DOUGLAS_TRAPDOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.DOUGLAS_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.DOUGLAS_LEAVES.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(BlockRegistry.GRASS.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(BlockRegistry.DOUGLAS_SAPLING.get(), RenderType.getCutout());
        ScreenManager.registerFactory(ContainerRegistry.SMELTERY.get(), SmelteryScreen::new);
        RenderTypeLookup.setRenderLayer(BlockRegistry.GOOSEBERRY_BUSH.get(), RenderType.getCutout());
    }

}