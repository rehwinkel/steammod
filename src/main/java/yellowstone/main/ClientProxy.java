package yellowstone.main;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class ClientProxy implements IProxy {

    @Override
    public void commonSetup() {

    }

    @Override
    public void clientSetup() {
        RenderTypeLookup.setRenderLayer(BlockRegistry.DOUGLAS_TRAPDOOR.get(), RenderType.getCutoutMipped());
    }

}
