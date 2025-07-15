package net.tracystacktrace.spawnerdropsloot;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FinalFieldReplacer {
    private static final boolean OLD_JAVA; //pre 8
    private static final Unsafe UNSAFE;

    static {
        OLD_JAVA = System.getProperty("java.version").startsWith("1.");

        Unsafe unsafe = null;
        if (!OLD_JAVA) {
            try {
                Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                unsafe = (Unsafe) unsafeField.get(null);
            } catch (Exception e) {
                System.err.println("Could not get Unsafe instance: " + e.getMessage());
            }
        }
        UNSAFE = unsafe;
    }

    public static void setStaticFinal(Class<?> clazz, String original, String obfuscated, Object value) {
        Field field;
        try {
            field = clazz.getDeclaredField(InvokeHelper.OBFUSCATED ? obfuscated : original);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        if (OLD_JAVA) {
            try {
                final Field modifiers = Field.class.getDeclaredField("modifiers");
                modifiers.setAccessible(true);
                modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

                field.set(null, value);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            final long offset = UNSAFE.staticFieldOffset(field);
            final Object base = UNSAFE.staticFieldBase(field);
            UNSAFE.putObject(base, offset, value);
        }

    }
}
