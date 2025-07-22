package net.tracystacktrace.spawnerdropsloot.hooks;

import net.minecraft.src.TileEntityMobSpawner;
import net.tracystacktrace.spawnerdropsloot.HookTools;
import net.tracystacktrace.spawnerdropsloot.MethodHook;

import java.lang.reflect.Method;

public class m_TileEntityMobSpawner_updateDelay extends MethodHook<Void> {
    @Override
    protected Method getMethod() throws NoSuchMethodException {
        return TileEntityMobSpawner.class.getDeclaredMethod(HookTools.OBFUSCATED ? "d" : "updateDelay");
    }
}
