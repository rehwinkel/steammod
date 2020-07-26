package yellowstone.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Predicate;

public class CountedIngredient implements Predicate<ItemStack> {

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

    @Override
    public boolean test(ItemStack itemStack) {
        boolean matchesIngredient = this.getIngredient().test(itemStack);
        return matchesIngredient && itemStack.getCount() >= this.getCount();
    }
}
