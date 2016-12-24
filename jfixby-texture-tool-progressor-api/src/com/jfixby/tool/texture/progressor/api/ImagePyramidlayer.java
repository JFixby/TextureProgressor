package com.jfixby.tool.texture.progressor.api;

import com.jfixby.scarabei.api.image.GrayMap;

public interface ImagePyramidlayer {

    GrayMap getReduced();

    GrayMap getDifference();

    GrayMap getBase();

}
