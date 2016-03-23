package com.jfixby.tool.texture.progressor.test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jfixby.cmns.api.desktop.ImageAWT;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.red.desktop.DesktopAssembler;

public class ScaleTestAWT {

    public static void main(String[] args) throws IOException {
	DesktopAssembler.setup();

	File inputPNG = LocalFileSystem.ApplicationHome().child("input").child("1.png");
	File output = LocalFileSystem.ApplicationHome().child("output");
	output.makeFolder();
	output.clearFolder();

	BufferedImage input = ImageAWT.readFromFile(inputPNG);

	final int H = input.getHeight();
	final int W = input.getWidth();

	BufferedImage reduced = ImageAWT.awtScaleTo(input, W / 2, H / 2);
	// reduced =
	// ImageProcessing.newGrayMap(ImageProcessing.roundArguments(reduced), W
	// / 2, H / 2);
	BufferedImage expanded = ImageAWT.awtScaleTo(reduced, W, H);

	BufferedImage diff = ImageAWT.linearMix(input, 1f, expanded, -1f);

	write(output.child("test.png"), input);
	write(output.child("reduced.png"), reduced);
	write(output.child("expanded.png"), expanded);
	write(output.child("diff.png"), diff);

	BufferedImage io_reduced = ImageAWT.readFromFile(output.child("reduced.png"));
	BufferedImage io_diff = ImageAWT.readFromFile(output.child("diff.png"));
	BufferedImage io_expanded = ImageAWT.awtScaleTo(io_reduced, W, H);
	BufferedImage restored = ImageAWT.linearMix(io_diff, 1f, io_expanded, 1f);

	write(output.child("restored.png"), restored);
    }

    private static void write(File output_file, BufferedImage image) throws IOException {
	ImageAWT.writeToFile(image, output_file, "png");
    }
}
