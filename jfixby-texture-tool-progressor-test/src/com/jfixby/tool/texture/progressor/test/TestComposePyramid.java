
package com.jfixby.tool.texture.progressor.test;

import java.io.IOException;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.desktop.ImageAWT;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.image.ArrayGrayMap;
import com.jfixby.cmns.api.image.GrayMap;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.tool.texture.progressor.api.GrayImagePyramidComposeSpecs;
import com.jfixby.tool.texture.progressor.api.GrayImagePyramidComposer;
import com.jfixby.tool.texture.progressor.api.ImagePyramidlayer;
import com.jfixby.tool.texture.progressor.api.TextureProgressor;
import com.jfixby.tool.texture.progressor.api.srlz.GrayImagePyramidComposition;
import com.jfixby.tool.texture.progressor.api.srlz.GrayImagePyramidCompositionlayer;
import com.jfixby.tool.texture.progressor.red.RedTextureProgressor;

public class TestComposePyramid {

	public static void main (final String[] args) throws IOException {
		DesktopSetup.deploy();
		TextureProgressor.installComponent(new RedTextureProgressor());
		Json.installComponent("com.jfixby.cmns.adopted.gdx.json.RedJson");

		final File input = LocalFileSystem.ApplicationHome().child("output").child("decomposed");
		final File output = LocalFileSystem.ApplicationHome().child("output").child("composed");
		output.makeFolder();
		output.clearFolder();

		final File pyramid_file = input.child("1.r3-pyramid");
		final String data = pyramid_file.readToString();
		final GrayImagePyramidComposition composition = Json.deserializeFromString(GrayImagePyramidComposition.class, data);

		final GrayImagePyramidComposeSpecs settings = TextureProgressor.newGrayImagePyramidComposeSpecs();
		settings.setOriginalWidth(composition.original_width);
		settings.setOriginalHeight(composition.original_height);
		settings.setSize(composition.size);

		for (int i = 0; i < composition.layers.size(); i++) {
			final GrayImagePyramidCompositionlayer layer = composition.layers.get(i);
			final String layer_file_name = layer.file_name;
			final File image_file = input.child(layer_file_name);
			final ArrayGrayMap image = ImageAWT.readAWTGrayMap(image_file);
			if (layer.level == GrayImagePyramidCompositionlayer.TAIL) {
				settings.setTail(image);
				continue;
			}
			final int index = settings.addLayer(image);
			if (index != layer.level) {
				Err.reportError("Wrong layers order: " + index + " != " + layer.level);
			}

		}

		final GrayImagePyramidComposer pyramid = TextureProgressor.newPyramidComposer(settings);
		pyramid.compose();

		final String original_name = pyramid_file.nameWithoutExtension();

		final Collection<ImagePyramidlayer> layes = pyramid.listLayers();

		for (int i = 0; i < layes.size(); i++) {
			final ImagePyramidlayer layer = layes.getElementAt(i);
			{
				final String child_name = original_name + "-" + i + "-.png";
				final File output_file = output.child(child_name);
				final GrayMap restored = layer.getBase();
				write(output_file, restored);
			}

		}

	}

	private static void write (final File output_file, final GrayMap image) throws IOException {
		L.d("writing", output_file);
		ImageAWT.writeToFile(image, output_file, "png");
	}

}
