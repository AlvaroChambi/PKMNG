package es.developer.achambi.pkmng.idling;

import android.support.test.espresso.IdlingResource;

import es.developer.achambi.pkmng.core.threading.MainExecutor;

public class ExecutorIdlingResource implements IdlingResource {
    boolean isIdle;
    private ResourceCallback resourceCallback;
    private MainExecutor executor;

    public ExecutorIdlingResource(MainExecutor executor) {
        this.executor = executor;
    }

    @Override
    public String getName() {
        return ExecutorIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        isIdle = executor.getActiveCount() == 0 ;
        if(isIdle) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
