package com.jfixby.tool.texture.progressor.test;

import java.io.IOException;

import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.desktop.ImageAWT;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.image.ArrayGrayMap;
import com.jfixby.cmns.api.image.GrayMap;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;
import com.jfixby.tool.texture.progressor.api.GrayImagePyramidComposeSpecs;
import com.jfixby.tool.texture.progressor.api.GrayImagePyramidComposer;
import com.jfixby.tool.texture.progressor.api.ImagePyramidlayer;
import com.jfixby.tool.texture.progressor.api.TextureProgressor;
import com.jfixby.tool.texture.progressor.api.srlz.GrayImagePyramidComposition;
import com.jfixby.tool.texture.progressor.api.srlz.GrayImagePyramidCompositionlayer;
import com.jfixby.tool.texture.progressor.red.RedTextureProgressor;

public class TestComposePyramid {

    public static void main(String[] args) throws IOException {
	DesktopSetup.deploy();
	TextureProgressor.installComponent(new RedTextureProgressor());
	Json.installComponent(new RedJson());

	File input = LocalFileSystem.ApplicationHome().child("output").child("decomposed");
	File output = LocalFileSystem.ApplicationHome().child("output").child("composed");
	output.makeFolder();
	output.clearFolder();

	File pyramid_file = input.child("1.r3-pyramid");
	String data = pyramid_file.readToString();
	GrayImagePyramidComposition composition = Json.deserializeFromString(GrayImagePyramidComposition.class, data);

	GrayImagePyramidComposeSpecs settings = TextureProgressor.newGrayImagePyramidComposeSpecs();
	settings.setOriginalWidth(composition.original_width);
	settings.setOriginalHeight(composition.original_height);
	settings.setSize(composition.size);

	for (int i = 0; i < composition.layers.size(); i++) {
	    GrayImagePyramidCompositionlayer layer = composition.layers.get(i);
	    String layer_file_name = layer.file_name;
	    File image_file = input.child(layer_file_name);
	    ArrayGrayMap image = ImageAWT.readAWTGrayMap(image_file);
	    if (layer.level == GrayImagePyramidCompositionlayer.TAIL) {
		settings.setTail(image);
		continue;
	    }
	    int index = settings.addLayer(image);
	    if (index != layer.level) {
		Err.reportError("Wrong layers order: " + index + " != " + layer.level);
	    }

	}

	GrayImagePyramidComposer pyramid = TextureProgressor.newPyramidComposer(settings);
	pyramid.compose();

	String original_name = pyramid_file.nameWithoutExtension();

	Collection<ImagePyramidlayer> layes = pyramid.listLayers();

	for (int i = 0; i < layes.size(); i++) {
	    ImagePyramidlayer layer = layes.getElementAt(i);
	    {
		String child_name = original_name + "-" + i + "-.png";
		File output_file = output.child(child_name);
		GrayMap restored = layer.getBase();
		write(output_file, restored);
	    }

	}

    }

    private static void write(File output_file, GrayMap image) throws IOException {
	L.d("writing", output_file);
	ImageAWT.writeToFile(image, output_file, "png");
    }

}
