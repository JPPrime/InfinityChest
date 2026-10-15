package com.jpprime.infinitychest.block;

import com.jpprime.infinitychest.InfinityChestMod;
import com.jpprime.infinitychest.loot.LootGenerator;
import com.jpprime.infinitychest.menu.InfinityChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SimpleMenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class InfinityChestBlock extends Block {
    public InfinityChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            MenuProvider provider = new SimpleMenuProvider(
                    (id, inventory, ignoredPlayer) -> new InfinityChestMenu(id, inventory,
                            LootGenerator.create(level.registryAccess(), serverPlayer)),
                    Component.translatable("container.infinitychest.infinity_chest"));
            serverPlayer.openMenu(provider);
        }
        return InteractionResult.SUCCESS;
    }
}
