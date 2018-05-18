package com.center.handler.gl;

import android.content.res.Resources;

public class CandyFilter extends GroupFilter {

    public CandyFilter(Resources resource) {
        super(resource);
    }

    @Override
    protected void initBuffer() {
        super.initBuffer();
        addFilter(new GrayFilter(mRes));
        addFilter(new BaseFuncFilter(mRes,BaseFuncFilter.FILTER_GAUSS));
        addFilter(new BaseFuncFilter(mRes,BaseFuncFilter.FILTER_SOBEL));
    }
}
