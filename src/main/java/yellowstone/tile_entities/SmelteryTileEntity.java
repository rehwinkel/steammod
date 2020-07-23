package yellowstone.tile_entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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

public class SmelteryTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    public LazyOptional<ItemStackHandler> input = LazyOptional.of(() -> new ItemStackHandler(4));
    public LazyOptional<ItemStackHandler> output = LazyOptional.of(() -> new ItemStackHandler(1));
    public LazyOptional<ItemStackHandler> fuel = LazyOptional.of(() -> new ItemStackHandler(1));

    public static final SmelteryRecipe[] recipes = {
        new SmelteryRecipe(new ItemStack[] {new ItemStack(BlockRegistry.COPPER_ORE.get(), 2), new ItemStack(BlockRegistry.NICKEL_ORE.get()), ItemStack.EMPTY, ItemStack.EMPTY}, new ItemStack(ItemRegistry.BRASS_INGOT.get(), 3))
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
        if(!this.isEmpty()) {
            for(SmelteryRecipe recipe : this.recipes) {
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
        }
    }

    boolean isEmpty() {
        for(int i = 0; i < this.input.orElse(null).getSlots(); i++) {
            if(this.input.orElse(null).getStackInSlot(i) != ItemStack.EMPTY) return false;
        }
        return true;
    }
}
