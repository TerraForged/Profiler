package com.terraforged.profiler.profiler;

import java.util.function.Function;

class SectionStack implements Function<String, Section> {

    private final Timer[] stack;
    private final int max;

    private int pointer = -1;

    SectionStack(int maxSize) {
        stack = new Timer[maxSize];
        max = maxSize - 1;
    }

    public void push(Section section, long timestamp) {
        if (pointer < max) {
            stack[++pointer] = section.getTimer().punchIn(timestamp);
        }
    }

    public void pop() {
        if (pointer > -1) {
            Timer timer = stack[pointer--];
            timer.punchOut(System.nanoTime());
        }
    }

    @Override
    public Section apply(String s) {
        if (pointer > -1) {
            Timer timer = stack[pointer];
            return new Section(s, timer.getProfile());
        }
        return new Section(s, null);
    }
}
