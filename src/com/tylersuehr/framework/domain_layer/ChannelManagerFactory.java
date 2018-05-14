/*
 * Copyright 2018 Tyler.
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

package com.tylersuehr.framework.domain_layer;

/**
 * Factory to create stock instances of {@link AbstractChannelManager}.
 * This will hide the internal implementations from other objects.
 * 
 * @author Tyler Suehr
 */
public abstract class ChannelManagerFactory {
    private ChannelManagerFactory() {}
    
    /**
     * Provides a channel manager that will always result in
     * a deadlock when relocating channel into another manager.
     * 
     * @return {@link AbstractChanelManager}
     */
    public static AbstractChannelManager newBadThreadSafe() {
        return new RetardedChannelManager();
    }
    
    /**
     * Provides a channel manager that uses sufficient thread-safe
     * code.
     * 
     * @return {@link AbstractChanelManager}
     */
    public static AbstractChannelManager newGoodThreadSafe() {
        return new GoodChannelManager();
    }
    
    
    /**
     * Subclass of {@link AbstractChannelManager} that affords a very
     * bad implementation of thread-safe code. 
     * Will ALWAYS result in a deadlock when method is called at same time
     * in different threads.
     */
    private static final class RetardedChannelManager 
            extends AbstractChannelManager {
        @Override
        public synchronized void relocate(AbstractChannelManager m, IChannel channel) {
            // Do nothing, we have no channels :)
            if (mChannels == null) { return; }
            
            // Tough luck if we don't have the channel
            final IChannel found = mChannels.remove(channel.getId());
            if (found == null) {
                throw new IllegalArgumentException("Not a valid channel!");
            }
            
            // This is what causes the deadlock!
            m.add(found);
        }
    }
    
    /**
     * Subclass of {@link AbstractChannelManager} that affords sufficient
     * implementation of thread-safe code.
     */
    private static final class GoodChannelManager 
            extends AbstractChannelManager {
        private static final Object LOCK = new Object();
        
        @Override
        public void relocate(AbstractChannelManager m, IChannel channel) {
            // Do nothing, we have no channels :)
            if (mChannels == null) { return; }
            
            // Since the intrinsic lock being used is on a static
            // variable, it is class-scoped. 
            //
            // Therefore, all instances of this class will be synchronized
            // on the same intrinsic lock, preventing the former deadlock 
            // from occuring.
            // 
            // Importantly, we also do as LITTLE logic as possible in the 
            // synchronized block for increased efficiency.
            final IChannel found;
            synchronized (LOCK) {
                found = mChannels.remove(channel.getId());
            }
            
            // Tough luck if we don't have the channel.
            if (found == null) {
                throw new IllegalArgumentException("Not a valid channel!");
            }
            
            // The implementation of this call happens to be synchronized,
            // however it will still be thread-safe synchronized or not
            // and will not produce a deadlock.
            m.add(found);
        }
    }
}