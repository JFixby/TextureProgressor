package com.jfixby.tool.texture.progressor.api;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.image.GrayMap;

public interface GrayImagePyramidDecomposer {

    Collection<ImagePyramidlayer> listLayers();

    void deCompose();

    GrayMap getTail();

}
