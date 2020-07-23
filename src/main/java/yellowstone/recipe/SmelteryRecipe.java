package yellowstone.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class SmelteryRecipe {

    private final ItemStack[] input;
    private final ItemStack result;
    private final int processTime;

    public SmelteryRecipe(ItemStack[] input, ItemStack result, int processTime) {
        this.input = input;
        this.result = result;
        this.processTime = processTime;
    }

    public ItemStack getResult() {
        return this.result;
    }
    public int getProcessTime() { return this.processTime; }
    public ItemStack[] getIngredients() { return this.input; }

    public boolean canCraft(ItemStackHandler ingredients) {
        if(ingredients.getSlots() != this.input.length) return false;

        HashMap<Item, Integer> available = new HashMap<Item, Integer>();
        for(int i = 0; i < ingredients.getSlots(); i++) {
            if(available.containsKey(ingredients.getStackInSlot(i).getItem())) {
                available.put(ingredients.getStackInSlot(i).getItem(), available.get(ingredients.getStackInSlot(i).getItem()) + ingredients.getStackInSlot(i).getCount());
            } else {
                available.put(ingredients.getStackInSlot(i).getItem(), ingredients.getStackInSlot(i).getCount());
            }
        }
        for(int i = 0; i < this.input.length; i++) {
            if(this.input[i] != ItemStack.EMPTY) {
                if(available.get(this.input[i].getItem()) == null || available.get(this.input[i].getItem()) < this.input[i].getCount()) return false;
            }
        }

        return true;
    }
}
