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
public class ReceiveSite2 {

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
        
//        binary = convertTextToBinary(binaryTemp);

        return keys;
    }

    // Get key K1, key K2 and length of blocks message
//    public static List<String> getKeys(BufferedImage image) {
//
//        String keyK1 = "";
//        String keyK2 = "";
//        String lengthMessage = "";
//
//        String tempBinary = getBinaryFromRGB(image);
//
//        System.out.println("tempBinary: " + tempBinary);
//        System.out.println("length: " + tempBinary.length());
//
//        if (tempBinary == null) {
//            return null;
//        }
//
//        // Key K1 has 8byte = 64bit
//        String tempKeyK1;
//        if (tempBinary.length() > 64) {
//            tempKeyK1 = tempBinary.substring(0, 64);
//
//            for (int i = 0; i < 64; i += 8) {
//                if (i + 8 > tempKeyK1.length()) {
//                    break;
//                }
//                String binary = tempKeyK1.substring(i, i + 8);
//                int charCode = convertBinaryToDecimal(binary);
//                keyK1 += new Character((char) charCode).toString();
//            }
//
//            System.out.println("KeyK1: " + keyK1);
//        }
//
//        // Key K2 has 8byte = 64bit; and length of blocks message
//        String tempKeyK2;
//        String tempLengthMessage;
//
//        if (tempBinary.length() > 128) {
//            tempKeyK2 = tempBinary.substring(64, 128);
//
//            for (int i = 0; i < 64; i += 8) {
//                if (i + 8 > tempKeyK2.length()) {
//                    break;
//                }
//                String binary = tempKeyK2.substring(i, i + 8);
//                int charCode = convertBinaryToDecimal(binary);
//                keyK2 += new Character((char) charCode).toString();
//            }
//            
//            System.out.println("KeyK2: " + keyK2);
//
//            // Length block message
//            tempLengthMessage = tempBinary.substring(128);
//
//            for (int i = 0; i < tempLengthMessage.length(); i += 8) {
//
//                if (lengthMessage.contains("=")) {
//                    lengthMessage = lengthMessage.substring(0, lengthMessage.length() - 1);
//                    break;
//                }
//
//                if (i + 8 < tempLengthMessage.length()) {
//                    if (i + 8 > tempLengthMessage.length()) {
//                        break;
//                    }
//                    String binary = tempLengthMessage.substring(i, i + 8);
//                    int charCode = convertBinaryToDecimal(binary);
//                    lengthMessage += new Character((char) charCode).toString();
//                }
//            }
//        }
//
//        List<String> keys = new ArrayList<>();
//        keys.add(keyK1);
//        keys.add(keyK2);
//        keys.add(lengthMessage);
//
//        return keys;
//    }
    public static String getBinaryCipherText(BufferedImage image) {

        int[] milestones = divideImageTo8Blocks(image);

//        List<String> keys = getKeys(image);
        List<String> keys = getBinaryFromRGB(image);
        
        if (keys.size() < 3) {
            return null;
        }

        String keyK1 = keys.get(0);
        String keyK2 = keys.get(1);
        String lengthMessage = keys.get(2);

        int lengthBlockMessage = Integer.parseInt(lengthMessage);

        List<String> blockBinaryMessages = new ArrayList<>();

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
            blockBinaryMessages.add(binaryCipherText);
            checkLocationKeyK2++;
        }

        String binaryCipher = "";

        for (int i = 0; i < keyK1.length(); i++) {
            int localKeyK1 = Character.getNumericValue(keyK1.charAt(i));
            binaryCipher += blockBinaryMessages.get(localKeyK1);
        }

        return binaryCipher;
    }

    // Convert binaryCipherText to cipher text
    public static String convertBinaryCipherTextToCipherText(BufferedImage image) {

        String binaryCipherText = getBinaryCipherText(image);

        String cipherText = "";

        for (int i = 0; i < binaryCipherText.length(); i += 8) {

            if (i + 8 < binaryCipherText.length()) {
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
