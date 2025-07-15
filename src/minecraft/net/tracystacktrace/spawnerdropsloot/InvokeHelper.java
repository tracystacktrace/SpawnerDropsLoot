package net.tracystacktrace.spawnerdropsloot;

import net.minecraft.src.Block;
import net.minecraft.src.StepSound;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeHelper {

    public static boolean OBFUSCATED;

    public static void invokeVoidE(
            Class<?> clazz, Object instance,
            String original, String obfuscated
    ) {
        try {
            final Method method = clazz.getDeclaredMethod(OBFUSCATED ? obfuscated : original);
            method.setAccessible(true);
            method.invoke(instance);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getStaticField(Class<?> clazz, String original, String obfuscated) {
        try {
            final Field field = clazz.getDeclaredField(OBFUSCATED ? obfuscated : original);
            field.setAccessible(true);
            return field.get(null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setStaticField(Class<?> clazz, String original, String obfuscated, Object value) {
        try {
            final Field field = clazz.getDeclaredField(OBFUSCATED ? obfuscated : original);
            field.setAccessible(true);
            field.set(null, value);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getVirtualField(
            Class<?> clazz, Object instance,
            String original, String obfuscated
    ) {
        try {
            final Field field = clazz.getDeclaredField(OBFUSCATED ? obfuscated : original);
            field.setAccessible(true);
            return field.get(instance);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void invoke_setHardness(Object instance, float value) {
        try {
            final Method method = Block.class.getDeclaredMethod(OBFUSCATED ? "c" : "setHardness", float.class);
            method.setAccessible(true);
            method.invoke(instance, value);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void invoke_setStepSound(Object instance, StepSound stepSound) {
        try {
            final Method method = Block.class.getDeclaredMethod(OBFUSCATED ? "a" : "setStepSound", StepSound.class);
            method.setAccessible(true);
            method.invoke(instance, stepSound);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void invoke_disableStats(Object instance) {
        try {
            final Method method = Block.class.getDeclaredMethod(OBFUSCATED ? "q" : "disableStats");
            method.setAccessible(true);
            method.invoke(instance);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runObfuscationTester() {
        try {
            Class<?> test = Class.forName("net.minecraft.src.ModLoader");
            OBFUSCATED = test != null;
        } catch (ClassNotFoundException e) {
            OBFUSCATED = true;
        }
    }
}
