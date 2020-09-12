package com.terraforged.profiler;

import java.util.Arrays;
import java.util.function.Function;

class InstanceStack implements Function<String, Section> {

    private final Instance[] stack;
    private final int max;

    private int pointer = -1;

    InstanceStack(int maxSize) {
        stack = new Instance[maxSize];
        max = maxSize - 1;
    }

    void clear() {
        Arrays.fill(stack, null);
    }

    public void push(Section section, long timestamp) {
        if (pointer < max) {
            stack[++pointer] = section.getTimer().punchIn(timestamp);
        }
    }

    public void pop() {
        if (pointer > -1) {
            Instance instance = stack[pointer--];
            instance.punchOut(System.nanoTime());
        }
    }

    @Override
    public Section apply(String s) {
        if (pointer > -1) {
            Instance instance = stack[pointer];
            return new Section(s, instance.getSection());
        }
        return new Section(s, null);
    }
}
