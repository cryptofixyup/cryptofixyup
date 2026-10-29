package com.jev;

/**
 * A deterministic CPU-only stand-in so the harness can be smoke-tested without
 * the JEV repository. Replace this adapter with the real JEV batch operation
 * when that source/classpath is available.
 */
public final class SyntheticJevWorkload implements BatchWorkload {
    private final int operations;
    private volatile long sink;

    public SyntheticJevWorkload(int operations) {
        if (operations < 1) {
            throw new IllegalArgumentException("operations must be >= 1");
        }
        this.operations = operations;
    }

    @Override
    public void executeBatch() {
        long value = sink + 0x9E3779B97F4A7C15L;
        for (int i = 0; i < operations; i++) {
            value ^= (value << 7);
            value ^= (value >>> 9);
            value *= 0xD6E8FEB86659FD93L;
        }
        sink = value;
    }

    @Override
    public long operationsPerBatch() {
        return operations;
    }
}
