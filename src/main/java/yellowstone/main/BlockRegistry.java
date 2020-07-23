package yellowstone.main;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, YellowStone.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, YellowStone.MODID);

    public static final RegistryObject<Block> DIRT = BLOCKS.register("dirt", () -> new Block(
            AbstractBlock.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F)
                    .sound(SoundType.GROUND)));

    static {
        ITEMS.register("dirt", () -> new BlockItem(DIRT.get(), new Item.Properties()));
    }

}
