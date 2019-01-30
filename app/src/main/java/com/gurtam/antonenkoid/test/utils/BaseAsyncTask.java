package com.gurtam.antonenkoid.test.utils;

import android.os.AsyncTask;
import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

/**
 * {@link AsyncTask}  wrapper. Contains success and failure callbacks;
 *
 * @author antonenkoid
 */
public abstract class BaseAsyncTask<Params, Result> extends AsyncTask<Params, Void, Result> {

    private Exception exception = null;

    /**
     * Async operation runs here;
     *
     * @param params params of operation
     * @return result
     */
    @WorkerThread
    protected abstract Result asyncOperation(Params... params) throws Exception;

    /**
     * Operation success callback.
     *
     * @param result result of operation
     */
    @MainThread
    protected abstract void onSuccess(Result result);

    /**
     * Operation failure callback.
     *
     * @param e exception
     */
    @MainThread
    protected abstract void onFail(Exception e);

    @SafeVarargs
    @Override
    protected final Result doInBackground(Params... params) {
        Result result = null;
        try {
            result = asyncOperation(params);
        }
        catch (Exception e) {
            exception = e;
        }
        return result;
    }

    @Override
    @CallSuper
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);

        if (!hasException()) {
            onSuccess(result);
        } else {
            onFail(exception);
        }
    }

    private boolean hasException() {
        return exception != null;
    }
}


