package yellowstone.item;

import com.google.common.collect.Maps;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import yellowstone.main.BlockRegistry;

public class AddStrippingAxeItem extends AxeItem {

    public AddStrippingAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    public static void addStripping() {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        AxeItem.BLOCK_STRIPPING_MAP.put(BlockRegistry.DOUGLAS_LOG.get(), BlockRegistry.STRIPPED_DOUGLAS_LOG.get());
        AxeItem.BLOCK_STRIPPING_MAP.put(BlockRegistry.DOUGLAS_WOOD.get(), BlockRegistry.STRIPPED_DOUGLAS_WOOD.get());
    }

}
