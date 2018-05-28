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

import edu.psu.ist411.framework.domain_layer.ChannelImpl;
import edu.psu.ist411.framework.domain_layer.IChannel;
import edu.psu.ist411.framework.domain_layer.IUseCaseScheduler;
import edu.psu.ist411.framework.domain_layer.UseCaseSchedulerImpl;
import edu.psu.ist411.framework.ui_layer.AbstractSchedulerPresenter;

/**
 * Mock Inversion of Control object to afford concrete dependencies.
 * This is solely for demonstration and does not provide legit
 * dependencies and therefore would not actually run as expected.
 *
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public final class MockIoC {
    private MockIoC() {}

    static AbstractSchedulerPresenter mockPresenter() {
        return new MockPresenterImpl(mockFindUserByIdTask());
    }

    static MockFindUserByIdTask mockFindUserByIdTask() {
        return new MockFindUserByIdTask(null);
    }

    static IChannel channel(byte channelId) {
        return new ChannelImpl(channelId, useCaseScheduler());
    }

    static IUseCaseScheduler useCaseScheduler() {
        return new UseCaseSchedulerImpl();
    }
}