package com.jpprime.infinitychest.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class LootGenerator {
    private static final Random RANDOM = new Random();
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
        SimpleContainer container = new SimpleContainer(27);
        List<Item> pool = loadPool();
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

    private static List<Item> loadPool() {
        List<Item> pool = new ArrayList<>();
        Path file = FMLPaths.CONFIGDIR.get().resolve("infinitychest_loot.json");
        try {
            Files.createDirectories(file.getParent());
            if (Files.notExists(file)) {
                JsonObject defaults = new JsonObject();
                JsonArray items = new JsonArray();
                DEFAULT_ITEMS.forEach(items::add);
                defaults.add("items", items);
                Files.writeString(file, defaults.toString() + System.lineSeparator());
            }
            JsonObject config = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
            JsonArray items = config.getAsJsonArray("items");
            if (items != null) {
                for (JsonElement element : items) {
                    if (!element.isJsonPrimitive()) continue;
                    ResourceLocation id = ResourceLocation.tryParse(element.getAsString());
                    if (id == null || !BuiltInRegistries.ITEM.containsKey(id)) continue;
                    Item item = BuiltInRegistries.ITEM.get(id);
                    if (item != Items.AIR) pool.add(item);
                }
            }
        } catch (Exception exception) {
            System.err.println("[Infinity Chest] Could not read config; using default loot. " + exception.getMessage());
        }
        if (pool.isEmpty()) {
            for (String value : DEFAULT_ITEMS) {
                ResourceLocation id = ResourceLocation.tryParse(value);
                if (id != null && BuiltInRegistries.ITEM.containsKey(id)) {
                    Item item = BuiltInRegistries.ITEM.get(id);
                    if (item != Items.AIR) pool.add(item);
                }
            }
        }
        return pool;
    }
}
