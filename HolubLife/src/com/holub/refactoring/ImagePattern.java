package com.holub.refactoring;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ImagePattern {

    public static boolean[][] imageToPattern(File in) throws Exception {
        Image imagefile = ImageIO.read(in);

        int height = imagefile.getHeight(null);
        int width = imagefile.getWidth(null);
        int heightStart = 0;
        int widthStart = 0;

        if (height == -1 || width == -1) {
            throw new Exception("NotImageFile");
        }

        if (height > width) {
            double ratio = (double) height / 64;

            height /= ratio;
            width /= ratio;
            heightStart = 0;
            widthStart = 32 - width / 2;
        } else {
            double ratio = (double) width / 64;

            height /= ratio;
            width /= ratio;
            heightStart = 32 - height / 2;
            widthStart = 0;
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        image.getGraphics()
            .drawImage(imagefile.getScaledInstance(width, height, Image.SCALE_DEFAULT), 0, 0, null);

        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        boolean[][] pattern = new boolean[64][64];
        for (int i = 0; i < 64; i++) {
            Arrays.fill(pattern[i], false);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (pixels[i * width + j] >= 0) {
                    pattern[i + heightStart][j + widthStart] = true;
                }
            }
        }

        return pattern;
    }
}

