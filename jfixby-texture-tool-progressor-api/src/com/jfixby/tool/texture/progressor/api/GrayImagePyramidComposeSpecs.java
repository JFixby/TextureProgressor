package com.jfixby.tool.texture.progressor.api;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.image.GrayMap;

public interface GrayImagePyramidComposeSpecs {

    void setOriginalWidth(int original_width);

    void setOriginalHeight(int original_height);

    void setSize(int size);

    int addLayer(GrayMap image);

    int getOriginalWidth();

    int getOriginalHeight();

    int getSize();

    Collection<GrayMap> listLayers();

    GrayMap getTail();

    void setTail(GrayMap image);

}
