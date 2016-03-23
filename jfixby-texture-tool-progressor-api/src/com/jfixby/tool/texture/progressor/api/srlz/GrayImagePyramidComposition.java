package com.jfixby.tool.texture.progressor.api.srlz;

import java.util.ArrayList;

public class GrayImagePyramidComposition {

    public static final String FILE_EXTENSION = ".r3-pyramid";

    public int original_width;
    public int original_height;
    public int size;
    public ArrayList<GrayImagePyramidCompositionlayer> layers = new ArrayList<GrayImagePyramidCompositionlayer>();

}
