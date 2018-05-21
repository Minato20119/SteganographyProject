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

        int[] milestones = new int[9];

        for (int i = 0; i < milestones.length; i++) {

            if (i == 1) {
                result += 80;
                milestones[i] = result;
                continue;
            }

            if (i == 8) {
                result += height - result;
                milestones[i] = result;
                break;
            }

            result += temp;
            milestones[i] = result;
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

    // Divide the cipher text into 8 blocks
    public static void divideCipherTextTo8Blocks(String cipherText) {
        
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
