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

import javax.swing.JFrame;

/**
 * Demonstration of mock view using MVP architecture.
 *
 * @author Tyler Suehr
 * @author Win Ton
 * @author Steven Weber
 * @author David Wong
 */
public class MockViewImpl extends JFrame implements MockContract.IMockView {
    private final MockContract.IMockPresenter mPresenter;

    /** Dependency inject the presenter. */
    public MockViewImpl(final MockContract.IMockPresenter presenter) {
        super("Mock Frame View");
        mPresenter = presenter;
        mPresenter.attach(this);
        // TODO call presenter load mock user wherever necessary.
        // TODO remember to detach wherever view only if needed.
    }

    @Override
    public void onMockUserReady(MockUser user) {
        // TODO load user in some container/profile view.
    }

    @Override
    public void onShowMsg(String msg) {
        // TODO show some dialog or something.
    }
}