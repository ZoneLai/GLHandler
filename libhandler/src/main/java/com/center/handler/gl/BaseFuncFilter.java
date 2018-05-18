package com.center.handler.gl;

import android.content.res.Resources;

/**
 * BaseFuncFilter 基础功能滤镜
 */
class BaseFuncFilter extends BaseFilter {

    static final String FILTER_SOBEL="shader/func/sobel.frag";
    static final String FILTER_SOBEL_REVERSE="shader/func/sobel2.frag";
    static final String FILTER_GAUSS="shader/func/gauss.frag";

    BaseFuncFilter(Resources resource, String fragment) {
        super(resource, "shader/base.vert", fragment);
        shaderNeedTextureSize(true);
    }
}
