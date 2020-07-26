package yellowstone.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CountedIngredient {

    private final int count;
    private final Ingredient ingredient;

    public CountedIngredient(int count, Ingredient ingredient) {
        this.count = count;
        this.ingredient = ingredient;
    }

    public int getCount() {
        return count;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public ItemStack[] getMatchingStacks() {
        ItemStack[] stacks = this.getIngredient().getMatchingStacks();
        for (ItemStack stack : stacks) {
            stack.setCount(getCount());
        }
        return stacks;
    }

}
