package net.tracystacktrace.spawnerdropsloot;

import net.tracystacktrace.spawnerdropsloot.hooks.f_TileEntityMobSpawner_mobID;
import net.tracystacktrace.spawnerdropsloot.hooks.m_TileEntityMobSpawner_updateDelay;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public final class HookTools {
    public static final boolean OBFUSCATED;

    private static final Unsafe UNSAFE;
    public static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    public static final m_TileEntityMobSpawner_updateDelay m_TileEntityMobSpawner_updateDelay = new m_TileEntityMobSpawner_updateDelay();
    public static final f_TileEntityMobSpawner_mobID f_TileEntityMobSpawner_mobID = new f_TileEntityMobSpawner_mobID();

    static {
        boolean test_obf;
        try {
            net.minecraft.client.Minecraft.class.getDeclaredField("theMinecraft");
            test_obf = false;
        } catch (NoSuchFieldException e) {
            test_obf = true;
        }
        OBFUSCATED = test_obf;

        try {
            final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            UNSAFE = (Unsafe) unsafeField.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Could not get Unsafe instance: " + e.getMessage());
        }
    }

    public static void setStaticFinalField(Class<?> clazz, String original, String obfuscated, Object value) {
        final Field field;
        try {
            field = clazz.getDeclaredField(OBFUSCATED ? obfuscated : original);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        final Object base = UNSAFE.staticFieldBase(field);
        final long offset = UNSAFE.staticFieldOffset(field);
        UNSAFE.putObject(base, offset, value);
    }

    public static Object getStaticField(Class<?> clazz, String original, String obfuscated) {
        final Field field;
        try {
            field = clazz.getDeclaredField(OBFUSCATED ? obfuscated : original);
            field.setAccessible(true);
            return field.get(null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
