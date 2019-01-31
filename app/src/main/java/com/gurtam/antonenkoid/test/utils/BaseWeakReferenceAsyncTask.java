package com.gurtam.antonenkoid.test.utils;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;

/**
 * Async task that contains a weak reference.
 * AsyncTask actions will performed if weak reference will exist in moment of execution.
 *
 * @author antonenkoid
 */
public abstract class BaseWeakReferenceAsyncTask<Reference, Params, Result> extends BaseAsyncTask<Params, Result> {

    private WeakReference<Reference> weakReference;

    public BaseWeakReferenceAsyncTask(Reference reference) {
        this.weakReference = new WeakReference<>(reference);
    }

    @Override
    protected final Result asyncOperation(Params... params) throws Exception {
        Reference reference = weakReference.get();

        if (reference != null) {
            return executeAsyncOperation(params);
        } else {
            return null;
        }
    }

    protected abstract Result executeAsyncOperation(Params... params) throws Exception;

    @Override
    protected final void onSuccess(Result result) {
        Reference reference = weakReference.get();

        if (reference != null) {
            success(result);
        }
    }

    protected abstract void success(Result result);


    @Override
    protected final void onFail(Exception e) {
        Reference reference = weakReference.get();

        if (reference != null) {
            fail(e);
        }
    }

    protected abstract void fail(Exception e);

    /**
     * Returns actual passed reference.
     */
    @NonNull
    protected Reference getReference() {
        return weakReference.get();
    }
}
