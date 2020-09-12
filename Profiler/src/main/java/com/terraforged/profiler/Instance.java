package com.terraforged.profiler;

class Instance implements AutoCloseable {

    private final Section section;

    private long time;

    public Instance(Section profile) {
        this.section = profile;
    }

    public Section getSection() {
        return section;
    }

    protected Instance punchIn(long timestamp) {
        this.time = timestamp;
        return this;
    }

    protected Instance punchOut(long timestamp) {
        long duration = timestamp - time;
        section.inc(duration);
        return this;
    }

    @Override
    public void close() {
        punchOut(System.nanoTime());
    }
}
