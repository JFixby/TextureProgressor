package com.jfixby.tool.texture.progressor.api;

public interface TextureProgressorComponent {

    GrayImagePyramidDecomposeSpecs newImagePyramidSpecs();

    GrayImagePyramidDecomposer newPyramid(GrayImagePyramidDecomposeSpecs settings);

    GrayImagePyramidComposeSpecs newGrayImagePyramidComposeSpecs();

    GrayImagePyramidComposer newPyramidComposer(GrayImagePyramidComposeSpecs settings);
}
