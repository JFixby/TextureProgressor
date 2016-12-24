
package com.jfixby.tool.texture.progressor.test;

import java.io.IOException;

import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.desktop.ImageAWT;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.image.ColorMap;
import com.jfixby.scarabei.api.image.GrayMap;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.tool.texture.progressor.api.FractalProgressorSpecs;
import com.jfixby.tool.texture.progressor.api.TextureProgressor;
import com.jfixby.tool.texture.progressor.red.RedTextureProgressor;

public class TestFractalDecomposer {

	public static void main (final String[] args) throws IOException {
		DesktopSetup.deploy();
		TextureProgressor.installComponent(new RedTextureProgressor());
		Json.installComponent("com.jfixby.cmns.adopted.gdx.json.RedJson");

		final File inputPNG = LocalFileSystem.ApplicationHome().child("input").child("1.png");
		final File output = LocalFileSystem.ApplicationHome().child("output").child("decomposed");
		output.makeFolder();
		output.clearFolder();

		final ColorMap inputImage = ImageAWT.readAWTColorMap(inputPNG);

		final FractalProgressorSpecs settings = TextureProgressor.newFractalProgressorSpecs();

	}

	private static void write (final File output_file, final GrayMap image) throws IOException {
		L.d("writing", output_file);
		ImageAWT.writeToFile(image, output_file, "png");
	}

}
