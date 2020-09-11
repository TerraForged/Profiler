package com.terraforged.profiler;

class Instance implements AutoCloseable {

    private final Section profile;

    private long time;

    public Instance(Section profile) {
        this.profile = profile;
    }

    public Section getProfile() {
        return profile;
    }

    protected Instance punchIn(long timestamp) {
        this.time = timestamp;
        return this;
    }

    protected Instance punchOut(long timestamp) {
        long duration = timestamp - time;
        profile.inc(duration);
        return this;
    }

    @Override
    public void close() {
        punchOut(System.nanoTime());
    }
}
