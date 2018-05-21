/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendRSA;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

/**
 *
 * @author Minato
 */
public class SentSite2 {

	private static BufferedReader bufferedReader;

	// Input image
	public static BufferedImage inputImage(String imageFilePath) {
		BufferedImage image = null;
		try {

			File imageFile = new File(imageFilePath);
			image = ImageIO.read(imageFile);

		} catch (IOException e) {
			System.out.println("Error input image: " + e);
		}
		return image;
	}

	// Divide the image into 8 blocks
	public static int[] divideImageTo8Blocks(BufferedImage image) {

		int height = image.getHeight();

		int temp = (height - 80) / 8;
		int result = 0;

		// 8 block + key K1 and K2
		int[] milestones = new int[10];
		
		// milestones[0] = 0;

		for (int i = 0; i < milestones.length; i++) {

			if (i == 1) {
				result += 80;
				milestones[i + 1] = result;
				continue;
			}

			if (i == 8) {
				result += height - result;
				milestones[i + 1] = result;
				break;
			}

			result += temp;
			milestones[i + 1] = result;
		}

		return milestones;
	}

	// Read text file
	public static String inputTextFile(String pathFileText) throws IOException {

		FileReader fileReader = null;
		String plainText = "";

		try {
			fileReader = new FileReader(pathFileText);
			bufferedReader = new BufferedReader(fileReader);

			String readLine;
			while ((readLine = bufferedReader.readLine()) != null) {
				plainText += readLine + "\n";
			}

		} catch (IOException e) {
			System.out.println("Error input text file: " + e);
		} finally {

			if (bufferedReader != null) {
				bufferedReader.close();
			}

			if (fileReader != null) {
				fileReader.close();
			}
		}
		return plainText;
	}

	// Convert text to binary
	public static String convertTextToBinary(String cipherText) {

		byte[] bytes = cipherText.getBytes();
		StringBuilder binary = new StringBuilder();

		for (byte b : bytes) {
			int value = b;

			for (int i = 0; i < 8; i++) {
				binary.append((value & 128) == 0 ? 0 : 1);
				value <<= 1;
			}
		}

		return binary.toString();
	}

	// Divide the cipher text into 8 blocks
	public static String[] divideCipherTextTo8Blocks(String binaryCipherText) {

		String[] arrayBinaryCipherText = new String[8];

		for (int i = 0; i < arrayBinaryCipherText.length; i++) {
			arrayBinaryCipherText[i] = "";
		}

		for (int i = 0; i < binaryCipherText.length(); i++) {

			// bit 0 become M0
			if (i % 8 == 0) {
				arrayBinaryCipherText[0] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			// bit 1 become M1
			if (i % 8 == 1) {
				arrayBinaryCipherText[1] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			// bit 2 become M2
			if (i % 8 == 2) {
				arrayBinaryCipherText[2] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			// bit 3 become M3
			if (i % 8 == 3) {
				arrayBinaryCipherText[3] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			// bit 4 become M4
			if (i % 8 == 4) {
				arrayBinaryCipherText[4] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			// bit 5 become M5
			if (i % 8 == 5) {
				arrayBinaryCipherText[5] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			// bit 6 become M6
			if (i % 8 == 6) {
				arrayBinaryCipherText[6] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			// bit 7 become M7
			if (i % 8 == 7) {
				arrayBinaryCipherText[7] += String.valueOf(binaryCipherText.charAt(i));
			}
		}

		return arrayBinaryCipherText;

	}

	// Create K1 key random
	public static String createK1Key() {

		String keyRandom = "";

		ArrayList<Integer> numbersRandom = new ArrayList<Integer>();

		for (int i = 0; i < 8; i++) {
			numbersRandom.add(i);
		}
		Collections.shuffle(numbersRandom);

		for (int i = 0; i < numbersRandom.size(); i++) {
			keyRandom += numbersRandom.get(i);
		}

		return keyRandom;
	}

	public static void findK2Key(BufferedImage image) {

		int[] milestones = divideImageTo8Blocks(image);
		

		int height = 0;
		
		for (int i = 0; i < 9; i++) {
			
			// because byte du tru
			if (i == 1) {
				continue;
			}
			
			for (height = milestones[i]; height <= milestones[i + 1]; height++) {
				for (int width = 0; width < image.getWidth(); width++) {
					int rgbValue = image.getRGB(width, height);
					
					
				}
			}
		}
	}

	// Get binary
	private static String convertDecimalToBinary(int value) {

		String binaryString = "0000000" + Integer.toBinaryString(value);
		binaryString = binaryString.substring(binaryString.length() - 8);

		return binaryString;
	}

	// Convert binary to decimal
	private static int convertBinaryToDecimal(String binary) {

		int decimal = Integer.parseInt(binary, 2);

		return decimal;
	}

	// Write new image
	public static void writeNewImage(BufferedImage image, String pathOutputNewImage) {
		try {
			File file = new File(pathOutputNewImage);
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			System.out.println("Error write image: " + e);
		}
	}
}
