package yellowstone.main;

import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Yellowstone.MODID);

    public static final RegistryObject<Item> BRASS_INGOT = ITEMS.register("brass_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOOSEBERRIES = ITEMS.register("gooseberries", () -> new BlockNamedItem(BlockRegistry.GOOSEBERRY_BUSH.get(), (new Item.Properties()).group(ItemGroup.FOOD).food(Foods.SWEET_BERRIES)));

    //TODO: register brass ingot to item tab
}
