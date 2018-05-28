/*
 * Copyright 2018 Group 5.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.psu.ist411.framework.domain_layer;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Basic implementation of {@link IUseCaseScheduler} that uses a thread-pool
 * executor {@link ThreadPoolExecutor} for scheduling use case executions.
 *
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public class UseCaseSchedulerImpl implements IUseCaseScheduler {
    /** Stores reference to this scheduler's thread-pool executor. */
    private ThreadPoolExecutor mExecutor;

    @Override
    public <T, V> void execute(final UseCase<T, V> useCase, T request, UseCase.Callback<V> callback) {
        // Set the properties of the use case.
        useCase.setRequest(request);
        useCase.setCallback(callback);

        // Use the executor to execute the use case.
        getExecutor().execute(() -> {
            useCase.onExecute();
        });
    }

    @Override
    public List<Runnable> stopExecution() {
        // The executor could be null. It's handled by simply
        // returning null.
        if (mExecutor != null) {
            final List<Runnable> tasks = mExecutor.shutdownNow();
            mExecutor = null;
            return tasks;
        }
        return null;
    }

    /**
     * Lazily load the {@link #mExecutor}.
     * @return {@link ThreadPoolExecutor}.
     */
    private ThreadPoolExecutor getExecutor() {
        if (mExecutor == null) {
            // Setup using standard properties.
            final int SIZE = 2;
            final int MAX = 4;
            final int TIMEOUT = 30;
            mExecutor = new ThreadPoolExecutor(SIZE, MAX, TIMEOUT,
                    TimeUnit.SECONDS, new ArrayBlockingQueue<>(MAX));
        }
        return mExecutor;
    }
}