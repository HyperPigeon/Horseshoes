package net.hyper_pigeon.horseshoes.register;


import net.hyper_pigeon.horseshoes.platform.Services;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public final class SoundRegistry {

    public static void init() {}

    private static <T extends SoundEvent> Supplier<T> registerSound(String id, Supplier<T> sound) {
        return Services.PLATFORM.registerSound(id, sound);
    }
}
