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

import edu.psu.ist411.framework.data_layer.IRepoCallbacks;

/**
 * Demonstration of mock data source using MVP architecture.
 *
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public interface MockUserRepository {
    void save(MockUser user);
    void delete(MockUser user);
    void findById(String userId, IRepoCallbacks.ISingle<MockUser> callback);
}