package yellowstone.main;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yellowstone.block.DirtBlock;
import yellowstone.block.GooseberryBushBlock;
import yellowstone.blocks.SmelteryBlock;
import yellowstone.world.DouglasTree;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Yellowstone.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Yellowstone.MODID);

    public static final RegistryObject<Block> DIRT = BLOCKS.register("dirt", () -> new DirtBlock(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)));
    public static final RegistryObject<Block> LAVASTONE = BLOCKS.register("lavastone", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DOUGLAS_PLANKS = BLOCKS.register("douglas_planks", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_FENCE = BLOCKS.register("douglas_fence", () -> new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_FENCE_GATE = BLOCKS.register("douglas_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_DOOR = BLOCKS.register("douglas_door", () -> new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> DOUGLAS_TRAPDOOR = BLOCKS.register("douglas_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> DOUGLAS_STAIRS = BLOCKS.register("douglas_stairs", () -> new StairsBlock(() -> BlockRegistry.DOUGLAS_PLANKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_SLAB = BLOCKS.register("douglas_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_PRESSURE_PLATE = BLOCKS.register("douglas_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_BUTTON = BLOCKS.register("douglas_button", () -> new WoodButtonBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_LEAVES = BLOCKS.register("douglas_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES, MaterialColor.GREEN).hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid()));
    public static final RegistryObject<Block> DOUGLAS_SAPLING = BLOCKS.register("douglas_sapling", () -> new SaplingBlock(new DouglasTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final RegistryObject<Block> DOUGLAS_LOG = BLOCKS.register("douglas_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> STRIPPED_DOUGLAS_LOG = BLOCKS.register("stripped_douglas_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> STRIPPED_DOUGLAS_WOOD = BLOCKS.register("stripped_douglas_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DOUGLAS_WOOD = BLOCKS.register("douglas_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new OreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0F, 3.0F)));
    public static final RegistryObject<Block> NICKEL_ORE = BLOCKS.register("nickel_ore", () -> new OreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.0F, 3.0F)));
    public static final RegistryObject<Block> BRASS_BLOCK = BLOCKS.register("brass_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).setRequiresTool().hardnessAndResistance(3.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> GRASS = BLOCKS.register("grass", () -> new yellowstone.block.GrassBlock(AbstractBlock.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> PATH = BLOCKS.register("path", () -> new GrassPathBlock(AbstractBlock.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> LAVASTONE_SLAB = BLOCKS.register("lavastone_slab", () -> new SlabBlock(AbstractBlock.Properties.from(LAVASTONE.get())));
    public static final RegistryObject<Block> LAVASTONE_WALL = BLOCKS.register("lavastone_wall", () -> new WallBlock(AbstractBlock.Properties.from(LAVASTONE.get())));
    public static final RegistryObject<Block> LAVASTONE_STAIRS = BLOCKS.register("lavastone_stairs", () -> new StairsBlock(() -> LAVASTONE.get().getDefaultState(), AbstractBlock.Properties.from(LAVASTONE.get())));
    public static final RegistryObject<Block> POLISHED_LAVASTONE = BLOCKS.register("polished_lavastone", () -> new Block(AbstractBlock.Properties.from(LAVASTONE.get())));
    public static final RegistryObject<Block> POLISHED_LAVASTONE_SLAB = BLOCKS.register("polished_lavastone_slab", () -> new SlabBlock(AbstractBlock.Properties.from(LAVASTONE.get())));
    public static final RegistryObject<Block> POLISHED_LAVASTONE_STAIRS = BLOCKS.register("polished_lavastone_stairs", () -> new StairsBlock(() -> POLISHED_LAVASTONE.get().getDefaultState(), AbstractBlock.Properties.from(LAVASTONE.get())));
    public static final RegistryObject<Block> GOOSEBERRY_BUSH = BLOCKS.register("gooseberry_bush", () -> new GooseberryBushBlock(AbstractBlock.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH)));
    public static final RegistryObject<Block> SMELTERY = BLOCKS.register("smeltery", () -> new SmelteryBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE)));

    static {
        BLOCKS.register("potted_douglas_sapling", () -> new FlowerPotBlock(null, DOUGLAS_SAPLING, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
        ITEMS.register("dirt", () -> new BlockItem(DIRT.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("lavastone", () -> new BlockItem(LAVASTONE.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_planks", () -> new BlockItem(DOUGLAS_PLANKS.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_fence", () -> new BlockItem(DOUGLAS_FENCE.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_fence_gate", () -> new BlockItem(DOUGLAS_FENCE_GATE.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_door", () -> new BlockItem(DOUGLAS_DOOR.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_trapdoor", () -> new BlockItem(DOUGLAS_TRAPDOOR.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_stairs", () -> new BlockItem(DOUGLAS_STAIRS.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_slab", () -> new BlockItem(DOUGLAS_SLAB.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_pressure_plate", () -> new BlockItem(DOUGLAS_PRESSURE_PLATE.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_button", () -> new BlockItem(DOUGLAS_BUTTON.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_leaves", () -> new BlockItem(DOUGLAS_LEAVES.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_sapling", () -> new BlockItem(DOUGLAS_SAPLING.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_log", () -> new BlockItem(DOUGLAS_LOG.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("douglas_wood", () -> new BlockItem(DOUGLAS_WOOD.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("stripped_douglas_log", () -> new BlockItem(STRIPPED_DOUGLAS_LOG.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("stripped_douglas_wood", () -> new BlockItem(STRIPPED_DOUGLAS_WOOD.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("copper_ore", () -> new BlockItem(COPPER_ORE.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("nickel_ore", () -> new BlockItem(NICKEL_ORE.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("brass_block", () -> new BlockItem(BRASS_BLOCK.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("grass", () -> new BlockItem(GRASS.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("lavastone_slab", () -> new BlockItem(LAVASTONE_SLAB.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("lavastone_stairs", () -> new BlockItem(LAVASTONE_STAIRS.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("lavastone_wall", () -> new BlockItem(LAVASTONE_WALL.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("polished_lavastone", () -> new BlockItem(POLISHED_LAVASTONE.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("polished_lavastone_slab", () -> new BlockItem(POLISHED_LAVASTONE_SLAB.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("polished_lavastone_stairs", () -> new BlockItem(POLISHED_LAVASTONE_STAIRS.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("path", () -> new BlockItem(PATH.get(), new Item.Properties().group(Yellowstone.y_blocks)));
        ITEMS.register("smeltery", () -> new BlockItem(SMELTERY.get(), new Item.Properties().group(Yellowstone.y_blocks)));
    }

}
