package yellowstone.main;

import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister
            .create(ForgeRegistries.ITEMS, Yellowstone.MODID);

    // public static final RegistryObject<Item> GOOSEBERRIES = ITEMS.register("gooseberries", () -> new Item(null));

}
