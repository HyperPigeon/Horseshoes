package net.hyper_pigeon.horseshoes.register;

import net.hyper_pigeon.horseshoes.platform.Services;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public final class BlockRegistry {

    public static void init() {}

    private static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        return Services.PLATFORM.registerBlock(id, block);
    }
}