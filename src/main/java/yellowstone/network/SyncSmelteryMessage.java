package yellowstone.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSmelteryMessage {

    private int progress;

    public SyncSmelteryMessage() {

    }

    public SyncSmelteryMessage(int progress) {
        this.progress = progress;
    }

    public static void serialize(SyncSmelteryMessage msg, PacketBuffer buffer) {
        buffer.writeInt(msg.progress);
    }

    public static SyncSmelteryMessage deserialize(PacketBuffer buffer) {
        return new SyncSmelteryMessage(buffer.readInt());
    }

    public static void handle(SyncSmelteryMessage msg, Supplier<NetworkEvent.Context> ctx) {
        //TODO: Minecraft.getInstance().world.getTileEntity(null).
    }

}
