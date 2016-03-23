package com.jfixby.tool.texture.progressor.test;

import java.io.IOException;

import com.jfixby.cmns.api.desktop.ImageAWT;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.image.ColorMap;
import com.jfixby.cmns.api.image.GrayMap;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopAssembler;

public class GrayIOTest {

    public static void main(String[] args) throws IOException {
	DesktopAssembler.setup();

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
