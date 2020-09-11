package com.terraforged.profiler.profiler;

import com.terraforged.profiler.ProfilerMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Profiler {

    private static final AtomicLong TIMER = new AtomicLong();
    private static final long INTERVAL_NANOS = TimeUnit.SECONDS.toNanos(10);

    private static final Map<String, Section> sections = new ConcurrentHashMap<>();
    private static final List<Section> roots = Collections.synchronizedList(new ArrayList<>());
    private static final ThreadLocal<SectionStack> stack = ThreadLocal.withInitial(() -> new SectionStack(10));

    static void addRoot(Section section) {
        roots.add(section);
        Collections.sort(roots);
    }

    public static void push(String section) {
        SectionStack stack = Profiler.stack.get();
        Section profile = sections.computeIfAbsent(section, stack);
        stack.push(profile, nanos());
    }

    public static void pop() {
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

    public static void print() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("\n############################\n");
        sb.append("TIMINGS REPORT\n");

        double total = roots.stream().sorted()
                .peek(root -> printSection(root, sb.append("# ")))
                .mapToDouble(Section::getAverage)
                .sum();

        sb.append(String.format("Average: %.3fms Per Chunk\n", total / 1_000_000));

        ProfilerMod.LOG.debug(sb);
    }

    private static void printSection(Section section, StringBuilder stringBuilder) {
        double average = section.getAverage() / 1_000_000;
        stringBuilder.append(section.getName()).append(": ").append(String.format("%.3fms", average)).append('\n');
        section.children().forEach(child -> printSection(child, stringBuilder.append(" - ")));
    }
}
