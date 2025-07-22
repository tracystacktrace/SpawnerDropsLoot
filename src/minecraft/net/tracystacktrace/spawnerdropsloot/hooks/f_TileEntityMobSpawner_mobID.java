package net.tracystacktrace.spawnerdropsloot.hooks;

import net.minecraft.src.TileEntityMobSpawner;
import net.tracystacktrace.spawnerdropsloot.FieldHook;
import net.tracystacktrace.spawnerdropsloot.HookTools;

import java.lang.reflect.Field;

public class f_TileEntityMobSpawner_mobID extends FieldHook<String> {
    public f_TileEntityMobSpawner_mobID() {
        super(true);
    }

    @Override
    protected Field getField() throws NoSuchFieldException {
        return TileEntityMobSpawner.class.getDeclaredField(HookTools.OBFUSCATED ? "i" : "mobID");
    }
}
