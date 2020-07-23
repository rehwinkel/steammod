package yellowstone.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import yellowstone.container.SmelteryContainer;
import yellowstone.main.Yellowstone;

public class SmelteryScreen extends ContainerScreen<SmelteryContainer> {

    private static final ResourceLocation SMELTERY_TEXTURE = new ResourceLocation(Yellowstone.MODID,
            "textures/gui/container/smeltery.png");

    public SmelteryScreen(SmelteryContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    //Background
    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float p_230450_2_, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.getMinecraft().getTextureManager().bindTexture(SMELTERY_TEXTURE);
        int i = this.guiLeft;
        int j = (this.height - this.ySize) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float p_230430_4_) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, p_230430_4_);
        this.func_230459_a_(matrixStack, mouseX, mouseY);
    }

    //Foreground
    @Override
    protected void func_230451_b_(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        super.func_230451_b_(p_230451_1_, p_230451_2_, p_230451_3_);
    }
}
