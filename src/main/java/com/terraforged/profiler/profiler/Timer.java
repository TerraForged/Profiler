package com.terraforged.profiler.profiler;

class Timer implements AutoCloseable {

    private final Section profile;

    private long time;

    public Timer(Section profile) {
        this.profile = profile;
    }

    public Section getProfile() {
        return profile;
    }

    protected Timer punchIn(long timestamp) {
        this.time = timestamp;
        return this;
    }

    protected Timer punchOut(long timestamp) {
        long duration = timestamp - time;
        profile.inc(duration);
        return this;
    }

    @Override
    public void close() {
        punchOut(System.nanoTime());
    }
}
