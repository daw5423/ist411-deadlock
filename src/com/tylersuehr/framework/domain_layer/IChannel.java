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
 * Defines a unique channel that contains both a unique ID for
 * quick lookup, and a scheduler for scheduling use case executions.
 * 
 * @author Tyler Suehr
 */
public interface IChannel {
    /**
     * Gets the unique channel's ID.
     * @return channel ID
     */
    byte getId();
    
    /**
     * Gets the use case scheduler for this channel.
     * @return {@link IUseCaseScheduler}
     */
    IUseCaseScheduler getScheduler();
}