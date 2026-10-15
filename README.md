# Infinity Chest

A NeoForge mod for **Minecraft Java 1.21.1**.

- Craft the Infinity Chest with 8 obsidian around 1 normal chest.
- Open it to receive a fresh, private set of randomized items in a chest-style inventory.
- Items are generated separately for each opening/player; other players do not share that opening's loot inventory.
- The default loot pool contains vanilla items. Modded item IDs can be added to the list in `LootGenerator.java`; a JSON config is planned for a later iteration.
- Requires NeoForge 21.1.x and Java 21.

The JAR is built automatically by GitHub Actions after a push. Open the repository's **Actions** tab, select the latest successful **Build Infinity Chest** run, then download the artifact named `InfinityChest-Minecraft-1.21.1`.

## Current prototype notes
This is an initial test build. Each opening rolls new loot, and anything left in the GUI is not saved when it closes. The chest block is intentionally simple and currently uses an obsidian texture.
