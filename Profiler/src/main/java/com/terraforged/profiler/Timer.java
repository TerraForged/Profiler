package com.terraforged.profiler;

import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;

public class Timer {

    private final Supplier<String> nameSupplier;
    private final StampedLock lock = new StampedLock();

    private String name;

    public Timer(Supplier<String> name) {
        this.nameSupplier = name;
    }

    public void punchIn() {
        Profiler.push(getName());
    }

    public void punchOut() {
        Profiler.pop();
    }

    private String getName() {
        long readStamp = lock.readLock();
        try {
            if (name != null) {
                return name;
            }
        } finally {
            lock.unlockRead(readStamp);
        }

        long writeStamp = lock.writeLock();
        try {
            if (name == null) {
                name = nameSupplier.get();
            }
            return name;
        } finally {
            lock.unlockWrite(writeStamp);
        }
    }

    public static Timer of(Supplier<String> name) {
        return new Timer(name);
    }
}
