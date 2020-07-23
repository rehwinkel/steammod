package yellowstone.main;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yellowstone.container.SmelteryContainer;

public class ContainerRegistry {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Yellowstone.MODID);

    public static final RegistryObject<ContainerType<SmelteryContainer>> SMELTERY = CONTAINERS.register("smeltery",
            () -> new ContainerType<SmelteryContainer>(SmelteryContainer::new));
}
