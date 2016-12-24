package com.jfixby.tool.texture.progressor.api;

import com.jfixby.scarabei.api.image.GrayMap;

public interface GrayImagePyramidDecomposeSpecs {

    void setInputImage(GrayMap inputImage);

    GrayMap getInputImage();

    void setMaxIterations(int iterations);

    int getMaxIterations();



}
