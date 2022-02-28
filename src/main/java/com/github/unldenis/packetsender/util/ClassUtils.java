package com.github.unldenis.packetsender.util;

import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.*;

public class ClassUtils {

    private static final Map<Class<?>, String> CLASS_SIMPLE_NAME_CACHE = new ConcurrentHashMap<>();

    public static String getSimpleName(@NotNull Class<?> clazz) {
        return CLASS_SIMPLE_NAME_CACHE.computeIfAbsent(clazz, k -> clazz.getSimpleName());
    }
}
