/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackendRSA;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

		String filePath = "C:\\Users\\nvlong\\Desktop\\Untitled.png";
		BufferedImage image = inputImage(filePath);
		String keyK2 = findK2Key(image);
		System.out.println("Key K2: " + keyK2);

		 String binaryText = convertTextToBinary("Minato");
		//
		 System.out.println(binaryText);
		//
		System.out.println();

		for (int i = 0; i < binaryText.length(); i++) {
			if (i % 8 == 5) {
				System.out.print(binaryText.charAt(i));
			}
		}
		System.out.println();

		 divideCipherTextTo8Blocks(binaryText);
		System.out.println("Random number: ");

		String keyRandom = "";

		ArrayList<Integer> numbersRandom = new ArrayList<>();

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
	public static int[] divideImageTo8Blocks(BufferedImage image) {

		int height = image.getHeight();

		int temp = (height - 80) / 8;
		int result = 0;

		if (height < 88) {
			System.exit(0);
		}

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

	// Find K2 key
	public static String findK2Key(BufferedImage image) {

		String keyK2 = "";

		int[] milestones = divideImageTo8Blocks(image);

		int height = 0;

		for (int i = 0; i < 9; i++) {

			int red = 0;
			int green = 0;
			int blue = 0;
			int width = image.getWidth();

			// byte du tru
			if (i == 1) {
				continue;
			}

			for (height = milestones[i]; height < milestones[i + 1]; height++) {

				for (int j = 0; j < width; j++) {
					// System.out.println(j);
					int rgbValue = image.getRGB(j, height);

					red += (rgbValue >> 16) & 0xff;
					green += (rgbValue >> 8) & 0xff;
					blue += rgbValue & 0xff;
				}
			}

			// red max
			if ((red > green && red > blue) || (red == blue && red > green) || (red == green && red > blue)
					|| (red == green && red == blue)) {
				keyK2 += "0";
				continue;
			}

			// green max
			if ((green > red && green > blue) || (green == blue && blue > red)) {
				keyK2 += "1";
				continue;
			}

			// blue max
			if (blue > green && blue > red) {
				keyK2 += "2";
			}
		}

		return keyK2;
	}
}
