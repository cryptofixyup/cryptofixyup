package com.jev;

/** Measures the time spent invoking a workload method from native code. */
public final class NativeBenchHarness {
    static {
        System.loadLibrary("jevbench");
    }

    /** Creates a harness backed by the {@code jevbench} native library. */
    public NativeBenchHarness() {}

    /**
     * Invokes {@code workload.executeBatch()} once and returns its measured
     * duration in nanoseconds. The workload must expose a public, no-argument
     * {@code void executeBatch()} method.
     *
     * <p>The returned duration does not include the complete Java-to-native
     * transition. Measure this method with {@link System#nanoTime()} around the
     * call to observe the end-to-end wrapper cost as well.</p>
     *
     * @param workload object exposing {@code public void executeBatch()}
     * @return elapsed nanoseconds inside the native timing region
     */
    public native long measureBatchNanos(Object workload);
}
