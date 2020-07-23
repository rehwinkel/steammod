package yellowstone.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import yellowstone.main.Yellowstone;

public class PacketHandler {

    public static final String VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Yellowstone.MODID, "main"), () -> VERSION, VERSION::equals, VERSION::equals);

    public static void registerPackets() {
        INSTANCE.registerMessage(0, SyncSmelteryMessage.class, SyncSmelteryMessage::serialize, SyncSmelteryMessage::deserialize, SyncSmelteryMessage::handle);
    }

}
