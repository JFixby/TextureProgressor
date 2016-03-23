package com.jfixby.tool.texture.progressor.test;

import java.io.IOException;

import com.jfixby.cmns.adopted.gdx.json.GdxJson;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.desktop.ImageAWT;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.image.ColorMap;
import com.jfixby.cmns.api.image.GrayMap;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopAssembler;
import com.jfixby.tool.texture.progressor.api.GrayImagePyramidDecomposeSpecs;
import com.jfixby.tool.texture.progressor.api.GrayImagePyramidDecomposer;
import com.jfixby.tool.texture.progressor.api.ImagePyramidlayer;
import com.jfixby.tool.texture.progressor.api.TextureProgressor;
import com.jfixby.tool.texture.progressor.api.srlz.GrayImagePyramidComposition;
import com.jfixby.tool.texture.progressor.api.srlz.GrayImagePyramidCompositionlayer;
import com.jfixby.tool.texture.progressor.red.RedTextureProgressor;

public class TestDecomposePyramid {

    public static void main(String[] args) throws IOException {
	DesktopAssembler.setup();
	TextureProgressor.installComponent(new RedTextureProgressor());
	Json.installComponent(new GdxJson());

	File inputPNG = LocalFileSystem.ApplicationHome().child("input").child("1.png");
	File output = LocalFileSystem.ApplicationHome().child("output").child("decomposed");
	output.makeFolder();
	output.clearFolder();

	ColorMap inputImage = ImageAWT.readAWTColorMap(inputPNG);

	GrayImagePyramidDecomposeSpecs settings = TextureProgressor.newGrayImagePyramidDeComposeSpecs();
	settings.setInputImage(inputImage.getRed());
	settings.setMaxIterations(16);

	GrayImagePyramidDecomposer pyramid = TextureProgressor.newPyramidDecomposer(settings);
	pyramid.deCompose();

	String original_name = inputPNG.nameWithoutExtension();
	Collection<ImagePyramidlayer> layes = pyramid.listLayers();

	GrayImagePyramidComposition composition = new GrayImagePyramidComposition();
	composition.original_width = inputImage.getWidth();
	composition.original_height = inputImage.getHeight();
	composition.size = layes.size();
	for (int i = 0; i < layes.size(); i++) {
	    ImagePyramidlayer layer = layes.getElementAt(i);
	    // GrayMap reduced = layer.getReduced();
	    GrayMap difference = layer.getDifference();
	    int k = 0 + i;
	    // {
	    // String child_name = original_name + "-" + k + "-reduced.png";
	    // File output_file = output.child(child_name);
	    // ImageAWT.writeToFile(reduced, output_file, "png");
	    //
	    // }
	    {
		String child_name = original_name + "-" + k + "-diff.png";

		GrayImagePyramidCompositionlayer srlz_layer = new GrayImagePyramidCompositionlayer();
		srlz_layer.file_name = child_name;
		srlz_layer.level = i;
		composition.layers.add(srlz_layer);

		File output_file = output.child(child_name);
		write(output_file, difference);
	    }

	}
	GrayMap tail = pyramid.getTail();
	{
	    String child_name = original_name + "-" + "tail.png";

	    GrayImagePyramidCompositionlayer srlz_layer = new GrayImagePyramidCompositionlayer();
	    srlz_layer.file_name = child_name;
	    srlz_layer.level = -1;
	    composition.layers.add(srlz_layer);

	    File output_file = output.child(child_name);
	    write(output_file, tail);
	}

	{
	    String child_name = original_name + GrayImagePyramidComposition.FILE_EXTENSION;

	    String data = Json.serializeToString(composition);
	    File output_file = output.child(child_name);
	    output_file.writeString(data);
	    L.d("writing", output_file);
	}

    }

    private static void write(File output_file, GrayMap image) throws IOException {
	L.d("writing", output_file);
	ImageAWT.writeToFile(image, output_file, "png");
    }

}
