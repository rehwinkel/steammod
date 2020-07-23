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
    public static final RegistryObject<Block> DOUGLAS_PLANKS = BLOCKS.register("douglas_planks", () -> new Block(
            AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GRAY).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_FENCE = BLOCKS.register("douglas_fence", () -> new FenceBlock(
            AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GRAY).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_FENCE_GATE = BLOCKS.register("douglas_fence_gate",
            () -> new FenceGateBlock(
                    AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GRAY).hardnessAndResistance(2.0F, 3.0F)
                            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_DOOR = BLOCKS.register("douglas_door", () -> new DoorBlock(
            AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GRAY).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_STAIRS = BLOCKS.register("douglas_stairs",
            () -> new StairsBlock(() -> BlockRegistry.DOUGLAS_PLANKS.get().getDefaultState(),
                    AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GRAY).hardnessAndResistance(2.0F, 3.0F)
                            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_SLAB = BLOCKS.register("douglas_slab", () -> new SlabBlock(
            AbstractBlock.Properties.create(Material.WOOD, MaterialColor.GRAY).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final RegistryObject<Block> STONE = BLOCKS.register("stone", () -> new Block(
            Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> LEAVES = BLOCKS.register("leaves", () -> new LeavesBlock(
            Block.Properties.create(Material.LEAVES, MaterialColor.GREEN).hardnessAndResistance(0.2F)
                    .sound(SoundType.PLANT)));

    static {
        ITEMS.register("dirt", () -> new BlockItem(DIRT.get(), new Item.Properties()));
        ITEMS.register("douglas_fence",
                () -> new BlockItem(DOUGLAS_FENCE.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_fence_gate",
                () -> new BlockItem(DOUGLAS_FENCE_GATE.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_door",
                () -> new BlockItem(DOUGLAS_DOOR.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_stairs",
                () -> new BlockItem(DOUGLAS_STAIRS.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_slab",
                () -> new BlockItem(DOUGLAS_SLAB.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("stone", () -> new BlockItem(STONE.get(), new Item.Properties()));
        ITEMS.register("leaves", () -> new BlockItem(LEAVES.get(), new Item.Properties()));
    }

}
