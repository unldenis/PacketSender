package com.github.unldenis.packetsender;

import com.github.unldenis.packetsender.util.*;

public class WrappedItem extends WrappedPacket {

    private static final Class<?> ITEM;

    static {
        ITEM = ReflectionUtils.getNMSClass("network.syncher", "DataWatcher$Item");
    }

    private final WrappedDataWatcherObject dataWatcherObject;
    private final Object value;

    public WrappedItem(WrappedDataWatcherObject dataWatcherObject, Object value) {
        super(ITEM);
        this.dataWatcherObject = dataWatcherObject;
        this.value = value;
        load();
    }

    private void load() {
        writeDataWatcherObject(0, dataWatcherObject);
        write(1, value);
        writeBoolean(0, true);
    }

    public Object toNMS() {
        return getRawNMSPacket();
    }
}
