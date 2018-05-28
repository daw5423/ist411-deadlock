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

/**
 * Single executable task that takes a request and produces a response,
 * which allows for total abstraction of all business logic in the program.
 *
 * <b>Basic Idea:</b>
 * Use case is given a request to process, it performs business logic (any
 * persistence, networking, and such), and then it indicates a successful or
 * failure response.
 *
 * Every use case ALWAYS completes execution and should never throw an
 * exception. This uses the Chain of Responsibility design pattern to
 * handle successful and failed completions by using {@link Callback}.
 *
 * ONE of these two things happen when a use case completes:
 * (1) Invokes success callback and provides a response.
 * (2) Invokes failed callback and provides reason why.
 *
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public abstract class UseCase<T, V> {
    /** Stores reference to callback. */
    private Callback<V> mCallback;
    /** Stores reference to request. */
    private T mRequest;

    public UseCase() {}

    /** Assumed always called on new thread. */
    protected abstract void onExecute();

    /**
     * Convenience method for passing the use case.
     * @param response UseCase response.
     */
    protected final void pass(final V response) {
        System.out.println(getClass().getSimpleName()
                .toUpperCase() + ": Success!");
        mCallback.onSuccess(response);
    }

    /**
     * Convenience method for failing the use case.
     * @param ex Reason why it failed.
     */
    protected final void fail(final Exception ex) {
        System.out.println(getClass().getSimpleName()
                .toUpperCase() + ": Failed!");
        mCallback.onFailure(ex);
    }

    public final void setCallback(final Callback<V> callback) {
        mCallback = callback;
    }

    public final void setRequest(final T request) {
        mRequest = request;
    }

    public final T getRequest() {
        return mRequest;
    }

    /**
     * Defines completion events for the use case.
     * @param <V> UseCase response.
     */
    public interface Callback<V> {
        void onSuccess(V response);
        void onFailure(Exception ex);
    }
}