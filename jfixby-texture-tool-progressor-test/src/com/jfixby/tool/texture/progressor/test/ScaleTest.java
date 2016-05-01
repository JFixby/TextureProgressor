package com.jfixby.tool.texture.progressor.test;

import java.io.IOException;

import com.jfixby.cmns.api.desktop.ImageAWT;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.image.ColorMap;
import com.jfixby.cmns.api.image.GrayMap;
import com.jfixby.cmns.api.image.ImageProcessing;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class ScaleTest {

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

	GrayMap reduced = ImageAWT.awtScaleTo(base, W / 2, H / 2);
//	reduced = ImageProcessing.newGrayMap(ImageProcessing.roundArguments(reduced), W / 2, H / 2);
	GrayMap expanded = ImageAWT.awtScaleTo(reduced, W, H);

	GrayMap diff = ImageProcessing.newGrayMap(ImageProcessing.minus(base, expanded), W, H);

	write(output.child("test.png"), base);
	write(output.child("reduced.png"), reduced);
	write(output.child("expanded.png"), expanded);
	write(output.child("diff.png"), diff);

	GrayMap io_reduced = ImageAWT.readAWTGrayMap(output.child("reduced.png"));
	GrayMap io_diff = ImageAWT.readAWTGrayMap(output.child("diff.png"));
	GrayMap io_expanded = ImageAWT.awtScaleTo(io_reduced, W, H);
	GrayMap restored = ImageProcessing.newGrayMap(ImageProcessing.plus(io_diff, io_expanded), W, H);

	write(output.child("restored.png"), restored);
    }

    private static void write(File output_file, GrayMap image) throws IOException {
	L.d("writing", output_file);
	ImageAWT.writeToFile(image, output_file, "png");
    }
}
