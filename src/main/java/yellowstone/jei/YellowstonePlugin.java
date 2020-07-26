package yellowstone.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import yellowstone.container.SmelteryContainer;
import yellowstone.main.BlockRegistry;
import yellowstone.main.RecipeRegistry;
import yellowstone.main.Yellowstone;
import yellowstone.screen.SmelteryScreen;

@JeiPlugin
public class YellowstonePlugin implements IModPlugin {

    public static final ResourceLocation SMELTERY_UID = new ResourceLocation(Yellowstone.MODID, "smeltery");

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Yellowstone.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        SmelteryRecipeCategory smelteryCategory = new SmelteryRecipeCategory(guiHelper);
        registration.addRecipeCategories(smelteryCategory);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        World world = Minecraft.getInstance().world;
        registration.addRecipes(world.getRecipeManager().func_241447_a_(RecipeRegistry.SMELTERY), SMELTERY_UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration
                .addRecipeClickArea(SmelteryScreen.class, 78, 34, 28, 21, SMELTERY_UID, VanillaRecipeCategoryUid.FUEL);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(SmelteryContainer.class, SMELTERY_UID, 0, 4, 3,
                36); //TODO: fix count of times transferred
        registration.addRecipeTransferHandler(SmelteryContainer.class, VanillaRecipeCategoryUid.FUEL, 4, 1, 3, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.SMELTERY.get()), SMELTERY_UID);
    }
}
