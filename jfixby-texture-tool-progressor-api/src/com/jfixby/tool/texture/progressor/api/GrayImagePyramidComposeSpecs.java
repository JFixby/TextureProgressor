package com.jfixby.tool.texture.progressor.api;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.image.GrayMap;

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
