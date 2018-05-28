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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Affords the basic functionality of the channel manager.
 * This code is thread-safe and does NOT deadlock.
 *
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public abstract class AbstractChannelManager {
    /** Stores references to channels for this manager. */
    protected Map<Byte, IChannel> mChannels;

    /**
     * Relocates a channel from this manager into another manager.
     * @param m Other {@link AbstractChannelManager}
     * @param channel {@link IChannel}
     */
    public abstract void relocate(AbstractChannelManager m, IChannel channel);

    /**
     * Gets a channel using the given channel ID.
     * @param channelId ID of the channel to find.
     * @return {@link IChannel}.
     */
    public final synchronized IChannel get(final byte channelId) {
        if (mChannels != null) {
            return mChannels.get(channelId);
        }
        return null;
    }

    /**
     * Gets a channel using the given channel ID, or throws
     * an exception.
     *
     * @param channelId ID of the channel to find.
     * @return {@link IChannel}.
     *
     * @throws NoSuchElementException if channel not found.
     */
    public final synchronized IChannel getOrThrow(final byte channelId) throws NoSuchElementException {
        if (mChannels == null) {
            throw new NoSuchElementException();
        }
        final IChannel found = mChannels.get(channelId);
        if (found == null) {
            throw new NoSuchElementException();
        }
        return found;
    }

    /**
     * Adds a channel to this manager.
     * @param channel {@link IChannel}.
     */
    public final synchronized void add(final IChannel channel) {
        if (mChannels == null) {
            mChannels = new HashMap<>();
        }
        mChannels.put(channel.getId(), channel);
    }

    /**
     * Removes a channel from this manager.
     * @param channel {@link IChannel}.
     * @return Removed {@link IChannel}.
     */
    public final synchronized IChannel remove(final IChannel channel) {
        if (mChannels != null) {
            return mChannels.remove(channel.getId());
        }
        return null;
    }

    /**
     * Removes a channel from this manager using its channel ID.
     * @param channelId ID of the channel to remove.
     * @return Removed {@link IChannel}.
     */
    public final synchronized IChannel remove(final byte channelId) {
        if (mChannels != null) {
            return mChannels.remove(channelId);
        }
        return null;
    }

    /**
     * Convenience method to print each channel out.
     * @param tag Tag to print (i.e. "M1").
     */
    public final void print(final String tag) {
        final Collection<IChannel> channels = mChannels.values();
        channels.forEach(c -> {
            System.out.println(tag + ": " + c);
        });
    }
}