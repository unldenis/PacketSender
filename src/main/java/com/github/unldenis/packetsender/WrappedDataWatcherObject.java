package com.github.unldenis.packetsender;

import com.github.unldenis.packetsender.util.ReflectionUtils;
import org.jetbrains.annotations.*;


public class WrappedDataWatcherObject extends WrappedPacket {

    public static final Class<?> DATA_WATCHER_OBJECT;

    static {
        DATA_WATCHER_OBJECT = ReflectionUtils.getNMSClass("network.syncher", "DataWatcherObject");
    }

    private final int index;
    private final WrappedDataWatcherSerializer serializer;

    public WrappedDataWatcherObject(int index, @NotNull WrappedDataWatcherSerializer serializer) {
        super(DATA_WATCHER_OBJECT);
        this.index = index;
        this.serializer = serializer;
        load();
    }

    private void load() {
        writeInt(0, index);
        write(1, serializer.toNMS());
    }

    public Object toNMS() {
        return getRawNMSPacket();
    }
}
