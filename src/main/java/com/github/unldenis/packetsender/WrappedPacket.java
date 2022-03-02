package com.github.unldenis.packetsender;

import com.github.unldenis.packetsender.util.ReflectionUtils;
import com.github.unldenis.packetsender.util.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

public class WrappedPacket {

    private final Class<?> packetClass;
    private final Object rawNMSPacket;
    private final Map<Class<?>, Field[]> field_cache = new ConcurrentHashMap<>();

    public WrappedPacket(@NotNull Class<?> packetClass) {
        this.packetClass = packetClass;
        try {
            this.rawNMSPacket = UnsafeUtils.allocateInstance(packetClass);
        } catch (InstantiationException e) {
            throw new UnsupportedOperationException("Unable to instantiate " + ClassUtils.getSimpleName(packetClass) + " class", e);
        }
    }

    public WrappedPacket(@NotNull String packetName) {
        this(Objects.requireNonNull(ReflectionUtils.getNMSClass("network.protocol.game", packetName)));
    }

    public void sendPacketSync(@NotNull Player player) {
        ReflectionUtils.sendPacketSync(player, rawNMSPacket);
    }

    private Field getField(@NotNull Class<?> type, int index) {
        Field[] fields = field_cache.computeIfAbsent(type, k -> getFields(type));
        if(index >= fields.length) {
            throw new IndexOutOfBoundsException(String.format("The index field %d of type %s of class %s does not exist",
                    index, ClassUtils.getSimpleName(type), ClassUtils.getSimpleName(packetClass)));
        }
        return fields[index];
    }

    private @NotNull Field[] getFields(@NotNull Class<?> type) {
        LinkedList<Field> fields = new LinkedList<>();
        for(Field field: packetClass.getDeclaredFields()) {
            if(field.getType().equals(type)) {
                fields.add(field);
            }
        }
        return fields.toArray(new Field[0]);
    }

    public void write(@NotNull Class<?> type, int index, Object value) {
        Field field = getField(type, index);
        UnsafeUtils.setFinal(rawNMSPacket, field, value);
    }

    @ApiStatus.ScheduledForRemoval
    @Deprecated
    public WrappedPacket write(int index, Object value) {
        UnsafeUtils.setFinal(rawNMSPacket, packetClass.getDeclaredFields()[index], value);
        return this;
    }

    public WrappedPacket writeDataWatcherObject(int index, WrappedDataWatcherObject value) {
        write(value.toNMS().getClass(), index, value.toNMS());
        return this;
    }
    
    public WrappedPacket writeBoolean(int index, boolean value) {
        write(boolean.class, index, value);
        return this;
    }

    public WrappedPacket writeByte(int index, byte value) {
        write(byte.class, index, value);
        return this;
    }

    public WrappedPacket writeShort(int index, short value) {
        write(short.class, index, value);
        return this;
    }

    public WrappedPacket writeInt(int index, int value) {
        write(int.class, index, value);
        return this;
    }

    public WrappedPacket writeLong(int index, long value) {
        write(long.class, index, value);
        return this;
    }

    public WrappedPacket writeFloat(int index, float value) {
        write(float.class, index, value);
        return this;
    }

    public WrappedPacket writeDouble(int index, double value) {
        write(double.class, index, value);
        return this;
    }

    public WrappedPacket writeString(int index, String value) {
        write(String.class, index, value);
        return this;
    }

    public WrappedPacket writeUUID(int index, UUID value) {
        write(UUID.class, index, value);
        return this;
    }

    public WrappedPacket writeObject(int index, Object value) {
        write(value.getClass(), index, value);
        return this;
    }

    public WrappedPacket writeBooleanArray(int index, boolean[] array) {
        write(boolean[].class, index, array);
        return this;
    }

    public WrappedPacket writeByteArray(int index, byte[] value) {
        write(byte[].class, index, value);
        return this;
    }

    public WrappedPacket writeShortArray(int index, short[] value) {
        write(short[].class, index, value);
        return this;
    }

    public WrappedPacket writeIntArray(int index, int[] value) {
        write(int[].class, index, value);
        return this;
    }

    public WrappedPacket writeLongArray(int index, long[] value) {
        write(long[].class, index, value);
        return this;
    }

    public WrappedPacket writeFloatArray(int index, float[] value) {
        write(float[].class, index, value);
        return this;
    }

    public WrappedPacket writeDoubleArray(int index, double[] value) {
        write(double[].class, index, value);
        return this;
    }

    public WrappedPacket writeStringArray(int index, String[] value) {
        write(String[].class, index, value);
        return this;
    }

    public WrappedPacket writeList(int index, List<Object> list) {
        write(List.class, index, list);
        return this;
    }

    protected Object getRawNMSPacket() {
        return rawNMSPacket;
    }
}
