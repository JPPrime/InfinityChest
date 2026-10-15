package com.jpprime.infinitychest.loot;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class LootGenerator {
    private static final Random RANDOM = new Random();

    // Edit this list or use the JSON configuration documented in README.md.
    private static final List<String> DEFAULT_ITEMS = List.of(
            "minecraft:diamond", "minecraft:emerald", "minecraft:gold_ingot",
            "minecraft:iron_ingot", "minecraft:ender_pearl", "minecraft:golden_apple",
            "minecraft:enchanted_golden_apple", "minecraft:netherite_ingot",
            "minecraft:experience_bottle", "minecraft:totem_of_undying",
            "minecraft:amethyst_shard", "minecraft:lapis_lazuli",
            "minecraft:blaze_rod", "minecraft:ghast_tear", "minecraft:shulker_shell"
    );

    private LootGenerator() {}

    public static Container create(RegistryAccess registryAccess, ServerPlayer player) {
        net.minecraft.world.SimpleContainer container = new net.minecraft.world.SimpleContainer(27);
        List<Item> pool = new ArrayList<>();
        for (String id : DEFAULT_ITEMS) {
            ResourceLocation key = ResourceLocation.tryParse(id);
            if (key != null && BuiltInRegistries.ITEM.containsKey(key)) {
                Item item = BuiltInRegistries.ITEM.get(key);
                if (item != Items.AIR) pool.add(item);
            }
        }
        if (pool.isEmpty()) pool.add(Items.DIAMOND);
        int stacks = 8 + RANDOM.nextInt(10);
        for (int i = 0; i < stacks; i++) {
            int slot;
            do { slot = RANDOM.nextInt(container.getContainerSize()); }
            while (!container.getItem(slot).isEmpty());
            Item item = pool.get(RANDOM.nextInt(pool.size()));
            int max = Math.max(1, Math.min(16, item.getDefaultMaxStackSize()));
            container.setItem(slot, new ItemStack(item, 1 + RANDOM.nextInt(max)));
        }
        return container;
    }
}
