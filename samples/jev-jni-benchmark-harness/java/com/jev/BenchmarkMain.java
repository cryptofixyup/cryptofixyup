package com.jev;

import java.util.Arrays;
import java.util.Locale;

/** Runs direct-Java and JNI-wrapped samples and reports latency distributions. */
public final class BenchmarkMain {
    private BenchmarkMain() {}

    private record Timings(
            double directP50,
            double directP95,
            double jniWallP50,
            double jniWallP95,
            double nativeBodyP50,
            double nativeBodyP95,
            double residualP50,
            double residualP95) {}

    public static void main(String[] args) {
        int operations = intArg(args, 0, 1_000, "operations per synthetic batch");
        int callsPerSample = intArg(args, 1, 100, "calls per sample");
        int samples = intArg(args, 2, 30, "sample count");
        if (operations < 1 || callsPerSample < 1 || samples < 2) {
            throw new IllegalArgumentException(
                    "operations and calls must be >= 1; samples must be >= 2");
        }

        NativeBenchHarness harness = new NativeBenchHarness();
        int warmupCalls = Math.max(100, callsPerSample);
        BatchWorkload noOp = new NoOpWorkload();
        BatchWorkload synthetic = new SyntheticJevWorkload(operations);

        System.out.println("JNI benchmark harness");
        System.out.printf(Locale.ROOT,
                "operations_per_batch=%d calls_per_sample=%d samples=%d warmup_calls=%d%n",
                operations, callsPerSample, samples, warmupCalls);
        System.out.println("NOTE: synthetic workload only; this is not a JEV result.");
        System.out.println();

        Timings baseline = benchmark(noOp, harness, warmupCalls, callsPerSample, samples);
        printTimings("No-op baseline", baseline);
        System.out.println();

        Timings result = benchmark(
                synthetic, harness, warmupCalls, callsPerSample, samples);
        printTimings("Synthetic CPU batch", result);
        System.out.printf(Locale.ROOT,
                "estimated_direct_ops_per_second=%.2f%n",
                throughput(operations, result.directP50()));
        System.out.printf(Locale.ROOT,
                "estimated_jni_ops_per_second=%.2f%n",
                throughput(operations, result.jniWallP50()));
        System.out.println();
        System.out.println("Interpretation: wall-minus-native is a residual, not pure JNI cost.");
        System.out.println("It includes transition, lookup, and measurement overhead; compare distributions, not one run.");
    }

    private static Timings benchmark(
            BatchWorkload workload,
            NativeBenchHarness harness,
            int warmupCalls,
            int callsPerSample,
            int samples) {
        for (int i = 0; i < warmupCalls; i++) {
            workload.executeBatch();
            harness.measureBatchNanos(workload);
        }

        double[] direct = new double[samples];
        double[] jniWall = new double[samples];
        double[] nativeBody = new double[samples];
        double[] residual = new double[samples];

        for (int sample = 0; sample < samples; sample++) {
            long[] jni = new long[2];
            long directElapsed;
            // Alternate order to reduce consistent order-related bias.
            if ((sample & 1) == 0) {
                directElapsed = measureDirect(workload, callsPerSample);
                jni = measureJni(workload, harness, callsPerSample);
            } else {
                jni = measureJni(workload, harness, callsPerSample);
                directElapsed = measureDirect(workload, callsPerSample);
            }

            direct[sample] = (double) directElapsed / callsPerSample;
            jniWall[sample] = (double) jni[0] / callsPerSample;
            nativeBody[sample] = (double) jni[1] / callsPerSample;
            residual[sample] = (double) Math.max(0L, jni[0] - jni[1]) / callsPerSample;
        }

        return new Timings(
                percentile(direct, 0.50), percentile(direct, 0.95),
                percentile(jniWall, 0.50), percentile(jniWall, 0.95),
                percentile(nativeBody, 0.50), percentile(nativeBody, 0.95),
                percentile(residual, 0.50), percentile(residual, 0.95));
    }

    /** Returns {total wall nanoseconds, sum of native timed-region nanoseconds}. */
    private static long[] measureJni(
            BatchWorkload workload, NativeBenchHarness harness, int calls) {
        long nativeTotal = 0L;
        long start = System.nanoTime();
        for (int i = 0; i < calls; i++) {
            long elapsed = harness.measureBatchNanos(workload);
            if (elapsed < 0) {
                throw new IllegalStateException("native timing returned a negative duration");
            }
            nativeTotal += elapsed;
        }
        return new long[] {System.nanoTime() - start, nativeTotal};
    }

    private static long measureDirect(BatchWorkload workload, int calls) {
        long start = System.nanoTime();
        for (int i = 0; i < calls; i++) {
            workload.executeBatch();
        }
        return System.nanoTime() - start;
    }

    private static void printTimings(String label, Timings t) {
        System.out.println(label + " (nanoseconds per call; p50 / p95)");
        System.out.printf(Locale.ROOT, "  direct_java_wall:       %.1f / %.1f%n", t.directP50(), t.directP95());
        System.out.printf(Locale.ROOT, "  jni_wrapper_wall:       %.1f / %.1f%n", t.jniWallP50(), t.jniWallP95());
        System.out.printf(Locale.ROOT, "  native_timed_region:    %.1f / %.1f%n", t.nativeBodyP50(), t.nativeBodyP95());
        System.out.printf(Locale.ROOT, "  wall_minus_native:      %.1f / %.1f%n", t.residualP50(), t.residualP95());
    }

    private static double percentile(double[] values, double quantile) {
        double[] sorted = Arrays.copyOf(values, values.length);
        Arrays.sort(sorted);
        int index = Math.max(0, (int) Math.ceil(quantile * sorted.length) - 1);
        return sorted[index];
    }

    private static double throughput(long operationsPerBatch, double nanosPerCall) {
        if (nanosPerCall <= 0.0) {
            return Double.NaN;
        }
        return operationsPerBatch * 1_000_000_000.0 / nanosPerCall;
    }

    private static int intArg(String[] args, int index, int defaultValue, String label) {
        if (args.length <= index) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(label + " must be an integer", e);
        }
    }
}
