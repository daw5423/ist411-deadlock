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

package edu.psu.ist411.framework.ui_layer;

import edu.psu.ist411.framework.domain_layer.AbstractChannelManager;
import edu.psu.ist411.framework.domain_layer.ChannelImpl;
import edu.psu.ist411.framework.domain_layer.ChannelManagerFactory;
import edu.psu.ist411.framework.domain_layer.IChannel;
import edu.psu.ist411.framework.domain_layer.IUseCaseScheduler;
import edu.psu.ist411.framework.domain_layer.UseCase;
import edu.psu.ist411.framework.domain_layer.UseCaseSchedulerImpl;

/**
 * Implementation of {@link IPresenter} that allows use case scheduling.
 * The purpose of this class is to provide the functionality to schedule
 * the execution of use cases on different channels.
 *
 * Different channels may be needed for different UI features. There might
 * be a shared channel used by different presenters for scheduling use cases
 * that may need to be stopped while other particular channels can still be
 * running and finishing their respective tasks.
 *
 * Constructing this object creates a default channel per instance for
 * use case scheduling with the ID of {@link #DEFAULT_CHANEL}.
 *
 * Note:
 * (1) Abstract to prevent direct instantiation.
 * (2) Parent of all presenters in this project.
 *
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public abstract class AbstractSchedulerPresenter<T extends IView> implements IPresenter<T> {
    /** Constant for default channel ID. */
    private static final byte DEFAULT_CHANNEL = (byte) 0;
    /** Stores reference to channel manager for this presenter. */
    private AbstractChannelManager mChannelManager;
    /** Stores reference to the view for this presenter. */
    private T mView;

    /** Create with default channel manager. */
    public AbstractSchedulerPresenter() {
        mChannelManager = ChannelManagerFactory.newGoodThreadSafe();
        setupDefaultChannel();
    }

    /** Dependency inject channel manager. */
    public AbstractSchedulerPresenter(final AbstractChannelManager manager) {
        mChannelManager = manager;
        setupDefaultChannel();
    }

    @Override
    public void attach(T view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public T getView() {
        return mView;
    }

    /**
     * Gets this presenter's channel manager.
     * @return {@link AbstractChannelManager}.
     */
    public AbstractChannelManager getChannelManager() {
        return mChannelManager;
    }

    /**
     * Sets the channel manager on this presenter.
     * @param manager {@link AbstractChannelManager}.
     */
    public void setChannelManager(final AbstractChannelManager manager) {
        mChannelManager = manager;
        setupDefaultChannel();
    }

    /**
     * Schedules execution of a given use case on the default channel.
     *
     * @param <V> UseCase request.
     * @param <K> UseCase response.
     *
     * @param useCase {@link UseCase} to schedule.
     * @param request Request of the UseCase.
     * @param callback {@link UseCase.Callback}.
     */
    protected final <V, K> void schedule(UseCase<V, K> useCase, V request, UseCase.Callback<K> callback) {
        final IChannel channel = mChannelManager.get(DEFAULT_CHANNEL);
        final IUseCaseScheduler scheduler = channel.getScheduler();
        scheduler.execute(useCase, request, new NullCheckWrapper<>(callback));
    }

    /**
     * Schedules execution of a given use case on a given channel.
     *
     * @param <V> UseCase request.
     * @param <K> UseCase response.
     *
     * @param channelId ID of the channel to use.
     * @param useCase {@link UseCase} to schedule.
     * @param request Request of the UseCase.
     * @param callback {@link UseCase.Callback}.
     *
     * @throws NullPointerException if channel doesn't exist.
     */
    protected final <V, K> void scheduleOn(byte channelId, UseCase<V, K> useCase, V request, UseCase.Callback<K> callback) {
        final IChannel channel = mChannelManager.get(channelId);
        if (channel == null) {
            throw new NullPointerException("Channel does not exist! " + channelId);
        }
        final IUseCaseScheduler scheduler = channel.getScheduler();
        scheduler.execute(useCase, request, new NullCheckWrapper<>(callback));
    }

    /**
     * Schedules execution of a given use case on a given channel.
     * Will create a new channel if the given channel ID does not
     * exist.
     *
     * @param <V> UseCase request.
     * @param <K> UseCase response.
     *
     * @param channelId ID of the channel to use.
     * @param useCase {@link UseCase} to schedule.
     * @param request Request of the UseCase.
     * @param callback {@link UseCase.Callback}.
     */
    protected final <V, K> void scheduleOrCreateOn(byte channelId, UseCase<V, K> useCase, V request, UseCase.Callback<K> callback) {
        IChannel channel = mChannelManager.get(channelId);
        if (channel == null) {
            channel = new ChannelImpl(
                    channelId, new UseCaseSchedulerImpl());
            mChannelManager.add(channel);
        }
        final IUseCaseScheduler scheduler = channel.getScheduler();
        scheduler.execute(useCase, request, new NullCheckWrapper<>(callback));
    }

    /**
     * Create a default channel for use case execution if
     * it doesn't already exist.
     */
    private void setupDefaultChannel() {
        if (mChannelManager.get(DEFAULT_CHANNEL) == null) {
            // Use the default framework implementations.
            mChannelManager.add(new ChannelImpl(
                    DEFAULT_CHANNEL, new UseCaseSchedulerImpl()));
        }
    }

    /**
     * Nested inner-class that prevents {@link NullPointerException} from
     * being thrown when a use case invokes a callback after a view has
     * been detached from its presenter.
     *
     * Note: Safe nested inner-class... no memory leaks are caused by this.
     */
    private final class NullCheckWrapper<V> implements UseCase.Callback<V> {
        private final UseCase.Callback<V> callback;

        NullCheckWrapper(final UseCase.Callback<V> callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(V response) {
            if (mView != null) { callback.onSuccess(response); }
        }

        @Override
        public void onFailure(Exception ex) {
            if (mView != null) { callback.onFailure(ex); }
        }
    }
}