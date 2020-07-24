package yellowstone.main;

import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yellowstone.block.SteamPipeBlock;
import yellowstone.screen.SmelteryScreen;

import java.util.function.Function;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = Yellowstone.MODID)
public class ClientProxy implements IProxy {

    @Override
    public void commonSetup() {

    }

    private static final ResourceLocation STRAIGHT_PIPE_LOCATION = new ResourceLocation(Yellowstone.MODID, "block/steam_pipe_straight");

    @SubscribeEvent
    public static void onBakeModels(ModelBakeEvent event) {
        override(event, BlockRegistry.STEAM_PIPE.get().getDefaultState().with(SteamPipeBlock.DOWN, true).with(SteamPipeBlock.UP, true), state -> event.getModelLoader().getBakedModel(STRAIGHT_PIPE_LOCATION, ModelRotation.X0_Y0, event.getModelLoader().getSpriteMap()::getSprite));
        override(event, BlockRegistry.STEAM_PIPE.get().getDefaultState().with(SteamPipeBlock.WEST, true).with(SteamPipeBlock.EAST, true), state -> event.getModelLoader().getBakedModel(STRAIGHT_PIPE_LOCATION, ModelRotation.X90_Y90, event.getModelLoader().getSpriteMap()::getSprite));
        override(event, BlockRegistry.STEAM_PIPE.get().getDefaultState().with(SteamPipeBlock.NORTH, true).with(SteamPipeBlock.SOUTH, true), state -> event.getModelLoader().getBakedModel(STRAIGHT_PIPE_LOCATION, ModelRotation.X90_Y0, event.getModelLoader().getSpriteMap()::getSprite));
    }

    private static void override(ModelBakeEvent event, BlockState state, Function<BlockState, IBakedModel> f) {
        ModelResourceLocation loc = BlockModelShapes.getModelLocation(state);
        IBakedModel model = event.getModelRegistry().get(loc);
        if (model != null) {
            event.getModelRegistry().put(loc, f.apply(state));
        }
    }

    @SubscribeEvent
    public static void onColorHandler(ColorHandlerEvent.Block event) {
        event.getBlockColors().register((state, reader, pos, i) -> reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : GrassColors.get(1.0D, 0.0D), BlockRegistry.GRASS.get());
        event.getBlockColors().register((state, reader, pos, i) -> FoliageColors.get(1.0, 0.0), BlockRegistry.DOUGLAS_LEAVES.get());
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
        RenderTypeLookup.setRenderLayer(BlockRegistry.PHLOX.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.POTTED_PHLOX.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.POTTED_DOUGLAS_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.STEAM_PIPE.get(), RenderType.getTranslucent());
        ScreenManager.registerFactory(ContainerRegistry.SMELTERY.get(), SmelteryScreen::new);
        RenderTypeLookup.setRenderLayer(BlockRegistry.GOOSEBERRY_BUSH.get(), RenderType.getCutout());
    }

}