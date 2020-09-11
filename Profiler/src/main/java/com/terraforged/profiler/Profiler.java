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

    private static final StringPool stringPool = new StringPool(4);
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
        if (roots.size() > 3) {
            Profiler.messageSink.get().accept(getReport());
        }
    }

    public static String getReport() {
        try (StringPool.Resource resource = stringPool.retain()) {
            final String title = "TIMINGS REPORT";
            final int nameWidth = getWidestName() + 6;
            final int pageWidth = nameWidth + 40;

            StringBuilder sb = resource.get().append('\n');
            StringPool.pad(sb, '#', pageWidth).append('\n');
            StringPool.center(sb, title, pageWidth).append('\n');
            StringPool.pad(sb, '#', pageWidth).append('\n');

            double total = roots.stream().sorted()
                    .peek(root -> printSection(root, sb.append("- "), nameWidth, 2))
                    .mapToDouble(Section::getAverage)
                    .sum();

            String summary = String.format("AVERAGE: %.3fms PER CHUNK\n", nanoToMillis(total));
            StringPool.center(sb, summary, pageWidth).append('\n');
            StringPool.pad(sb, '#', pageWidth).append('\n');

            return sb.toString();
        }
    }

    private static int getWidestName() {
        return sections.values().stream().map(Section::getName).mapToInt(String::length).max().orElse(0);
    }

    private static void printSection(Section section, StringBuilder stringBuilder, int widest, int indent) {
        String prefix = indent == 2 ? "- " : "  ";

        String name = section.getName();
        stringBuilder.append(name);
        StringPool.pad(stringBuilder, ' ', name.length() + indent, widest);

        String average = String.format("%.03fms", nanoToMillis(section.getAverage()));
        StringPool.pad(stringBuilder, ' ', average.length(), 9).append(prefix).append("Average: ").append(average);
        StringPool.pad(stringBuilder, ' ', 4).append(prefix).append("Samples: ").append(section.getSamples()).append('\n');

        section.children().forEach(child -> printSection(child, stringBuilder.append("   "), widest, 3));
    }

    private static double nanoToMillis(double nanos) {
        return nanos / 1_000_000;
    }
}
