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

import java.util.List;

/**
 * Defines the scheduler that will schedule use case executions.
 * @author Tyler Suehr
 */
public interface IUseCaseScheduler {
    /**
     * Schedules execution of use case, immediately if possible.
     * 
     * @param <T> UseCase request
     * @param <V> UseCase response
     * 
     * @param useCase {@link UseCase} to execute
     * @param request Request of the use case
     * @param callback {@link UseCase.Callback}
     */
    <T,V> void execute(UseCase<T,V> useCase, T request, UseCase.Callback<V> callback);
    
    /**
     * Attempts to stop execution of all active and pending use cases.
     * @return List of {@link Runnable}
     */
    List<Runnable> stopExecution();
}