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
import java.util.List;

import javax.imageio.ImageIO;

/**
 *
 * @author Minato
 */
public class SentSite {

    private static final int LIMIT_BIT_TO_CHANGE = 7;
    private static final int NUMBER_BLOCK_CIPHER = 4;
    private static final int NUMBER_BLOCK_CIPHER_INDICATOR = 6;
    private static int increase = 0;
    private static int increase2 = 0;
    private static int checkIndicatorToChange = 0;
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

        ArrayList<Integer> numbersRandom = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            numbersRandom.add(i);
        }
        Collections.shuffle(numbersRandom);

        for (int i = 0; i < numbersRandom.size(); i++) {
            keyRandom += numbersRandom.get(i);
        }

        return keyRandom;
    }

    // Find K2 key
    public static String findK2Key(BufferedImage image, int[] milestones) {

        String keyK2 = "";

        for (int i = 0; i < 9; i++) {

            int red = 0;
            int green = 0;
            int blue = 0;
            int width = image.getWidth();

            // byte du tru
            if (i == 1) {
                continue;
            }

            for (int height = milestones[i]; height < milestones[i + 1]; height++) {

                for (int j = 0; j < width; j++) {
                    int rgbValue = image.getRGB(j, height);

                    red += (rgbValue >> 16) & 0xff;
                    green += (rgbValue >> 8) & 0xff;
                    blue += rgbValue & 0xff;
                }
            }

            // red max
            if ((red > green && red > blue) || (red == blue && red > green)) {
                keyK2 += "0";
                continue;
            }

            // green max
            if ((green > red && green > blue) || (red == green && red == blue) || (red == green && red > blue)
                    || (blue == green && blue > red)) {
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

    public static List<String> getValueRGB(BufferedImage image, String binaryString) {

        int bitsNhung = 0;

        int[] milestones = divideImageTo8Blocks(image);

        String keyK1 = createK1Key();
        String keyK2 = findK2Key(image, milestones);

        List<String> keys = new ArrayList<>();
        keys.add(keyK1);
        keys.add(keyK2);

        String[] blockBinaryCipherText = divideCipherTextTo8Blocks(binaryString);

        int WIDTH = image.getWidth();
        int indexKey = 0;
        int indexKeyK1 = 0;
        
        System.out.println("height + width: " + (WIDTH * image.getHeight()));

        increase2 = 0;
        for (int i = 0; i < 9; i++) {

            // byte du tru
            if (i == 1) {
                // Embed K1 and K2
                embedK1K2andLengthCipher(keyK1, keyK2, blockBinaryCipherText[indexKeyK1].length(), milestones[i], milestones[i + 1], image);
                keys.add(String.valueOf(blockBinaryCipherText[indexKeyK1].length()));
                continue;
            }

            indexKeyK1 = Integer.valueOf(String.valueOf(keyK1.charAt(indexKey)));
            int indexKeyK2 = Integer.valueOf(String.valueOf(keyK2.charAt(indexKey)));

            if (blockBinaryCipherText[indexKeyK1].length() % 4 == 1) {
                blockBinaryCipherText[indexKeyK1] += "000";
            }

            if (blockBinaryCipherText[indexKeyK1].length() % 4 == 2) {
                blockBinaryCipherText[indexKeyK1] += "00";
            }

            if (blockBinaryCipherText[indexKeyK1].length() % 4 == 3) {
                blockBinaryCipherText[indexKeyK1] += "00";
            }

            for (int height = milestones[i]; height < milestones[i + 1]; height++) {

                for (int width = 0; width < WIDTH; width++) {

                    int rgbValue = image.getRGB(width, height);
                    
                    // Set new rgb
                    int newRGBValue = setNewRGBValue(rgbValue, indexKeyK2,
                            blockBinaryCipherText[indexKeyK1]);

                    image.setRGB(width, height, newRGBValue);
                    increase2++;
                }
            }
            bitsNhung += increase;
            increase = 0;
            indexKey++;
        }
        int pixelsUsed = increase2;
        System.out.println("bitsNhung: " + bitsNhung);
        System.out.println("pixelsUsed: " + pixelsUsed);
        return keys;
    }

    private static int setNewRGBValue(int rgbValue, int indexKeyK2, String blockBinaryCipherText) {

        int red = (rgbValue >> 16) & 0xff;
        int green = (rgbValue >> 8) & 0xff;
        int blue = rgbValue & 0xff;

        // convert rgb value to binary
        String redBinary = convertDecimalToBinary(red);
        String greenBinary = convertDecimalToBinary(green);
        String blueBinary = convertDecimalToBinary(blue);

        if (indexKeyK2 == 0) {

            int channel1 = addCipherToBit(greenBinary, blockBinaryCipherText);
            int channel1Changed = changeChannelOne(green, channel1);

            int channel2 = addCipherToBit(blueBinary, blockBinaryCipherText);
            int channel2Changed = changeChannelTwo(blue, channel2);

            int changeIndicator = changeIndicator(redBinary);

            checkIndicatorToChange = 0;

            rgbValue = (changeIndicator << 16) | (channel1Changed << 8) | channel2Changed;
            return rgbValue;
        }

        if (indexKeyK2 == 1) {

            int redNew = addCipherToBit(redBinary, blockBinaryCipherText);
            int redChanged = changeChannelOne(red, redNew);

            int blueNew = addCipherToBit(blueBinary, blockBinaryCipherText);
            int blueChanged = changeChannelTwo(blue, blueNew);

            int changeIndicator = changeIndicator(greenBinary);

            // Reset check green to change
            checkIndicatorToChange = 0;

            rgbValue = (redChanged << 16) | (changeIndicator << 8) | blueChanged;
            return rgbValue;

        }

        if (indexKeyK2 == 2) {

            int redNew = addCipherToBit(redBinary, blockBinaryCipherText);
            int redChanged = changeChannelOne(red, redNew);

            int greenNew = addCipherToBit(greenBinary, blockBinaryCipherText);
            int greenChanged = changeChannelTwo(green, greenNew);

            int blueChanged = changeIndicator(blueBinary);

            // Reset check green to change
            checkIndicatorToChange = 0;

            rgbValue = (redChanged << 16) | (greenChanged << 8) | blueChanged;
            return rgbValue;
        }

        return rgbValue;
    }

    // Add cipher to red or blue
    private static int addCipherToBit(String rgbBinary, String binaryCipher) {

        if (increase + NUMBER_BLOCK_CIPHER > binaryCipher.length()) {

            // No embed (270 - 255 = 15 > 7)
            return 270;
        }

        String getCipher = binaryCipher.substring(increase, increase + NUMBER_BLOCK_CIPHER);

        // System.out.println("Cipher: " + getCipher);
        String rgbChanged = rgbBinary.substring(0, NUMBER_BLOCK_CIPHER) + getCipher;

        // System.out.println("rgbChanged: " + rgbChanged);
        int rgbNew = convertBinaryToDecimal(rgbChanged);

        return rgbNew;
    }

    // Check RGB to change
    private static int changeChannelOne(int rgbValue, int channel1) {

        // System.out.println("Red: " + red + " RedNew: " + redNew + " Result: "
        // +
        // Math.abs(red - redNew));
        if (Math.abs(rgbValue - channel1) > LIMIT_BIT_TO_CHANGE) {
            // System.out.println("No Embed");
            return rgbValue;
        }

        increase = increase + NUMBER_BLOCK_CIPHER;

        checkIndicatorToChange++;

        // System.out.println("Embed");
        // System.out.println("increase: " + increase);
        return channel1;
    }

    private static int changeChannelTwo(int rgbValue, int channel2) {

        // System.out.println("Blue: " + blue + " BlueNew: " + blueNew + " Result: " +
        // Math.abs(blue - blueNew));
        if (Math.abs(rgbValue - channel2) > LIMIT_BIT_TO_CHANGE) {
            // System.out.println("No Embed");
            return rgbValue;
        }

        increase = increase + NUMBER_BLOCK_CIPHER;

        checkIndicatorToChange = checkIndicatorToChange + 2;

        // System.out.println("Embed");
        // System.out.println("increase: " + increase);
        return channel2;
    }

    // Add cipher to indicator
    private static int changeIndicator(String indicatorBinary) {

        if (indicatorBinary.length() > NUMBER_BLOCK_CIPHER_INDICATOR) {
            // 00
            if (checkIndicatorToChange == 1) {
                // System.out.println("GreenChanged: " + greenBinary.substring(0,
                // NUMBER_BLOCK_CIPHER_INDICATOR) + "00");
                return convertBinaryToDecimal(indicatorBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + "00");
            }

            // 01
            if (checkIndicatorToChange == 2) {
                // System.out.println("GreenChanged: " + greenBinary.substring(0,
                // NUMBER_BLOCK_CIPHER_INDICATOR) + "01");
                return convertBinaryToDecimal(indicatorBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + "01");
            }

            // 10
            if (checkIndicatorToChange == 3) {
                // System.out.println("GreenChanged: " + greenBinary.substring(0,
                // NUMBER_BLOCK_CIPHER_INDICATOR) + "10");
                return convertBinaryToDecimal(indicatorBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + "10");
            }

            // 11
            // System.out.println("GreenChanged: " + greenBinary.substring(0,
            // NUMBER_BLOCK_CIPHER_INDICATOR) + "11");
            return convertBinaryToDecimal(indicatorBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + "11");
        }
        return 0;
    }

    // Embed key k1, key k2 and length cipher to byte reserve to 2bit LSB
    private static void embedK1K2andLengthCipher(String keyK1, String keyK2, int blockLength, int start, int end, BufferedImage image) {

        String binaryKeyK1 = convertTextToBinary(keyK1);
        String binaryKeyK2 = convertTextToBinary(keyK2);

        String blockLengthBinary = convertTextToBinary(String.valueOf(blockLength));
        String textBinaryEnd = convertTextToBinary("=");

        String binaryAll = binaryKeyK1 + binaryKeyK2 + blockLengthBinary + textBinaryEnd;
        int checkOutBinary = 0;

        int WIDTH = image.getWidth();

        for (int height = start; height < end; height++) {
            for (int width = 0; width < WIDTH; width++) {

                int rgbValue = image.getRGB(width, height);
//                increase2++;

                int red = (rgbValue >> 16) & 0xff;
                int green = (rgbValue >> 8) & 0xff;
                int blue = rgbValue & 0xff;

                // convert rgb value to binary
                String redBinary = convertDecimalToBinary(red);
                String greenBinary = convertDecimalToBinary(green);
                String blueBinary = convertDecimalToBinary(blue);

                // Embed 2bit LSB
                if (checkOutBinary + 2 > binaryAll.length()) {
                    return;
                }

                redBinary = redBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + binaryAll.substring(checkOutBinary, checkOutBinary + 2);
                checkOutBinary += 2;

                if (checkOutBinary + 2 > binaryAll.length()) {
                    int redChanged = convertBinaryToDecimal(redBinary);
                    int greenChanged = convertBinaryToDecimal(greenBinary);
                    int blueChanged = convertBinaryToDecimal(blueBinary);

                    rgbValue = (redChanged << 16) | (greenChanged << 8) | blueChanged;

                    image.setRGB(width, height, rgbValue);

                    return;
                }

                greenBinary = greenBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + binaryAll.substring(checkOutBinary, checkOutBinary + 2);
                checkOutBinary += 2;

                if (checkOutBinary + 2 > binaryAll.length()) {
                    int redChanged = convertBinaryToDecimal(redBinary);
                    int greenChanged = convertBinaryToDecimal(greenBinary);
                    int blueChanged = convertBinaryToDecimal(blueBinary);

                    rgbValue = (redChanged << 16) | (greenChanged << 8) | blueChanged;

                    image.setRGB(width, height, rgbValue);

                    return;
                }

                blueBinary = blueBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + binaryAll.substring(checkOutBinary, checkOutBinary + 2);
                checkOutBinary += 2;

                int redChanged = convertBinaryToDecimal(redBinary);
                int greenChanged = convertBinaryToDecimal(greenBinary);
                int blueChanged = convertBinaryToDecimal(blueBinary);

                rgbValue = (redChanged << 16) | (greenChanged << 8) | blueChanged;

                image.setRGB(width, height, rgbValue);

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
