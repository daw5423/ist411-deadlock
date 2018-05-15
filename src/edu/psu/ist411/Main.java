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

package edu.psu.ist411;

import edu.psu.ist411.framework.domain_layer.IChannel;
import edu.psu.ist411.framework.domain_layer.ChannelManagerFactory;
import edu.psu.ist411.framework.ui_layer.AbstractSchedulerPresenter;

/**
 * Demonstrates a deadlock.
 * I wanted to replicate an enterprise real-world situation where a deadlock
 * can occur when using enterprise-style framework APIs.
 * 
 * The framework being used here is a quick sample I threw together that
 * uses both the Clean and MVP (model-view-presenter) architecture. This 
 * type of architectural style is used predominantly by big companies like 
 * Google.
 * 
 * For more information and details visit: https://github.com/tylersuehr7
 * 
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public class Main {
    /**
     * Basically, swapping channels with different channel managers while
     * running in different threads is what causes our deadlock to occur :D
     * 
     * Here's a detailed overview of what'll happen:
     * (1) Instantiate and setup the presenters we'll use to manipulate
     *     our channels - used for scheduling use case executions.
     *   
     * (2) Instantiate our 1st thread which will create a channel, add it 
     *     to our 1st presenter, and then swap it to the 2nd presenter.
     *   
     * (3) Instantiate our 2nd thread which will create a channel, add it
     *     to our 2nd presenter, and then swap it to the 1st presenter.
     *   
     * (4) Start the threads so that our code can run in-parallel :)
     *   
     *     Deadlock will happen here because the 1st channel manager acquires
     *     a lock, takes a while to process, and acquires another lock which
     *     will have already been acquired by the 2nd channel manager that is
     *     waiting for the lock acquired by the 1st presenter. 
     * 
     * @param args 
     */
    public static void main(String[] args) {
        // Create the 1st presenter we'll use to manipulate channels... 
        // using the BAD channel manager implementation!
        final AbstractSchedulerPresenter p1 = MockIoC.mockPresenter();
        p1.setChannelManager(ChannelManagerFactory.newBadThreadSafe());
        
        // Create the 2nd presenter we'll use to manipulate channels... 
        // using the BAD channel manager implementation!
        final AbstractSchedulerPresenter p2 = MockIoC.mockPresenter();
        p2.setChannelManager(ChannelManagerFactory.newBadThreadSafe());
        
        
        // 1st thread we'll use add a channel to the 1st presenter then 
        // switch it to the 2nd presenter.
        final Thread t1 = new Thread(() -> {
            // Create the channel we'll add to our 1st presenter
            final IChannel channel = MockIoC.channel((byte)2);
            
            // Add the channel to our 1st presenter
            p1.getChannelManager().add(channel);
            
            // Switch the channel from our 1st presenter to our 2nd
            p1.getChannelManager().relocate(
                    p2.getChannelManager(), channel);
            
            p1.getChannelManager().print("Presenter 1");
        });
        
        
        // 2nd thread we'll use add a channel to the 2nd presenter then 
        // switch it to the 1st presenter.
        final Thread t2 = new Thread(() -> {
            // Create the channel we'll add to our 2nd presenter
            final IChannel channel = MockIoC.channel((byte)5);
            
            // Add the channel to our 2nd presenter
            p2.getChannelManager().add(channel);
            
            // Switch the channel from our 2nd presenter to our 1st
            p2.getChannelManager().relocate(
                    p1.getChannelManager(), channel);
            
            p2.getChannelManager().print("Presenter 2");
        });
        
        // Run our created threads
        t1.start();
        t2.start();
    }
}