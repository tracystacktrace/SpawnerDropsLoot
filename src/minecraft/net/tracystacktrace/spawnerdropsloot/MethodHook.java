package net.tracystacktrace.spawnerdropsloot;

import net.minecraft.src.mod_SpawnerDropsLoot;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

@SuppressWarnings("TryWithIdenticalCatches")
public abstract class MethodHook<T> {
    protected boolean initialized;
    protected MethodHandle handle;

    protected abstract Method getMethod() throws NoSuchMethodException;

    @SuppressWarnings("unchecked")
    public T invoke(Object... args) {
        if (!initialized) {
            try {
                final Method method = this.getMethod();
                method.setAccessible(true);
                handle = HookTools.LOOKUP.unreflect(method);
                initialized = true;
                mod_SpawnerDropsLoot.LOGGER.debug("Initialized hook for method: {}", method.toString());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            return (T) handle.invokeWithArguments(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
