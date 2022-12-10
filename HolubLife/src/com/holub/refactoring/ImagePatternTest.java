package com.holub.refactoring;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ImagePatternTest {

	@Test
	void ImageToPattern_64x64() throws Exception {  
		File image_64x64 = new File("./example_image/black_square_64x64.png");
		boolean[][] pattern = ImagePattern.imageToPattern(image_64x64);
		
		boolean[][] answer = new boolean[64][64];
		for (int i=0; i<64; i++)
        	Arrays.fill(answer[i], false);
		
		for (int i=13; i< 51; i++) {
			for (int j=12; j<52; j++) {
				answer[i][j] = true;
			}
		}
		
		for (int j=13; j<51;j++) {
			answer[12][j] = true;
			answer[51][j] = true;
		}
		
        assertTrue(isSamePattern(answer, pattern));
	}
	
	@Test
	void ImageToPattern_100x100() throws Exception {     
		File image_100x100 = new File("./example_image/black_square_100x100.png");
		boolean[][] pattern = ImagePattern.imageToPattern(image_100x100);
		
		boolean[][] answer = new boolean[64][64];
		for (int i=0; i<64; i++)
        	Arrays.fill(answer[i], false);
		
		for (int i=12; i< 52; i++) {
			for (int j=12; j<52; j++) {
				answer[i][j] = true;
			}
		}
		
        assertTrue(isSamePattern(answer, pattern));
	}
	
	@Test
	void ImageToPattern_30x30() throws Exception {     
		File image_30x30 = new File("./example_image/black_square_30x30.png");
		boolean[][] pattern = ImagePattern.imageToPattern(image_30x30);
		
		boolean[][] answer = new boolean[64][64];
		for (int i=0; i<64; i++)
        	Arrays.fill(answer[i], false);
		
		for (int i=13; i< 51; i++) {
			for (int j=13; j<51; j++) {
				answer[i][j] = true;
			}
		}
		
        assertTrue(isSamePattern(answer, pattern));
	}
	
	@Test
	void ImageToPattern_100x50() throws Exception {     
		File image_100x50 = new File("./example_image/black_square_100x50.png");
		boolean[][] pattern = ImagePattern.imageToPattern(image_100x50);
		
		boolean[][] answer = new boolean[64][64];
		for (int i=0; i<64; i++)
        	Arrays.fill(answer[i], false);
		
		for (int i=22; i< 42; i++) {
			for (int j=12; j<52; j++) {
				answer[i][j] = true;
			}
		}
		
        assertTrue(isSamePattern(answer, pattern));
	}
	
	@Test
	void ImageToPattern_15x45() throws Exception {       
		File image_14x45 = new File("./example_image/black_square_15x45.png");
		boolean[][] pattern = ImagePattern.imageToPattern(image_14x45);
		
		boolean[][] answer = new boolean[64][64];
		for (int i=0; i<64; i++)
        	Arrays.fill(answer[i], false);
		
		for (int i=13; i< 51; i++) {
			for (int j=26; j<39; j++) {
				answer[i][j] = true;
			}
		}
		 
        assertTrue(isSamePattern(answer, pattern));
	}
	
	private boolean isSamePattern(boolean[][] array1, boolean[][] array2) {
		for (int i=0; i< 64; i++) {
			for (int j=0; j<64; j++) {
				if (array1[i][j] == array2[i][j]) continue;
				else return false;
			}
		}
		
		return true;
	}

}
