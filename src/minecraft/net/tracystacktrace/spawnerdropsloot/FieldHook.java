package net.tracystacktrace.spawnerdropsloot;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;

@SuppressWarnings("TryWithIdenticalCatches")
public abstract class FieldHook<T> {
    private final boolean getterOrSetter;
    protected boolean initialized;
    protected MethodHandle handle;

    /**
     * Getter - true
     * <br>
     * Setter - false
     */
    protected FieldHook(boolean getterOrSetter) {
        this.getterOrSetter = getterOrSetter;
    }

    protected abstract Field getField() throws NoSuchFieldException;

    @SuppressWarnings("unchecked")
    public T invoke(Object instance) {
        if (!initialized) {
            try {
                final Field field = this.getField();
                field.setAccessible(true);
                handle = this.getterOrSetter ? HookTools.LOOKUP.unreflectGetter(field) : HookTools.LOOKUP.unreflectSetter(field);
                initialized = true;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            return (T) handle.invoke(instance);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
