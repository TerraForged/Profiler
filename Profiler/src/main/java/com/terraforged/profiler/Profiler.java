package com.terraforged.profiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class Profiler {

    public static final AtomicReference<Consumer<String>> messageSink = new AtomicReference<>();

    private static final AtomicLong TIMER = new AtomicLong();
    private static final long INTERVAL_NANOS = TimeUnit.SECONDS.toNanos(10);

    private static final Map<String, Section> sections = new ConcurrentHashMap<>();
    private static final List<Section> roots = Collections.synchronizedList(new ArrayList<>());
    private static final ThreadLocal<InstanceStack> stack = ThreadLocal.withInitial(() -> new InstanceStack(10));

    public static void attachLogger(Consumer<String> consumer) {
        messageSink.set(consumer);
    }

    static void addRoot(Section section) {
        roots.add(section);
        Collections.sort(roots);
    }

    static void push(String section) {
        InstanceStack stack = Profiler.stack.get();
        Section profile = sections.computeIfAbsent(section, stack);
        stack.push(profile, nanos());
    }

    static void pop() {
        Profiler.stack.get().pop();
    }

    private static long nanos() {
        long now = System.nanoTime();
        long time = TIMER.get();
        if (now - time > INTERVAL_NANOS && TIMER.compareAndSet(time, now)) {
            print();
        }
        return now;
    }

    private static void print() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("\n############################\n");
        sb.append("TIMINGS REPORT\n");

        double total = roots.stream().sorted()
                .peek(root -> printSection(root, sb.append("# ")))
                .mapToDouble(Section::getAverage)
                .sum();

        sb.append(String.format("Average: %.3fms Per Chunk\n", total / 1_000_000));

        Profiler.messageSink.get().accept(sb.toString());
    }

    private static void printSection(Section section, StringBuilder stringBuilder) {
        double average = section.getAverage() / 1_000_000;
        stringBuilder.append(section.getName()).append(": ").append(String.format("%.3fms", average)).append('\n');
        section.children().forEach(child -> printSection(child, stringBuilder.append(" - ")));
    }
}
