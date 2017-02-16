
package com.jfixby.tool.texture.progressor.test;

import java.io.IOException;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.desktop.ImageAWT;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.image.ColorMap;
import com.jfixby.scarabei.api.image.GrayMap;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.tool.texture.progressor.api.GrayImagePyramidDecomposeSpecs;
import com.jfixby.tool.texture.progressor.api.GrayImagePyramidDecomposer;
import com.jfixby.tool.texture.progressor.api.ImagePyramidlayer;
import com.jfixby.tool.texture.progressor.api.TextureProgressor;
import com.jfixby.tool.texture.progressor.api.srlz.GrayImagePyramidComposition;
import com.jfixby.tool.texture.progressor.api.srlz.GrayImagePyramidCompositionlayer;
import com.jfixby.tool.texture.progressor.red.RedTextureProgressor;

public class TestDecomposePyramid {

	public static void main (final String[] args) throws IOException {
		ScarabeiDesktop.deploy();
		TextureProgressor.installComponent(new RedTextureProgressor());
		Json.installComponent("com.jfixby.cmns.adopted.gdx.json.RedJson");

		final File inputPNG = LocalFileSystem.ApplicationHome().child("input").child("1.png");
		final File output = LocalFileSystem.ApplicationHome().child("output").child("decomposed");
		output.makeFolder();
		output.clearFolder();

		final ColorMap inputImage = ImageAWT.readAWTColorMap(inputPNG);

		final GrayImagePyramidDecomposeSpecs settings = TextureProgressor.newGrayImagePyramidDeComposeSpecs();
		settings.setInputImage(inputImage.getRed());
		settings.setMaxIterations(8);

		final GrayImagePyramidDecomposer pyramid = TextureProgressor.newPyramidDecomposer(settings);
		pyramid.deCompose();

		final String original_name = inputPNG.nameWithoutExtension();

		{

			final File output_file = output.child(original_name + ".png");
			write(output_file, inputImage.getRed());
		}

		final Collection<ImagePyramidlayer> layes = pyramid.listLayers();

		final GrayImagePyramidComposition composition = new GrayImagePyramidComposition();
		composition.original_width = inputImage.getWidth();
		composition.original_height = inputImage.getHeight();
		composition.size = layes.size();
		for (int i = 0; i < layes.size(); i++) {
			final ImagePyramidlayer layer = layes.getElementAt(i);
			// GrayMap reduced = layer.getReduced();
			final GrayMap difference = layer.getDifference();
			final int k = 0 + i;
			// {
			// String child_name = original_name + "-" + k + "-reduced.png";
			// File output_file = output.child(child_name);
			// ImageAWT.writeToFile(reduced, output_file, "png");
			//
			// }
			{
				final String child_name = original_name + "-" + k + "-diff.png";

				final GrayImagePyramidCompositionlayer srlz_layer = new GrayImagePyramidCompositionlayer();
				srlz_layer.file_name = child_name;
				srlz_layer.level = i;
				composition.layers.add(srlz_layer);

				final File output_file = output.child(child_name);
				write(output_file, difference);
			}

		}
		final GrayMap tail = pyramid.getTail();
		{
			final String child_name = original_name + "-" + "tail.png";

			final GrayImagePyramidCompositionlayer srlz_layer = new GrayImagePyramidCompositionlayer();
			srlz_layer.file_name = child_name;
			srlz_layer.level = -1;
			composition.layers.add(srlz_layer);

			final File output_file = output.child(child_name);
			write(output_file, tail);
		}

		{
			final String child_name = original_name + GrayImagePyramidComposition.FILE_EXTENSION;

			final String data = Json.serializeToString(composition).toString();
			final File output_file = output.child(child_name);
			output_file.writeString(data);
			L.d("writing", output_file);
		}

	}

	private static void write (final File output_file, final GrayMap image) throws IOException {
		L.d("writing", output_file);
		ImageAWT.writeToFile(image, output_file, "png");
	}

}
