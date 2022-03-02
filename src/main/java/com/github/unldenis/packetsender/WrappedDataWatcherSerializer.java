package com.github.unldenis.packetsender;

import com.github.unldenis.packetsender.util.*;

import java.lang.reflect.*;
import java.util.*;

import static com.github.unldenis.packetsender.util.ReflectionUtils.getTypeArgument;
import static com.github.unldenis.packetsender.util.ReflectionUtils.getUncheckedField;


public class WrappedDataWatcherSerializer {

    //Serializers
    public static WrappedDataWatcherSerializer BYTE;
    public static WrappedDataWatcherSerializer INTEGER;
    public static WrappedDataWatcherSerializer FLOAT;
    public static WrappedDataWatcherSerializer STRING;
    public static WrappedDataWatcherSerializer BOOLEAN;
    public static WrappedDataWatcherSerializer OPTIONAL_INT;

    //Optionals
    public static WrappedDataWatcherSerializer OPTIONAL_UUID;

    static {
        Class<?> dataWatcherRegistryClass = ReflectionUtils.getNMSClass("network.syncher", "DataWatcherRegistry");
        Class<?> dataWatcherSerializerClass = ReflectionUtils.getNMSClass("network.syncher", "DataWatcherSerializer");
        Arrays
            .stream(dataWatcherRegistryClass.getDeclaredFields())
            .filter(field -> Modifier.isStatic(field.getModifiers()))
            .filter(field -> Modifier.isFinal(field.getModifiers()))
            .filter(field -> field.getType().equals(dataWatcherSerializerClass))
            .forEachOrdered(field -> {
                Object[] classes = getTypeArgument(field);
                Class<?> serializerType = (Class<?>) classes[0];
                Object rawSerializer = getUncheckedField(field);
                if(classes.length > 1) {
                    // java.util.Optional#of
                    if (serializerType.equals(UUID.class)) {
                        OPTIONAL_UUID = new WrappedDataWatcherSerializer(rawSerializer);
                    }
                    //TODO NMS CLASSES

                } else {
                    if (serializerType.equals(Byte.class)) {
                        BYTE = new WrappedDataWatcherSerializer(rawSerializer);
                    } else if (serializerType.equals(Integer.class)) {
                        INTEGER = new WrappedDataWatcherSerializer(rawSerializer);
                    } else if (serializerType.equals(Float.class)) {
                        FLOAT = new WrappedDataWatcherSerializer(rawSerializer);
                    } else if (serializerType.equals(String.class)) {
                        STRING = new WrappedDataWatcherSerializer(rawSerializer);
                    } else if (serializerType.equals(Boolean.class)) {
                        BOOLEAN = new WrappedDataWatcherSerializer(rawSerializer);
                    } else if (serializerType.equals(OptionalInt.class)) {
                        OPTIONAL_INT = new WrappedDataWatcherSerializer(rawSerializer);
                    }
                    // TODO NMS CLASSES
                }
            });
    }

    private final Object rawSerializer;

    public WrappedDataWatcherSerializer(Object rawSerializer) {
        this.rawSerializer = rawSerializer;
    }

    public Object toNMS() {
        return rawSerializer;
    }
}
