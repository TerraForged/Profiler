package com.terraforged.profiler;

import java.util.concurrent.locks.StampedLock;

public class StringPool {

    private int pointer;
    private final int maxIndex;
    private final Resource[] pool;
    private final StampedLock lock = new StampedLock();

    public StringPool(int size) {
        this.pool = new Resource[size];
        this.maxIndex = size - 1;
        this.pointer = maxIndex;
        for (int i = 0; i < size; i++) {
            pool[i] = new Resource();
        }
    }

    public Resource retain() {
        long write = lock.writeLock();
        try {
            if (pointer < 0) {
                return new Resource();
            }
            Resource resource = pool[pointer];
            pool[pointer] = null;
            pointer--;
            return resource;
        } finally {
            lock.unlockWrite(write);
        }
    }

    private void restore(Resource resource) {
        long stamp = lock.writeLock();
        try {
            if (pointer < maxIndex) {
                pool[++pointer] = resource;
            }
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public class Resource implements AutoCloseable {

        private final StringBuilder sb = new StringBuilder(64);

        public StringBuilder get() {
            return sb;
        }

        @Override
        public void close() {
            sb.setLength(0);
            restore(this);
        }
    }

    public static StringBuilder center(StringBuilder sb, String text, int width) {
        int left = (width - text.length()) / 2;
        return pad(sb, ' ', left).append(text);
    }

    public static StringBuilder pad(StringBuilder sb, char c, int from, int to) {
        return pad(sb, c, to - from);
    }

    public static StringBuilder pad(StringBuilder sb, char c, int size) {
        for (int i = 0; i < size; i++) {
            sb.append(c);
        }
        return sb;
    }
}
