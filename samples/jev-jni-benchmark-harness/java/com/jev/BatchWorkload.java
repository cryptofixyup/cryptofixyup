package com.jev;

/** Contract for one repeatable benchmark batch. */
public interface BatchWorkload {
    /** Execute one batch; implementations should avoid external I/O. */
    void executeBatch();

    /** Number of logical operations performed by one batch. */
    long operationsPerBatch();
}
