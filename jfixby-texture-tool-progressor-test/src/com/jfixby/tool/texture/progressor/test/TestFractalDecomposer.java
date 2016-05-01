package com.jfixby.tool.texture.progressor.test;

import java.io.IOException;

import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.desktop.ImageAWT;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.image.ColorMap;
import com.jfixby.cmns.api.image.GrayMap;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;
import com.jfixby.tool.texture.progressor.api.FractalProgressorSpecs;
import com.jfixby.tool.texture.progressor.api.TextureProgressor;
import com.jfixby.tool.texture.progressor.red.RedTextureProgressor;

public class TestFractalDecomposer {

    public static void main(String[] args) throws IOException {
	DesktopSetup.deploy();
	TextureProgressor.installComponent(new RedTextureProgressor());
	Json.installComponent(new RedJson());

	File inputPNG = LocalFileSystem.ApplicationHome().child("input").child("1.png");
	File output = LocalFileSystem.ApplicationHome().child("output").child("decomposed");
	output.makeFolder();
	output.clearFolder();

	ColorMap inputImage = ImageAWT.readAWTColorMap(inputPNG);

	FractalProgressorSpecs settings = TextureProgressor.newFractalProgressorSpecs();

    }

    private static void write(File output_file, GrayMap image) throws IOException {
	L.d("writing", output_file);
	ImageAWT.writeToFile(image, output_file, "png");
    }

}
