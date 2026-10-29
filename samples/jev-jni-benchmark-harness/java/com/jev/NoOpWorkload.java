package com.jev;

/** No-op workload used to estimate wrapper baseline overhead. */
public final class NoOpWorkload implements BatchWorkload {
    @Override
    public void executeBatch() {
        // Intentionally empty: this is a baseline, not useful application work.
    }

    @Override
    public long operationsPerBatch() {
        return 0;
    }
}
