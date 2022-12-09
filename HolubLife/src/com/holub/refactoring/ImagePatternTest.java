package com.holub.refactoring;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImagePatternTest {
	
	private Image image500_500;
	private Image image64_64;
	
	private Boolean[][] answer;
	private Boolean[][] pattern;
    
	@BeforeEach
    void initialize(){
		try {
			this.image500_500 = ImageIO.read(new File("example_image/Black_square_500_500.png"));
			this.image64_64 = ImageIO.read(new File("example_image/Black_square_64_64.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
    	
		this.answer = ImagePattern.imageToPattern(image64_64);
		this.pattern = ImagePattern.imageToPattern(image500_500);
    }
    
	@Test
	void testImageToPattern() throws IOException {     
        assertTrue(IsSamePattern(answer, pattern));
	}
	
	private boolean IsSamePattern(Boolean[][] answer, Boolean[][] pattern) {
		for (int i=0; i< 64; i++) {
			for (int j=0; j<64; j++) {
				if (answer[i][j] == pattern[i][j]) continue;
				else return false;
			}
		}
		
		return true;
	}

}
