package com.jfixby.tool.texture.progressor.api;

import com.jfixby.cmns.api.collections.Collection;

public interface GrayImagePyramidComposer {

    void compose();

    Collection<ImagePyramidlayer> listLayers();

}
