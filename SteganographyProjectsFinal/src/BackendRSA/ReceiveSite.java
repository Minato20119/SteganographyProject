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
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Minato
 */
public class ReceiveSite {

    private static final int NUMBER_BLOK_CIPHER_INDICATOR = 6;
    private static final int NUMBER_BLOK_CIPHER = 4;

    public static BufferedImage inputImage(String pathFileStegoImage) {
        BufferedImage image = null;
        File file;
        try {

            file = new File(pathFileStegoImage);
            image = ImageIO.read(file);

        } catch (IOException e) {
            System.out.println("Error input image: " + e);
        }
        return image;
    }

    // Divide the image into 8 blocks
    public static int[] divideImageTo8Blocks(BufferedImage image) {

        int height = image.getHeight();

        if (height < 88) {
            System.exit(0);
        }

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

    public static List<String> getBinaryFromRGB(BufferedImage image) {

        int[] milestones = divideImageTo8Blocks(image);

        int WIDTH = image.getWidth();

        String binary = "";
        String binaryTemp = "";
        String temp = "";

        for (int height = milestones[1]; height < milestones[2]; height++) {

            if (temp.equals("=")) {
                break;
            }

            for (int width = 0; width < WIDTH; width++) {
                int rgbValue = image.getRGB(width, height);

                int red = (rgbValue >> 16) & 0xff;
                int green = (rgbValue >> 8) & 0xff;
                int blue = rgbValue & 0xff;

                // convert to binary
                String redBinary = convertDecimalToBinary(red);
                String greenBinary = convertDecimalToBinary(green);
                String blueBinary = convertDecimalToBinary(blue);

                binary += redBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR);
                binary += greenBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR);
                binary += blueBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR);

                if (binary.length() >= 8) {

                    temp = binary.substring(0, 8);
                    binary = binary.substring(8);

                    int charCode = convertBinaryToDecimal(temp);
                    temp = new Character((char) charCode).toString();

                    if (temp.equals("=")) {
                        break;
                    }

                    binaryTemp += temp;
                }
            }
        }

        List<String> keys = new ArrayList<>();

        if (binaryTemp.length() > 16) {

            keys.add(binaryTemp.substring(0, 8));
            keys.add(binaryTemp.substring(8, 16));
            keys.add(binaryTemp.substring(16));
        }

        return keys;
    }

    public static String getBinaryCipherText(BufferedImage image) {

        int[] milestones = divideImageTo8Blocks(image);

        List<String> keys = getBinaryFromRGB(image);

        if (keys.size() < 3) {
            return null;
        }

        String keyK1 = keys.get(0);
        String keyK2 = keys.get(1);
        String lengthMessage = keys.get(2);

        int lengthBlockMessage = Integer.parseInt(lengthMessage);

        String[] blockBinaryMessages = new String[8];

        int WIDTH = image.getWidth();
        int checkLocationKeyK2 = 0;

        for (int i = 0; i < 9; i++) {

            String binaryCipherText = "";

            if (i == 1) {
                continue;
            }

            for (int height = milestones[i]; height < milestones[i + 1]; height++) {

                if (binaryCipherText.length() >= lengthBlockMessage) {

                    break;
                }

                int localKeyK2 = Character.getNumericValue(keyK2.charAt(checkLocationKeyK2));

                for (int width = 0; width < WIDTH; width++) {

                    int rgbValue = image.getRGB(width, height);

                    if (binaryCipherText.length() < lengthBlockMessage) {
                        binaryCipherText += getBinaryCipher(rgbValue, localKeyK2);
                        continue;
                    }
                    break;
                }
            }

            blockBinaryMessages[checkLocationKeyK2] = binaryCipherText;
            checkLocationKeyK2++;
        }

//        for (int i = 0; i < blockBinaryMessages.length; i++) {
//            System.out.println("blockBinaryMessages " + i + ": " + blockBinaryMessages[i]);
//        }
        String[] blockBinary = new String[8];

        for (int i = 0; i < keyK1.length(); i++) {
            //4
            int localKeyK1 = Character.getNumericValue(keyK1.charAt(i));
            blockBinary[localKeyK1] = blockBinaryMessages[i];

        }

        String binaryCipher = mergeCipherTextTo8Blocks(blockBinary);

        return binaryCipher;
    }

    // Merge the cipher text into 8 blocks
    public static String mergeCipherTextTo8Blocks(String[] blockBinary) {

        String binaryCipherText = "";

        if (blockBinary.length == 0 || blockBinary[0].length() == 0) {
            return null;
        }

        for (int i = 0; i < blockBinary[0].length(); i++) {

            for (String blockBinary1 : blockBinary) {
                binaryCipherText += blockBinary1.substring(i, i + 1);
            }
        }

        return binaryCipherText;

    }

    // Convert binaryCipherText to cipher text
    public static String convertBinaryCipherTextToCipherText(BufferedImage image) {

        String binaryCipherText = getBinaryCipherText(image);

        String cipherText = "";

        for (int i = 0; i < binaryCipherText.length(); i += 8) {

            if (i + 8 <= binaryCipherText.length()) {
                String tempBinaryCipherText = binaryCipherText.substring(i, i + 8);
                int charCode = convertBinaryToDecimal(tempBinaryCipherText);
                cipherText += new Character((char) charCode).toString();
            }

        }

        return cipherText;
    }

    private static String getBinaryCipher(int rgbValue, int localKeyK2) {

        int red = (rgbValue >> 16) & 0xff;
        int green = (rgbValue >> 8) & 0xff;
        int blue = rgbValue & 0xff;

        // convert to binary
        String redBinary = convertDecimalToBinary(red);
        String greenBinary = convertDecimalToBinary(green);
        String blueBinary = convertDecimalToBinary(blue);

        if (localKeyK2 == 0) {
            String getTwoLSB = redBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR);

            // Only embeded channel 1
            if (getTwoLSB.equals("00")) {
                return greenBinary.substring(NUMBER_BLOK_CIPHER);
            }
            // Only embeded channel 2
            if (getTwoLSB.equals("01")) {
                return blueBinary.substring(NUMBER_BLOK_CIPHER);
            }
            // Embeded channel 1 and channel 2
            if (getTwoLSB.equals("10")) {
                return greenBinary.substring(NUMBER_BLOK_CIPHER) + blueBinary.substring(NUMBER_BLOK_CIPHER);
            }
            // No embeded
            return "";

        }

        if (localKeyK2 == 1) {
            String getTwoLSB = greenBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR);

            // Only embeded channel 1
            if (getTwoLSB.equals("00")) {
                return redBinary.substring(NUMBER_BLOK_CIPHER);
            }
            // Only embeded channel 2
            if (getTwoLSB.equals("01")) {
                return blueBinary.substring(NUMBER_BLOK_CIPHER);
            }
            // Embeded channel 1 and channel 2
            if (getTwoLSB.equals("10")) {
                return redBinary.substring(NUMBER_BLOK_CIPHER) + blueBinary.substring(NUMBER_BLOK_CIPHER);
            }
            // No embeded
            return "";
        }

        if (localKeyK2 == 2) {
            String getTwoLSB = blueBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR);

            // Only embeded channel 1
            if (getTwoLSB.equals("00")) {
                return redBinary.substring(NUMBER_BLOK_CIPHER);
            }
            // Only embeded channel 2
            if (getTwoLSB.equals("01")) {
                return greenBinary.substring(NUMBER_BLOK_CIPHER);
            }
            // Embeded channel 1 and channel 2
            if (getTwoLSB.equals("10")) {
                return redBinary.substring(NUMBER_BLOK_CIPHER) + greenBinary.substring(NUMBER_BLOK_CIPHER);
            }
            // No embeded
            return "";
        }

        return "";
    }

    // Convert binary to decimal
    private static int convertBinaryToDecimal(String binary) {

        int decimal = Integer.parseInt(binary, 2);

        return decimal;
    }

    // Get binary
    private static String convertDecimalToBinary(int value) {

        String binaryString = "0000000" + Integer.toBinaryString(value);
        binaryString = binaryString.substring(binaryString.length() - 8);

        return binaryString;
    }

    // Convert text to binary
    private static String convertTextToBinary(String cipherText) {

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
}
