package com.jfixby.tool.texture.progressor.api;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.image.GrayMap;

public interface GrayImagePyramidDecomposer {

    Collection<ImagePyramidlayer> listLayers();

    void deCompose();

    GrayMap getTail();

}
