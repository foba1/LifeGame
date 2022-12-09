package com.holub.refactoring;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImagePattern {
	
	private ImagePattern() {}
	
	public static Boolean[][] imageToPattern(Image in){
        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_BYTE_GRAY);	
        image.getGraphics().drawImage(in.getScaledInstance(64, 64, Image.SCALE_DEFAULT), 0, 0 , null);
        
        System.out.println(image.getColorModel());
        
        Boolean[][] pattern = new Boolean[64][64];
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        
        for(int i = 0; i < 64; i++)
        {
            for(int j = 0; j < 64; j++)
            {
            	
            	if (pixels[i*64 + j] < 0)
            	{
            		pattern[i][j] = false;
            	}
            	else
            	{
            		pattern[i][j] = true;
            	}
            }
        }
		
		return pattern;
		
	}
}
