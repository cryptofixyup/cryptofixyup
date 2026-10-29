# JNI Benchmark Harness Wrapper

A small C++17/JNI bridge and Java runner for measuring a Java batch entry point through a native wrapper. It reports repeatable latency distributions instead of calling a single elapsed-time value “throughput.”

**Current scope:** this repository did not include the JEV source repository or its build/classpath, so the checked-in runner uses a deterministic synthetic CPU workload. The native bridge and adapter contract are complete as a standalone sample; a result from the actual JEV workload has **not** been measured here.

## What it measures

For each sample, the runner executes the same batch directly from Java and through the JNI wrapper, alternating execution order between samples. It reports p50 and p95 nanoseconds per call for:

- `direct_java_wall`: wall time around direct Java method calls.
- `jni_wrapper_wall`: wall time around calls into the native wrapper.
- `native_timed_region`: time measured inside C++ around `executeBatch()`.
- `wall_minus_native`: the non-negative difference between JNI wall time and the native timed region.

`wall_minus_native` is a **residual estimate**, not pure JNI transition cost. It also reflects method lookup, timer costs, scheduling, JIT warm-up, and measurement noise. Compare distributions under the same machine/JDK/build conditions; do not treat a single run as a performance claim.

The no-op baseline helps show the wrapper's floor. The synthetic workload is only a smoke-test fixture and must not be represented as a JEV result.

## Build and run (Linux)

Requirements: JDK 17 or 21, a C++17 compiler, CMake 3.20+, and the matching JDK native headers. Set `JAVA_HOME` to the JDK used by `javac`.

```bash
cmake -S . -B build/native -DCMAKE_BUILD_TYPE=Release
cmake --build build/native --parallel 2
mkdir -p build/classes
find java -name '*.java' -print0 | xargs -0 javac -Xlint:all -Werror -d build/classes
java -Djava.library.path=build/native -cp build/classes com.jev.BenchmarkMain
```

Optional arguments are `operations-per-batch calls-per-sample sample-count`, for example:

```bash
java -Djava.library.path=build/native -cp build/classes com.jev.BenchmarkMain 1000 100 30
```

A GitHub Actions workflow builds the native library, compiles the Java sources, and runs a small smoke benchmark on pushes and pull requests that touch this sample.

## Hook it to an existing JEV entry point

The native side looks for a public, no-argument method with this exact contract:

```java
public void executeBatch()
```

If the JEV class already has that method, pass its instance to `measureBatchNanos`. For the supplied Java runner, adapt the real operation to `BatchWorkload` with `RunnableBatchAdapter`:

```java
BatchWorkload jevBatch = new RunnableBatchAdapter(
    () -> existingJevRunner.executeBatch(),
    operationsPerBatch
);
NativeBenchHarness harness = new NativeBenchHarness();
long nativeRegionNanos = harness.measureBatchNanos(jevBatch);
```

Replace `existingJevRunner` and `operationsPerBatch` with the real JEV object and known batch size. Keep the same operation and input shape for direct-Java and JNI comparisons. If the real entry point has arguments, checked exceptions, or requires setup/teardown, implement a small adapter that preserves the intended batch semantics rather than timing setup work accidentally.

## Implementation details

- Uses `std::chrono::steady_clock` for the native interval.
- Resolves `executeBatch()V` before starting the native timer, so method lookup is outside `native_timed_region` but still inside `jni_wrapper_wall`.
- Checks null workloads and preserves JVM exceptions from method lookup or workload execution.
- Uses repeated samples, warm-up calls, p50/p95, and alternating measurement order.
- Does not attempt to disable JVM safepoints or claim that safepoint synchronization is the cause of any observed jitter. Use a profiler/JFR and repeat runs before attributing variance.

## Limits

This is a measurement harness, not a JMH replacement and not proof of a JEV bottleneck. It does not yet include the actual JEV class, a JEV-specific adapter, benchmark fixtures, or measured JEV output. Those require the target source/build and a defined benchmark invocation. No performance improvement is claimed by this sample alone.
