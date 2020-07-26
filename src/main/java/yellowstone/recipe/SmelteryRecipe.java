package yellowstone.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;
import yellowstone.main.RecipeRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SmelteryRecipe implements IRecipe<IInventory> {

    public static final IRecipeSerializer<SmelteryRecipe> SERIALIZER = new Serializer();

    private final ResourceLocation recipeId;
    private final int processingTime;
    private final ItemStack result;
    private final List<CountedIngredient> ingredientList;

    public SmelteryRecipe(ResourceLocation recipeId, int processingTime, ItemStack result, List<CountedIngredient> ingredientList) {
        this.recipeId = recipeId;
        this.processingTime = processingTime;
        this.result = result;
        this.ingredientList = ingredientList;
    }

    @Override
    public String toString() {
        return "SmelteryRecipe{" + "recipeId=" + recipeId + ", processingTime=" + processingTime + ", result=" + result + ", ingredientList=" + ingredientList + '}';
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        Map<Item, Integer> inputs = new IdentityHashMap<>();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                if (inputs.containsKey(itemstack.getItem())) {
                    inputs.put(itemstack.getItem(), inputs.get(itemstack.getItem()) + itemstack.getCount());
                } else {
                    inputs.put(itemstack.getItem(), itemstack.getCount());
                }
            }
        }
        List<ItemStack> inputList = inputs.entrySet().stream()
                .map(entry -> new ItemStack(entry.getKey(), entry.getValue())).collect(Collectors.toList());

        return RecipeMatcher.findMatches(inputList, this.getIngredientList()) != null;
    }

    public List<CountedIngredient> getIngredientList() {
        return ingredientList;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeRegistry.SMELTERY;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SmelteryRecipe> {

        @Override
        public SmelteryRecipe read(ResourceLocation recipeId, JsonObject json) {
            int burnTime = JSONUtils.getInt(json, "processingTime");
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            ArrayList<CountedIngredient> ingredientList = new ArrayList<>();
            JsonArray ingredientsJson = JSONUtils.getJsonArray(json, "ingredients");
            for (JsonElement ingredientJson : ingredientsJson) {
                int count = JSONUtils.getInt(ingredientJson.getAsJsonObject(), "count", 1);
                Ingredient ingredient = Ingredient.deserialize(ingredientJson);
                if (!ingredient.hasNoMatchingItems()) {
                    ingredientList.add(new CountedIngredient(count, ingredient));
                }
            }
            assert ingredientList.size() <= 4;
            return new SmelteryRecipe(recipeId, burnTime, result, ingredientList);
        }

        @Nullable
        @Override
        public SmelteryRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int burnTime = buffer.readInt();
            ItemStack result = buffer.readItemStack();
            int ingredientListSize = buffer.readInt();
            ArrayList<CountedIngredient> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientListSize; i++) {
                ingredients.add(new CountedIngredient(buffer.readInt(), Ingredient.read(buffer)));
            }
            return new SmelteryRecipe(recipeId, burnTime, result, ingredients);
        }

        @Override
        public void write(PacketBuffer buffer, SmelteryRecipe recipe) {
            buffer.writeInt(recipe.processingTime);
            buffer.writeItemStack(recipe.result);
            buffer.writeInt(recipe.ingredientList.size());
            for (CountedIngredient ingredient : recipe.ingredientList) {
                buffer.writeInt(ingredient.getCount());
                ingredient.getIngredient().write(buffer);
            }
        }

    }
}
