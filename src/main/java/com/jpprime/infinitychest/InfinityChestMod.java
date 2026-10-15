package com.jpprime.infinitychest;

import com.jpprime.infinitychest.block.InfinityChestBlock;
import com.jpprime.infinitychest.menu.InfinityChestMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

@Mod(InfinityChestMod.MOD_ID)
public final class InfinityChestMod {
    public static final String MOD_ID = "infinitychest";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MOD_ID);

    public static final DeferredHolder<Block, InfinityChestBlock> INFINITY_CHEST =
            BLOCKS.register("infinity_chest", () -> new InfinityChestBlock(BlockBehaviour.Properties.of().strength(3.0F).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Item, BlockItem> INFINITY_CHEST_ITEM =
            ITEMS.register("infinity_chest", () -> new BlockItem(INFINITY_CHEST.get(), new Item.Properties()));
    public static final DeferredHolder<MenuType<?>, MenuType<InfinityChestMenu>> INFINITY_CHEST_MENU =
            MENUS.register("infinity_chest", () -> IMenuTypeExtension.create((id, inventory, data) -> new InfinityChestMenu(id, inventory)));

    public InfinityChestMod(IEventBus modBus, net.neoforged.fml.ModContainer modContainer) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        MENUS.register(modBus);
    }
}
