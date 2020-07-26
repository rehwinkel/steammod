package yellowstone.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import yellowstone.main.BlockRegistry;
import yellowstone.main.Yellowstone;
import yellowstone.recipe.CountedIngredient;
import yellowstone.recipe.SmelteryRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmelteryRecipeCategory implements IRecipeCategory<SmelteryRecipe> {

    private static final ResourceLocation RECIPE_GUI = new ResourceLocation(Yellowstone.MODID,
            "textures/gui/smeltery_jei.png");
    protected final IDrawableAnimated arrow;
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;
    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;

    public SmelteryRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(RECIPE_GUI, 0, 0, 118, 55);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BlockRegistry.SMELTERY.get()));
        this.localizedName = I18n.format("jei.yellowstone.smeltery");
        this.arrow = guiHelper.drawableBuilder(RECIPE_GUI, 118, 14, 24, 17)
                .buildAnimated(450, IDrawableAnimated.StartDirection.LEFT, false);
        this.staticFlame = guiHelper.createDrawable(RECIPE_GUI, 118, 0, 14, 14);
        this.animatedFlame = guiHelper
                .createAnimatedDrawable(this.staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public ResourceLocation getUid() {
        return YellowstonePlugin.SMELTERY_UID;
    }

    @Override
    public Class<? extends SmelteryRecipe> getRecipeClass() {
        return SmelteryRecipe.class;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(SmelteryRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        this.animatedFlame.draw(matrixStack, 29, 21);
        this.arrow.draw(matrixStack, 60, 19);
    }

    @Override
    public void setIngredients(SmelteryRecipe smelteryRecipe, IIngredients ingredients) {
        List<List<ItemStack>> inputLists = new ArrayList();

        for (CountedIngredient countedIngredient : smelteryRecipe.getIngredientList()) {
            ItemStack[] stacks = countedIngredient.getMatchingStacks();
            List<ItemStack> expandedInput = Arrays.asList(stacks);
            inputLists.add(expandedInput);
        }

        ingredients.setInputLists(VanillaTypes.ITEM, inputLists);
        ingredients.setOutput(VanillaTypes.ITEM, smelteryRecipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout layout, SmelteryRecipe smelteryRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 0, 0);
        stacks.init(1, true, 18, 0);
        stacks.init(2, true, 36, 0);
        stacks.init(3, true, 54, 0);
        stacks.init(4, false, 96, 19);
        stacks.set(iIngredients);
    }

}
