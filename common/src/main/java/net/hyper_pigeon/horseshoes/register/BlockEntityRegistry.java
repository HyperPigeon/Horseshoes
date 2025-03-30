package net.hyper_pigeon.horseshoes.register;

import net.hyper_pigeon.horseshoes.platform.Services;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public final class BlockEntityRegistry {
    public static void init() {}

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String id, Supplier<BlockEntityType<T>> blockEntity) {
        return Services.PLATFORM.registerBlockEntity(id, blockEntity);
    }
}
