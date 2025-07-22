package net.tracystacktrace.spawnerdropsloot;

import net.minecraft.client.Minecraft;
import net.tracystacktrace.spawnerdropsloot.hooks.f_TileEntityMobSpawner_mobID;
import net.tracystacktrace.spawnerdropsloot.hooks.m_TileEntityMobSpawner_updateDelay;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuppressWarnings("TryWithIdenticalCatches")
public final class HookTools {
    private static final boolean OLD_JAVA; //pre 8
    public static final boolean OBFUSCATED;

    private static final Unsafe UNSAFE;
    public static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    public static final m_TileEntityMobSpawner_updateDelay m_TileEntityMobSpawner_updateDelay = new m_TileEntityMobSpawner_updateDelay();
    public static final f_TileEntityMobSpawner_mobID f_TileEntityMobSpawner_mobID = new f_TileEntityMobSpawner_mobID();

    static  {
        boolean test_obf;
        try {
            Minecraft.class.getDeclaredField("theMinecraft");
            test_obf = false;
        } catch (NoSuchFieldException e) {
            test_obf = true;
        }
        OBFUSCATED = test_obf;

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

    public static void setStaticFinalField(Class<?> clazz, String original, String obfuscated, Object value) {
        Field field;
        try {
            field = clazz.getDeclaredField(OBFUSCATED ? obfuscated : original);
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

    public static Object getStaticField(Class<?> clazz, String original, String obfuscated) {
        Field field;
        try {
            field = clazz.getDeclaredField(OBFUSCATED ? obfuscated : original);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        if (OLD_JAVA) {
            try {
                field.setAccessible(true);
                return field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            final long offset = UNSAFE.staticFieldOffset(field);
            final Object base = UNSAFE.staticFieldBase(field);
            return UNSAFE.getObject(base, offset);
        }
    }
}
