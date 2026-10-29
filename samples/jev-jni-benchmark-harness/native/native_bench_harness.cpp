#include <jni.h>

#include <chrono>

namespace {
using Clock = std::chrono::steady_clock;

void throw_null_pointer(JNIEnv* env, const char* message) {
    jclass exception_class = env->FindClass("java/lang/NullPointerException");
    if (exception_class != nullptr) {
        env->ThrowNew(exception_class, message);
        env->DeleteLocalRef(exception_class);
    }
}
}  // namespace

// Java declaration:
//   public native long measureBatchNanos(Object workload);
// The workload must expose a public, no-argument void executeBatch() method.
// The returned value measures that Java method call inside the native region;
// it is not a measurement of the complete Java -> JNI -> Java round trip.
extern "C" JNIEXPORT jlong JNICALL
Java_com_jev_NativeBenchHarness_measureBatchNanos(JNIEnv* env,
                                                   jobject /* self */,
                                                   jobject workload) {
    if (workload == nullptr) {
        throw_null_pointer(env, "workload must not be null");
        return -1;
    }

    jclass workload_class = env->GetObjectClass(workload);
    if (workload_class == nullptr) {
        // JNI has already raised an exception.
        return -1;
    }

    jmethodID execute_batch =
        env->GetMethodID(workload_class, "executeBatch", "()V");
    if (execute_batch == nullptr) {
        // Preserve NoSuchMethodError (or another pending JVM exception).
        env->DeleteLocalRef(workload_class);
        return -1;
    }

    const auto start = Clock::now();
    env->CallVoidMethod(workload, execute_batch);
    const auto end = Clock::now();
    env->DeleteLocalRef(workload_class);

    // If executeBatch threw, leave that exception pending for Java to observe.
    if (env->ExceptionCheck()) {
        return -1;
    }

    return static_cast<jlong>(
        std::chrono::duration_cast<std::chrono::nanoseconds>(end - start)
            .count());
}
