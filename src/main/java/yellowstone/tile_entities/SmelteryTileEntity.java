package yellowstone.tile_entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import yellowstone.container.SmelteryContainer;
import yellowstone.main.BlockRegistry;
import yellowstone.main.ItemRegistry;
import yellowstone.main.TileEntityRegistry;
import yellowstone.recipe.SmelteryRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class SmelteryTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    private int process = -1;
    private int burnTime = 0;
    private int maxBurnTime = 0;
    private SmelteryRecipe currentRecipe = null;

    public LazyOptional<ItemStackHandler> input = LazyOptional.of(() -> new ItemStackHandler(4));
    public LazyOptional<ItemStackHandler> output = LazyOptional.of(() -> new ItemStackHandler(1));
    public LazyOptional<ItemStackHandler> fuel = LazyOptional.of(() -> new ItemStackHandler(1));

    public static final SmelteryRecipe[] recipes = {
        new SmelteryRecipe(new ItemStack[] {new ItemStack(ItemRegistry.COPPER_INGOT.get(), 3), new ItemStack(ItemRegistry.NICKEL_INGOT.get()), ItemStack.EMPTY, ItemStack.EMPTY}, new ItemStack(ItemRegistry.BRASS_INGOT.get(), 4), 1000),
        new SmelteryRecipe(new ItemStack[] {new ItemStack(BlockRegistry.COPPER_BLOCK.get(), 3), new ItemStack(BlockRegistry.NICKEL_BLOCK.get()), ItemStack.EMPTY, ItemStack.EMPTY}, new ItemStack(BlockRegistry.BRASS_BLOCK.get(), 4), 1000),
        new SmelteryRecipe(new ItemStack[] {new ItemStack(Blocks.IRON_ORE), ItemStack.EMPTY, ItemStack.EMPTY,ItemStack.EMPTY}, new ItemStack(Items.IRON_INGOT, 2), 450),
        new SmelteryRecipe(new ItemStack[] {new ItemStack(Blocks.GOLD_ORE), ItemStack.EMPTY, ItemStack.EMPTY,ItemStack.EMPTY}, new ItemStack(Items.GOLD_INGOT, 2), 450),
        new SmelteryRecipe(new ItemStack[] {new ItemStack(BlockRegistry.COPPER_ORE.get()), ItemStack.EMPTY, ItemStack.EMPTY,ItemStack.EMPTY}, new ItemStack(ItemRegistry.COPPER_INGOT.get(), 2), 450),
        new SmelteryRecipe(new ItemStack[] {new ItemStack(BlockRegistry.NICKEL_ORE.get()), ItemStack.EMPTY, ItemStack.EMPTY,ItemStack.EMPTY}, new ItemStack(ItemRegistry.NICKEL_INGOT.get(), 2), 450)
    };

    public SmelteryTileEntity() {
        super(TileEntityRegistry.SMELTERY.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.put("input", this.input.orElse(null).serializeNBT());
        nbt.put("output", this.output.orElse(null).serializeNBT());
        nbt.put("fuel", this.fuel.orElse(null).serializeNBT());
        return super.write(nbt);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        this.input.orElse(null).deserializeNBT(nbt.getCompound("input"));
        this.output.orElse(null).deserializeNBT(nbt.getCompound("output"));
        this.fuel.orElse(null).deserializeNBT(nbt.getCompound("fuel"));
        super.read(state, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.input.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.yellowstone.smeltery");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new SmelteryContainer(i, playerInventory, this.input.orElse(null), this.output.orElse(null), this.fuel.orElse(null));
    }

    @Override
    public void tick() {
        if(!this.isCrafting() && (this.hasFuel() || this.burnTime > 0)) {
            if(!this.isEmpty()) {
                for(SmelteryRecipe recipe : this.recipes) {
                    if(recipe.canCraft(this.input.orElse(null))){
                        this.currentRecipe = recipe;
                        this.process = recipe.getProcessTime();
                        break;
                    }
                }
            }
        }

        if(this.burnTime > 0) this.burnTime--;
        if(this.process > 0) this.process--;

        if(this.hasFuel() && this.isCrafting() && this.burnTime == 0) {
            this.consumeFuel();
            this.maxBurnTime = this.burnTime;
        }

        if(this.isCrafting() && this.process == 0) {
            this.craft(this.currentRecipe);
            this.process = -1;
            this.currentRecipe = null;
            return;
        }

        if(this.isCrafting() && !this.currentRecipe.canCraft(this.input.orElse(null))) {
            this.process = -1;
            this.currentRecipe = null;
            return;
        }

        if(this.isCrafting() && this.burnTime == 0) {
            this.process = -1;
            this.currentRecipe = null;
            return;
        }
    }

    boolean isCrafting() {
        return this.currentRecipe != null && this.process != -1;
    }

    void craft(SmelteryRecipe recipe) {
        if(recipe.canCraft(this.input.orElse(null))){
            if(this.output.orElse(null).insertItem(0, recipe.getResult().copy(), true) == ItemStack.EMPTY) {
                this.output.orElse(null).insertItem(0, recipe.getResult().copy(), false);

                for(int i = 0; i < recipe.getIngredients().length; i++) {
                    int toRemove = recipe.getIngredients()[i].getCount();
                    for(int j =  0; j < 4; j++) {
                        if(toRemove == 0) break;

                        ItemStack toCheck = this.input.orElse(null).getStackInSlot(j);
                        if(ItemStack.areItemsEqual(toCheck, recipe.getIngredients()[i])) {
                            if(toCheck.getCount() < toRemove) {
                                toRemove -= toCheck.getCount();
                                this.input.orElse(null).extractItem(j, toCheck.getCount(), false);
                            } else {
                                this.input.orElse(null).extractItem(j, toRemove, false);
                                break;
                            }
                        }
                    }
                }
                return;
            }
        }
    }

    boolean isEmpty() {
        for(int i = 0; i < this.input.orElse(null).getSlots(); i++) {
            if(this.input.orElse(null).getStackInSlot(i) != ItemStack.EMPTY) return false;
        }
        return true;
    }

    boolean hasFuel() {
        return this.fuel.orElse(null).getStackInSlot(0) != ItemStack.EMPTY;
    }

    void consumeFuel() {
        if(this.hasFuel()) {
            this.burnTime += AbstractFurnaceTileEntity.getBurnTimes().get(this.fuel.orElse(null).getStackInSlot(0).getItem());
             //this.fuel.orElse(null).getStackInSlot(0).shrink(1);
            this.fuel.orElse(null).extractItem(0, 1, false);
        }
    }

    public void dropItems(World world, BlockPos pos) {
        NonNullList<ItemStack> toDrop = NonNullList.create();

        for(int i = 0; i < this.input.orElse(null).getSlots(); i++) {
            if(this.input.orElse(null).getStackInSlot(i) != ItemStack.EMPTY) {
                toDrop.add(this.input.orElse(null).getStackInSlot(i));
            }
        }
        for(int i = 0; i < this.fuel.orElse(null).getSlots(); i++) {
            if(this.fuel.orElse(null).getStackInSlot(i) != ItemStack.EMPTY) {
                toDrop.add(this.fuel.orElse(null).getStackInSlot(i));
            }
        }
        for(int i = 0; i < this.output.orElse(null).getSlots(); i++) {
            if(this.output.orElse(null).getStackInSlot(i) != ItemStack.EMPTY) {
                toDrop.add(this.output.orElse(null).getStackInSlot(i));
            }
        }

        InventoryHelper.dropItems(world, pos, toDrop);
    }
}
