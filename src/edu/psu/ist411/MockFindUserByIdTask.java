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
import edu.psu.ist411.framework.domain_layer.UseCase;

/**
 * Demonstration of mock use case using clean architecture.
 * This example would just find a user using a given user ID.
 *
 * <i>Request: </i> Unique ID of the user.
 * <i>Response: </i> Business model of the user.
 *
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public class MockFindUserByIdTask extends UseCase<String, MockUser> {
    private final MockUserRepository mUserRepo;

    public MockFindUserByIdTask(final MockUserRepository userRepo) {
        mUserRepo = userRepo;
    }

    @Override
    protected void onExecute() {
        // Get the unique user ID from use case request.
        final String userId = getRequest();

        // Lookup the user's ID in the repository.
        mUserRepo.findById(userId, new IRepoCallbacks.ISingle<MockUser>() {
            @Override
            public void onAvailable(MockUser value) {
                // Invoke successful callback.
                pass(value);
            }

            @Override
            public void onNotAvailable(Exception ex) {
                // Invoke failure callback.
                fail(ex);
            }
        });
    }
}