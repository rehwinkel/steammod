package yellowstone.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import yellowstone.blocks.SmelteryBlock;
import yellowstone.container.slots.FuelSlot;
import yellowstone.container.slots.OutputSlot;
import yellowstone.main.ContainerRegistry;

public class SmelteryContainer extends Container {

    public SmelteryContainer(int id, PlayerInventory player_inv) {
        this(id, player_inv, new ItemStackHandler(4), new ItemStackHandler(1),  new ItemStackHandler(1));
    }

    public SmelteryContainer(int id, PlayerInventory player_inv, ItemStackHandler input, ItemStackHandler output, ItemStackHandler fuel) {
        super(ContainerRegistry.SMELTERY.get(), id);

        this.addSlot(new SlotItemHandler(input, 0, 20, 16));
        this.addSlot(new SlotItemHandler(input, 1, 38, 16));
        this.addSlot(new SlotItemHandler(input, 2, 56, 16));
        this.addSlot(new SlotItemHandler(input, 3, 74, 16));

        this.addSlot(new FuelSlot(fuel, 0, 48, 53));

        this.addSlot(new OutputSlot(output, 0, 116, 35));

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(player_inv, i, 8 + i * 18, 142));
        }
        for (int i = 0; i < 27; i++) {
            this.addSlot(new Slot(player_inv, 9 + i, 8 + (i % 9) * 18, 84 + 18 * (i / 9)));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
