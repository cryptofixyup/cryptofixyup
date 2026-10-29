package com.jev;

import java.util.Objects;

/** Adapts an existing Java batch entry point to the harness contract. */
public final class RunnableBatchAdapter implements BatchWorkload {
    private final Runnable action;
    private final long operations;

    /**
     * @param action one invocation of the existing batch operation
     * @param operations logical operations performed by one batch (zero if unknown)
     */
    public RunnableBatchAdapter(Runnable action, long operations) {
        this.action = Objects.requireNonNull(action, "action");
        if (operations < 0) {
            throw new IllegalArgumentException("operations must be >= 0");
        }
        this.operations = operations;
    }

    @Override
    public void executeBatch() {
        action.run();
    }

    @Override
    public long operationsPerBatch() {
        return operations;
    }
}
