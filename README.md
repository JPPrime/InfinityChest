# Infinity Chest

A NeoForge mod for **Minecraft Java 1.21.1**.

- Craft the Infinity Chest with 8 obsidian around 1 normal chest.
- Open it to receive a fresh set of randomized items in a private chest-style inventory.
- Each player's opened inventory is separate. Closing the GUI discards anything left inside; opening again rolls new loot.
- On first launch, the mod creates `config/infinitychest_loot.json`. Edit its `items` list to include item registry IDs from any installed modpack, for example `"minecraft:diamond"`. Pixelmon/Cobblemon IDs must match the exact IDs registered by the version you have installed.
- Requires NeoForge 21.1.x and Java 21.

## Download the test JAR
After the GitHub Actions build succeeds, open the repository's **Actions** tab, select the latest successful **Build Infinity Chest** run, and download `InfinityChest-Minecraft-1.21.1`.

This is an initial test build. The block currently uses an obsidian texture and the loot is random rather than a balanced progression system.
