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
 * Basic implementation of {@link IChannel} with immutable properties.
 *
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public class ChannelImpl implements IChannel {
    /** Stores reference to scheduler. */
    private final IUseCaseScheduler mScheduler;
    /** Stores channel ID. */
    private final byte mId;

    public ChannelImpl(final byte id, final IUseCaseScheduler scheduler) {
        mId = id;
        mScheduler = scheduler;
    }

    @Override
    public final String toString() {
        return "Channel (" + mId + ")";
    }

    @Override
    public byte getId() {
        return mId;
    }

    @Override
    public IUseCaseScheduler getScheduler() {
        return mScheduler;
    }
}