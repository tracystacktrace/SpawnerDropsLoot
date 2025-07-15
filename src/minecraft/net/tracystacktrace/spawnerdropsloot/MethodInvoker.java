package net.tracystacktrace.spawnerdropsloot;

import net.minecraft.src.Block;
import net.minecraft.src.StepSound;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class MethodInvoker {
    public static void invokeVoidE(
            Class<?> clazz, Object instance,
            String original, String obfuscated
    ) {
        try {
            final Method method = clazz.getDeclaredMethod(FieldInvoker.OBFUSCATED ? obfuscated : original);
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

    public static Object getVirtualField(
            Class<?> clazz, Object instance,
            String original, String obfuscated
    ) {
        try {
            final Field field = clazz.getDeclaredField(FieldInvoker.OBFUSCATED ? obfuscated : original);
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
            final Method method = Block.class.getDeclaredMethod(FieldInvoker.OBFUSCATED ? "c" : "setHardness", float.class);
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
            final Method method = Block.class.getDeclaredMethod(FieldInvoker.OBFUSCATED ? "a" : "setStepSound", StepSound.class);
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
            final Method method = Block.class.getDeclaredMethod(FieldInvoker.OBFUSCATED ? "q" : "disableStats");
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
}
