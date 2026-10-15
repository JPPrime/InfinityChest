package com.jpprime.infinitychest.menu;

import com.jpprime.infinitychest.InfinityChestMod;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class InfinityChestMenu extends AbstractContainerMenu {
    private final Container loot;

    public InfinityChestMenu(int id, Inventory inventory) {
        this(id, inventory, new SimpleContainer(27));
    }

    public InfinityChestMenu(int id, Inventory inventory, Container loot) {
        super(InfinityChestMod.INFINITY_CHEST_MENU.get(), id);
        checkContainerSize(loot, 27);
        this.loot = loot;
        loot.startOpen(inventory.player);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(loot, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18,  theY(row)));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inventory, col, 8 + col * 18, 142));
        }
    }

    private static int theY(int row) {
        return 84 + row * 18;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack source = slot.getItem();
            result = source.copy();
            if (index < 27) {
                if (!moveItemStackTo(source, 27, slots.size(), true)) return ItemStack.EMPTY;
            } else if (!moveItemStackTo(source, 0, 27, false)) {
                return ItemStack.EMPTY;
            }
            if (source.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
            if (source.getCount() == result.getCount()) return ItemStack.EMPTY;
            slot.onTake(player, source);
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        loot.stopOpen(player);
    }
}
