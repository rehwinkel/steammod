package yellowstone.block.entity;

import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import yellowstone.container.SmelteryContainer;
import yellowstone.main.RecipeRegistry;
import yellowstone.main.TileEntityRegistry;
import yellowstone.recipe.CountedIngredient;
import yellowstone.recipe.ItemHandlerBackedInventory;
import yellowstone.recipe.SmelteryRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class SmelteryTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    public LazyOptional<ItemStackHandler> input = LazyOptional.of(() -> new ItemStackHandler(4));
    public LazyOptional<ItemStackHandler> output = LazyOptional.of(() -> new ItemStackHandler(1));
    public LazyOptional<ItemStackHandler> fuel = LazyOptional.of(() -> new ItemStackHandler(1));
    public boolean shouldUpdateClient;
    public int processingTime = 0;
    public int burnTime = 0;
    public int currentFuelTime = 0;
    public SmelteryRecipe currentRecipe = null;

    public SmelteryTileEntity() {
        super(TileEntityRegistry.SMELTERY.get());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.yellowstone.smeltery");
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.put("input",
                this.input.orElseThrow(() -> new RuntimeException("was null but needed a value")).serializeNBT());
        nbt.put("output",
                this.output.orElseThrow(() -> new RuntimeException("was null but needed a value")).serializeNBT());
        nbt.put("fuel",
                this.fuel.orElseThrow(() -> new RuntimeException("was null but needed a value")).serializeNBT());
        nbt.putInt("BurnTime", this.burnTime);
        nbt.putInt("ProcessingTime", this.processingTime);
        nbt.putInt("CurrentFuelTime", this.currentFuelTime);
        nbt.putBoolean("HasCurrentRecipe", this.currentRecipe != null);
        if (this.currentRecipe != null) {
            PacketBuffer data = new PacketBuffer(Unpooled.buffer());
            SmelteryRecipe.SERIALIZER.write(data, this.currentRecipe);
            nbt.putByteArray("CurrentRecipe", data.array());
            nbt.putString("CurrentRecipeID", this.currentRecipe.getId().toString());
        }
        return super.write(nbt);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        this.input.orElseThrow(() -> new RuntimeException("was null but needed a value"))
                .deserializeNBT(nbt.getCompound("input"));
        this.output.orElseThrow(() -> new RuntimeException("was null but needed a value"))
                .deserializeNBT(nbt.getCompound("output"));
        this.fuel.orElseThrow(() -> new RuntimeException("was null but needed a value"))
                .deserializeNBT(nbt.getCompound("fuel"));
        this.burnTime = nbt.getInt("BurnTime");
        this.processingTime = nbt.getInt("ProcessingTime");
        this.currentFuelTime = nbt.getInt("CurrentFuelTime");
        if (nbt.getBoolean("HasCurrentRecipe")) {
            PacketBuffer data = new PacketBuffer(Unpooled.copiedBuffer(nbt.getByteArray("CurrentRecipe")));
            this.currentRecipe = SmelteryRecipe.SERIALIZER
                    .read(new ResourceLocation(nbt.getString("CurrentRecipeID")), data);
        }
        super.read(state, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.input.cast();
        }
        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new SmelteryContainer(windowId, playerInventory, getPos(),
                this.input.orElseThrow(() -> new RuntimeException("was null but needed a value")),
                this.output.orElseThrow(() -> new RuntimeException("was null but needed a value")),
                this.fuel.orElseThrow(() -> new RuntimeException("was null but needed a value")));
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            AtomicBoolean flagUpdateClient = new AtomicBoolean(false);
            ItemStack fuelStack = this.fuel.orElseThrow(() -> new RuntimeException("was null but needed a value"))
                    .getStackInSlot(0);
            int fuelBurnTime = ForgeHooks.getBurnTime(fuelStack);

            if (burnTime > 0 || fuelBurnTime > 0) {
                if (currentRecipe == null) {
                    getWorld().getRecipeManager().getRecipe(RecipeRegistry.SMELTERY, getRecipeInventory(), getWorld())
                            .ifPresent((recipe) -> {
                                if (this.output.orElseThrow(() -> new RuntimeException("was null but needed a value"))
                                        .insertItem(0, recipe.getCraftingResult(null), true).isEmpty()) {
                                    this.currentRecipe = recipe;
                                    processingTime = recipe.getProcessingTime();
                                    flagUpdateClient.set(true);
                                }
                            });
                } else {
                    if (!currentRecipe.matches(getRecipeInventory(), getWorld())) {
                        currentRecipe = null;
                        processingTime = 0;
                        flagUpdateClient.set(true);
                    }
                }

                if (currentRecipe != null) {
                    if (burnTime <= 1) {
                        if (fuelBurnTime > 0) {
                            fuelStack.shrink(1);
                            burnTime += fuelBurnTime;
                            currentFuelTime = fuelBurnTime;
                            flagUpdateClient.set(true);
                        }
                    }
                }
            }

            if (burnTime > 0) {
                burnTime--;
            } else {
                if (currentRecipe != null) {
                    flagUpdateClient.set(true);
                }
                currentRecipe = null;
                processingTime = 0;
            }

            if (processingTime > 0) {
                processingTime--;
            }

            if (processingTime == 0) {
                if (currentRecipe != null) {
                    finishCrafting();
                    flagUpdateClient.set(true);
                }
            }

            if (flagUpdateClient.get()) {
                this.shouldUpdateClient = true;
            }
        }
    }

    private void finishCrafting() {
        ItemStack result = currentRecipe.getCraftingResult(null);
        ItemStack rest = this.output.orElseThrow(() -> new RuntimeException("was null but needed a value"))
                .insertItem(0, result, false);
        assert rest.isEmpty();
        IItemHandlerModifiable input = this.input
                .orElseThrow(() -> new RuntimeException("was null but needed a value"));
        for (CountedIngredient ingred : currentRecipe.getIngredientList()) {
            int toRemove = ingred.getCount();
            for (int i = 0; i < 4; i++) {
                if (ingred.getIngredient().test(input.getStackInSlot(i))) {
                    int extracted = input.extractItem(i, toRemove, false).getCount();
                    toRemove -= extracted;
                    if (toRemove == 0) {
                        break;
                    }
                }
            }
        }
        currentRecipe = null;
    }

    private IInventory getRecipeInventory() {
        return new ItemHandlerBackedInventory(
                this.input.orElseThrow(() -> new RuntimeException("was null but needed a value")));
    }

    public void dropItems(World world, BlockPos pos) {
        NonNullList<ItemStack> toDrop = NonNullList.create();

        IItemHandler input = this.input.orElseThrow(() -> new RuntimeException("was null but needed a value"));
        IItemHandler fuel = this.fuel.orElseThrow(() -> new RuntimeException("was null but needed a value"));
        IItemHandler output = this.output.orElseThrow(() -> new RuntimeException("was null but needed a value"));
        for (int i = 0; i < input.getSlots(); i++) {
            if (input.getStackInSlot(i) != ItemStack.EMPTY) {
                toDrop.add(input.getStackInSlot(i));
            }
        }
        for (int i = 0; i < fuel.getSlots(); i++) {
            if (fuel.getStackInSlot(i) != ItemStack.EMPTY) {
                toDrop.add(fuel.getStackInSlot(i));
            }
        }
        for (int i = 0; i < output.getSlots(); i++) {
            if (output.getStackInSlot(i) != ItemStack.EMPTY) {
                toDrop.add(output.getStackInSlot(i));
            }
        }

        InventoryHelper.dropItems(world, pos, toDrop);
    }
}
