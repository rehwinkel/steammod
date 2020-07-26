package yellowstone.main;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yellowstone.recipe.SmelteryRecipe;


public class RecipeRegistry {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Yellowstone.MODID);
    public static final IRecipeType<SmelteryRecipe> SMELTERY = new IRecipeType<SmelteryRecipe>() {
        @Override
        public String toString() {
            return new ResourceLocation(Yellowstone.MODID, "smeltery").toString();
        }
    };

    static {
        RECIPES.register("smeltery", () -> SmelteryRecipe.SERIALIZER);
    }

    public static void registerRecipeTypes() {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(SMELTERY.toString()), SMELTERY);
    }
}
