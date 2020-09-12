package com.terraforged.profiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Section implements Comparable<Section> {

    private static final int SAMPLE_RESET_POINT = 100_000;

    private final String name;
    private final boolean root;
    private final AtomicLong time = new AtomicLong();
    private final AtomicLong average = new AtomicLong();
    private final AtomicInteger hits = new AtomicInteger();

    private final long order;
    private final List<Section> children = Collections.synchronizedList(new ArrayList<>());
    private final ThreadLocal<Instance> instance = ThreadLocal.withInitial(() -> new Instance(this));

    public Section(String name, long ordinal) {
        this.root = true;
        this.name = name;
        this.order = ordinal;
    }

    public Section(String name, Section parent) {
        this.root = parent == null;
        this.order = System.currentTimeMillis();
        this.name = root ? name : parent.getPath() + "/" + name;
        if (parent != null) {
            parent.children.add(this);
            Collections.sort(parent.children);
        } else {
            Profiler.addRoot(this);
        }
    }

    public boolean isRoot() {
        return root;
    }

    public String getName() {
        return name;
    }

    private String getPath() {
        return getName().toLowerCase();
    }

    public Stream<Section> children() {
        return children.stream();
    }

    public Instance getTimer() {
        return instance.get();
    }

    public int getSamples() {
        return hits.get();
    }

    public double getAverage() {
        double hits = this.hits.get();
        if (hits == 0) {
            return 0.0;
        }
        return time.get() / hits;
    }

    public long getAverage(TimeUnit unit) {
        int hits = this.hits.get();
        if (hits == 0) {
            return 0L;
        }
        return unit.convert(time.get() / hits, TimeUnit.NANOSECONDS);
    }

    public double getRunningAverage() {
        double average = this.average.get();
        if (average > 0) {
            return average;
        }
        return getAverage();
    }

    @Override
    public int compareTo(Section o) {
        return Long.compare(order, o.order);
    }

    protected void inc(long timeNanos) {
        int hits = this.hits.incrementAndGet();

        if (hits > SAMPLE_RESET_POINT && this.hits.compareAndSet(hits, 1)) {
            long average = time.get() / hits;
            average += this.average.get();
            average /= 2L;
            this.average.set(average);
            this.time.set(timeNanos);
        } else {
            this.time.addAndGet(timeNanos);
        }
    }
}
