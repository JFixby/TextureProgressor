package com.jfixby.tool.texture.progressor.api;

import com.jfixby.cmns.api.ComponentInstaller;

public class TextureProgressor {

    static private ComponentInstaller<TextureProgressorComponent> componentInstaller = new ComponentInstaller<TextureProgressorComponent>(
	    "TextureProgressor");

    public static final void installComponent(TextureProgressorComponent component_to_install) {
	componentInstaller.installComponent(component_to_install);
    }

    public static final TextureProgressorComponent invoke() {
	return componentInstaller.invokeComponent();
    }

    public static final TextureProgressorComponent component() {
	return componentInstaller.getComponent();
    }

    public static GrayImagePyramidDecomposeSpecs newGrayImagePyramidDeComposeSpecs() {
	return invoke().newImagePyramidSpecs();
    }

    public static GrayImagePyramidDecomposer newPyramidDecomposer(GrayImagePyramidDecomposeSpecs settings) {
	return invoke().newPyramid(settings);
    }

    public static GrayImagePyramidComposeSpecs newGrayImagePyramidComposeSpecs() {
	return invoke().newGrayImagePyramidComposeSpecs();
    }

    public static GrayImagePyramidComposer newPyramidComposer(GrayImagePyramidComposeSpecs settings) {
	return invoke().newPyramidComposer(settings);
    }
}
