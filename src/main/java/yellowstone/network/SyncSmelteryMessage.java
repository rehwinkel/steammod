package yellowstone.network;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import yellowstone.container.SmelteryContainer;

import java.util.function.Supplier;

public class SyncSmelteryMessage {

    private int windowId;
    private int processingTime;
    private int burnTime;
    private int currentFuelTime;
    private int currentRecipeTime;

    public SyncSmelteryMessage() {

    }

    public SyncSmelteryMessage(int processingTime, int burnTime, int currentFuelTime, int currentRecipeTime, int windowId) {
        this.windowId = windowId;
        this.processingTime = processingTime;
        this.burnTime = burnTime;
        this.currentFuelTime = currentFuelTime;
        this.currentRecipeTime = currentRecipeTime;
    }

    public static void serialize(SyncSmelteryMessage msg, PacketBuffer buffer) {
        buffer.writeInt(msg.processingTime);
        buffer.writeInt(msg.burnTime);
        buffer.writeInt(msg.currentFuelTime);
        buffer.writeInt(msg.currentRecipeTime);
        buffer.writeInt(msg.windowId);
    }

    public static SyncSmelteryMessage deserialize(PacketBuffer buffer) {
        return new SyncSmelteryMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(),
                buffer.readInt());
    }

    public static void handle(SyncSmelteryMessage msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().enqueue(() -> {
            Container opened = Minecraft.getInstance().player.openContainer;
            if (opened.windowId == msg.windowId) {
                SmelteryContainer clientContainer = (SmelteryContainer) opened;
                clientContainer.burnTime = msg.burnTime;
                clientContainer.processingTime = msg.processingTime;
                clientContainer.currentFuelTime = msg.currentFuelTime;
                clientContainer.currentRecipeTime = msg.currentRecipeTime;
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
