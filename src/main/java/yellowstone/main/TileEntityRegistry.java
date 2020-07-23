package yellowstone.main;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yellowstone.tile_entities.SmelteryTileEntity;

public class TileEntityRegistry {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Yellowstone.MODID);

    public static final RegistryObject<TileEntityType<SmelteryTileEntity>> SMELTERY = TILE_ENTITIES.register("smeltery",
            () -> TileEntityType.Builder.create(SmelteryTileEntity::new, BlockRegistry.SMELTERY.get()).build(null));

}
