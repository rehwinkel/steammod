package yellowstone.main;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.OakTree;
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
    public static final RegistryObject<Block> LAVASTONE = BLOCKS.register("lavastone", () -> new Block(
            Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F)
                    .sound(SoundType.STONE)));
    public static final RegistryObject<Block> DOUGLAS_PLANKS = BLOCKS.register("douglas_planks", () -> new Block(
            AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_FENCE = BLOCKS.register("douglas_fence", () -> new FenceBlock(
            AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_FENCE_GATE = BLOCKS.register("douglas_fence_gate",
            () -> new FenceGateBlock(
                    AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_DOOR = BLOCKS.register("douglas_door", () -> new DoorBlock(
            AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> DOUGLAS_TRAPDOOR = BLOCKS.register("douglas_trapdoor",
            () -> new TrapDoorBlock(
                    AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                            .sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> DOUGLAS_STAIRS = BLOCKS.register("douglas_stairs",
            () -> new StairsBlock(() -> BlockRegistry.DOUGLAS_PLANKS.get().getDefaultState(),
                    AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_SLAB = BLOCKS.register("douglas_slab", () -> new SlabBlock(
            AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_PRESSURE_PLATE = BLOCKS.register("douglas_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                    AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_BUTTON = BLOCKS.register("douglas_button",
            () -> new WoodButtonBlock(
                    AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_LEAVES = BLOCKS.register("douglas_leaves", () -> new LeavesBlock(
            Block.Properties.create(Material.LEAVES, MaterialColor.GREEN).hardnessAndResistance(0.2F)
                    .sound(SoundType.PLANT).notSolid()));
    public static final RegistryObject<Block> DOUGLAS_SAPLING = BLOCKS.register("douglas_sapling",
            () -> new SaplingBlock(new OakTree(),
                    AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly()
                            .zeroHardnessAndResistance().sound(SoundType.PLANT))); //TODO
    public static final RegistryObject<Block> DOUGLAS_LOG = BLOCKS.register("douglas_log", () -> new RotatedPillarBlock(
            Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> STRIPPED_DOUGLAS_LOG = BLOCKS.register("stripped_douglas_log",
            () -> new RotatedPillarBlock(
                    Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F)
                            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> STRIPPED_DOUGLAS_WOOD = BLOCKS.register("stripped_douglas_wood",
            () -> new RotatedPillarBlock(
                    AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F)
                            .sound(SoundType.WOOD)));

    public static final RegistryObject<Block> DOUGLAS_WOOD = BLOCKS.register("douglas_wood",
            () -> new RotatedPillarBlock(
                    AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F)
                            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new OreBlock(
            AbstractBlock.Properties.create(Material.ROCK).func_235861_h_().hardnessAndResistance(3.0F, 3.0F)));

    public static final RegistryObject<Block> NICKEL_ORE = BLOCKS.register("nickel_ore", () -> new OreBlock(
            AbstractBlock.Properties.create(Material.ROCK).func_235861_h_().hardnessAndResistance(3.0F, 3.0F)));

    static {
        BLOCKS.register("potted_douglas_sapling",
                () -> new FlowerPotBlock(null, DOUGLAS_SAPLING,
                        AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
        ITEMS.register("dirt", () -> new BlockItem(DIRT.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("lavastone",
                () -> new BlockItem(LAVASTONE.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_planks",
                () -> new BlockItem(DOUGLAS_PLANKS.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_fence",
                () -> new BlockItem(DOUGLAS_FENCE.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_fence_gate",
                () -> new BlockItem(DOUGLAS_FENCE_GATE.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_door",
                () -> new BlockItem(DOUGLAS_DOOR.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_trapdoor",
                () -> new BlockItem(DOUGLAS_TRAPDOOR.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_stairs",
                () -> new BlockItem(DOUGLAS_STAIRS.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_slab",
                () -> new BlockItem(DOUGLAS_SLAB.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_pressure_plate",
                () -> new BlockItem(DOUGLAS_PRESSURE_PLATE.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_button",
                () -> new BlockItem(DOUGLAS_BUTTON.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_leaves",
                () -> new BlockItem(DOUGLAS_LEAVES.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_sapling",
                () -> new BlockItem(DOUGLAS_SAPLING.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_log",
                () -> new BlockItem(DOUGLAS_LOG.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("douglas_wood",
                () -> new BlockItem(DOUGLAS_WOOD.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("stripped_douglas_log",
                () -> new BlockItem(STRIPPED_DOUGLAS_LOG.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("stripped_douglas_wood",
                () -> new BlockItem(STRIPPED_DOUGLAS_WOOD.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("copper_ore",
                () -> new BlockItem(COPPER_ORE.get(), new Item.Properties().group(YellowStone.y_blocks)));
        ITEMS.register("nickel_ore",
                () -> new BlockItem(NICKEL_ORE.get(), new Item.Properties().group(YellowStone.y_blocks)));
    }

}
