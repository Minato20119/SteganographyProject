/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendRSA;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

/**
 *
 * @author Minato
 */
public class JavaApplication4 {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		String filePath = "C:\\Users\\Minato\\Pictures\\Image\\Encode_Decode.png";
		// BufferedImage image = inputImage(filePath);
		// divideImageTo8Blocks(image);

		// String binaryText = convertTextToBinary("Minato");
		//
		// System.out.println(binaryText);
		//
		// System.out.println();
		//
		// for (int i = 0; i < binaryText.length(); i++) {
		// if (i % 8 == 5) {
		// System.out.print(binaryText.charAt(i));
		// }
		// }

		System.out.println();

		// divideCipherTextTo8Blocks(binaryText);

		System.out.println("Random number: ");

		String keyRandom = "";

		ArrayList<Integer> numbersRandom = new ArrayList<Integer>();

		for (int i = 0; i < 8; i++) {
			numbersRandom.add(i);
		}
		Collections.shuffle(numbersRandom);
		
		for (int i = 0; i < numbersRandom.size(); i++) {
			keyRandom += numbersRandom.get(i);
		}
		
		System.out.println(keyRandom);
	}

	// Convert text to binary
	public static String convertTextToBinary(String text) {

		byte[] bytes = text.getBytes();
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
	public static void divideImageTo8Blocks(BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();

		System.out.println("Height: " + height);
		System.out.println("Width: " + width);

		int temp = (height - 80) / 8;
		int result = 0;

		int[] milestones = new int[9];

		for (int i = 0; i < milestones.length; i++) {

			if (i == 1) {
				result += 80;
				milestones[i] = result;
				System.out.println("store: " + i + ": result: " + result);

				continue;
			}

			if (i == 8) {
				result += height - result;
				milestones[i] = result;
				System.out.println("i: " + i + ": result: " + height + ": " + (height - result));
				break;
			}

			result += temp;
			milestones[i] = result;
			System.out.println("i: " + i + ": result: " + result + ": " + (temp));
		}

		for (int i = 0; i < milestones.length; i++) {
			System.out.println("i: " + milestones[i]);
		}
	}

	// Divide the cipher text into 8 blocks
	public static void divideCipherTextTo8Blocks(String binaryCipherText) {

		String[] arrayBinaryCipherText = new String[8];

		for (int i = 0; i < arrayBinaryCipherText.length; i++) {
			arrayBinaryCipherText[i] = "";
		}

		for (int i = 0; i < binaryCipherText.length(); i++) {

			if (i % 8 == 0) {
				arrayBinaryCipherText[0] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			if (i % 8 == 1) {
				arrayBinaryCipherText[1] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			if (i % 8 == 2) {
				arrayBinaryCipherText[2] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			if (i % 8 == 3) {
				arrayBinaryCipherText[3] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			if (i % 8 == 4) {
				arrayBinaryCipherText[4] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			if (i % 8 == 5) {
				arrayBinaryCipherText[5] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			if (i % 8 == 6) {
				arrayBinaryCipherText[6] += String.valueOf(binaryCipherText.charAt(i));
				continue;
			}

			if (i % 8 == 7) {
				arrayBinaryCipherText[7] += String.valueOf(binaryCipherText.charAt(i));
			}
		}

		for (int i = 0; i < arrayBinaryCipherText.length; i++) {
			System.out.println("i: " + i + ": " + arrayBinaryCipherText[i]);
		}
	}
}
