package net.tracystacktrace.spawnerdropsloot;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public final class HookTools {
    public static final boolean OBFUSCATED = false; //server environment
    private static final Unsafe UNSAFE;

    static {
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
