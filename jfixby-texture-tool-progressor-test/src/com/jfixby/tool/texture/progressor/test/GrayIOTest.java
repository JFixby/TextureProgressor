package com.jfixby.tool.texture.progressor.test;

import java.io.IOException;

import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.desktop.ImageAWT;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.image.ColorMap;
import com.jfixby.scarabei.api.image.GrayMap;
import com.jfixby.scarabei.api.log.L;

public class GrayIOTest {

    public static void main(String[] args) throws IOException {
	DesktopSetup.deploy();

	File inputPNG = LocalFileSystem.ApplicationHome().child("input").child("1.png");
	File output = LocalFileSystem.ApplicationHome().child("output");
	output.makeFolder();
	output.clearFolder();

	ColorMap inputImage = ImageAWT.readAWTColorMap(inputPNG);
	GrayMap base = inputImage.getRed();
	final int H = base.getHeight();
	final int W = base.getWidth();

	write(output.child("test.png"), base);

	GrayMap io_base = ImageAWT.readAWTGrayMap(output.child("test.png"));

	write(output.child("io_base.png"), io_base);
    }

    private static void write(File output_file, GrayMap image) throws IOException {
	L.d("writing", output_file);
	ImageAWT.writeToFile(image, output_file, "png");
    }
}
