package yellowstone.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ShovelItem;
import yellowstone.main.BlockRegistry;

public class AddPathingShovelItem extends ShovelItem {

    public AddPathingShovelItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public static void addPathing() {
        ShovelItem.SHOVEL_LOOKUP.put(BlockRegistry.GRASS.get(), BlockRegistry.PATH.get().getDefaultState());
    }

}
