package com.icw.ehf.cui.components.bootstrap.layout.input.support;

import com.icw.ehf.cui.components.bootstrap.layout.input.ContainerPlugin;
import com.icw.ehf.cui.components.bootstrap.layout.input.LabeledContainerComponent;
import com.icw.ehf.cui.core.api.components.base.CuiComponentBase;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("javadoc")
public class MockComponentPlugin extends CuiComponentBase implements ContainerPlugin {

    @Getter
    @Setter
    private boolean prerenderCalled = false;

    @Getter
    @Setter
    private boolean postAddToViewCalled = false;

    @Override
    public void prerender(LabeledContainerComponent parent) {
        prerenderCalled = true;

    }

    @Override
    public void postAddToView(LabeledContainerComponent parent) {
        postAddToViewCalled = true;
    }
}
