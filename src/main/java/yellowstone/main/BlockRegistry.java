package yellowstone.main;

import net.minecraft.block.*;
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

    public static final RegistryObject<Block> STONE = BLOCKS.register("stone", () -> new Block(
            Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> LEAVES = BLOCKS.register("leaves", () -> new LeavesBlock(
            Block.Properties.create(Material.LEAVES, MaterialColor.GREEN).hardnessAndResistance(0.2F)
                    .sound(SoundType.PLANT)));

    static {
        ITEMS.register("dirt", () -> new BlockItem(DIRT.get(), new Item.Properties()));
        ITEMS.register("stone", () -> new BlockItem(DIRT.get(), new Item.Properties()));
        ITEMS.register("leaves", () -> new BlockItem(DIRT.get(), new Item.Properties()));
    }

}
