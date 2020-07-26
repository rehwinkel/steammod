package yellowstone.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import yellowstone.block.entity.SmelteryTileEntity;
import yellowstone.container.slots.FuelSlot;
import yellowstone.container.slots.OutputSlot;
import yellowstone.main.ContainerRegistry;
import yellowstone.network.PacketHandler;
import yellowstone.network.SyncSmelteryMessage;

import java.util.function.Supplier;

public class SmelteryContainer extends Container {

    private final World world;
    private final BlockPos pos;
    private final SmelteryTileEntity tileEntity;
    public int burnTime;
    public int currentFuelTime;
    public int processingTime;
    public int currentRecipeTime;

    public SmelteryContainer(int windowId, PlayerInventory playerInv, PacketBuffer data) {
        this(windowId, playerInv, data.readBlockPos(), new ItemStackHandler(4), new ItemStackHandler(1),
                new ItemStackHandler(1));
    }

    public SmelteryContainer(int windowId, PlayerInventory playerInv, BlockPos pos, ItemStackHandler input, ItemStackHandler output, ItemStackHandler fuel) {
        super(ContainerRegistry.SMELTERY.get(), windowId);
        this.pos = pos;
        this.world = playerInv.player.getEntityWorld();
        this.tileEntity = (SmelteryTileEntity) playerInv.player.getEntityWorld().getTileEntity(pos);
        if (!world.isRemote) {
            Supplier<PacketDistributor.TargetPoint> target = PacketDistributor.TargetPoint
                    .p(pos.getX(), pos.getY(), pos.getZ(), 64, world.func_234923_W_());
            PacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(target),
                    new SyncSmelteryMessage(this.tileEntity.processingTime, this.tileEntity.burnTime,
                            this.tileEntity.currentFuelTime,
                            this.tileEntity.currentRecipe != null ? this.tileEntity.currentRecipe
                                    .getProcessingTime() : 0, this.windowId));
        }

        this.addSlot(new SlotItemHandler(input, 0, 20, 16));
        this.addSlot(new SlotItemHandler(input, 1, 38, 16));
        this.addSlot(new SlotItemHandler(input, 2, 56, 16));
        this.addSlot(new SlotItemHandler(input, 3, 74, 16));

        this.addSlot(new FuelSlot(fuel, 0, 48, 53));

        this.addSlot(new OutputSlot(output, 0, 116, 35));

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInv, i, 8 + i * 18, 142));
        }
        for (int i = 0; i < 27; i++) {
            this.addSlot(new Slot(playerInv, 9 + i, 8 + (i % 9) * 18, 84 + 18 * (i / 9)));
        }
    }


    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int id) {
        int invSize = 6;
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(id);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (id < invSize) {
                if (!this.mergeItemStack(itemstack1, invSize, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, invSize, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        update();
    }

    public void update() {
        if (!world.isRemote) {
            if (tileEntity.shouldUpdateClient) {
                tileEntity.shouldUpdateClient = false;
                Supplier<PacketDistributor.TargetPoint> target = PacketDistributor.TargetPoint
                        .p(pos.getX(), pos.getY(), pos.getZ(), 64, world.func_234923_W_());
                PacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(target),
                        new SyncSmelteryMessage(this.tileEntity.processingTime, this.tileEntity.burnTime,
                                this.tileEntity.currentFuelTime,
                                this.tileEntity.currentRecipe != null ? this.tileEntity.currentRecipe
                                        .getProcessingTime() : 0, this.windowId));
            }
        }
    }

    public int getBurnLeftScaled() {
        if (this.currentFuelTime > 0) {
            return this.burnTime * 13 / this.currentFuelTime;
        } else {
            return 0;
        }
    }

    public int getProgressScaled() {
        if (processingTime == 0 || this.currentRecipeTime == 0) {
            return 24;
        }
        return this.processingTime * 24 / this.currentRecipeTime;
    }
}
