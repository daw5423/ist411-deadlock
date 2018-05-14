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

package com.tylersuehr;

import com.tylersuehr.MockContract.IMockPresenter;
import com.tylersuehr.MockContract.IMockView;
import com.tylersuehr.framework.domain_layer.UseCase;
import com.tylersuehr.framework.ui_layer.AbstractSchedulerPresenter;

/**
 * Demonstration of mock presenter using MVP architecture.
 * @author Tyler Suehr
 */
public class MockPresenterImpl
        extends AbstractSchedulerPresenter<IMockView>
        implements IMockPresenter {
    private final MockFindUserByIdTask mockFindUserByIdTask;
    
    
    /* Dependency inject the business logic tasks */
    public MockPresenterImpl(final MockFindUserByIdTask task1) {
        this.mockFindUserByIdTask = task1;
    }
    
    @Override
    public void loadMockUser(String userId) {
        // Schedule the execution of the business logic
        schedule(mockFindUserByIdTask, userId, new UseCase.Callback<MockUser>() {
            @Override
            public void onSuccess(MockUser foundUser) {
                // Update the view with data
                getView().onMockUserReady(foundUser);
            }

            @Override
            public void onFailure(Exception ex) {
                // Alert the user of the failure in a pretty way
                getView().onShowMsg(ex.getLocalizedMessage());
            }            
        });
    }    
}