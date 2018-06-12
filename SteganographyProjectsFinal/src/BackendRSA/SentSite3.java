/**
 *
 */
package BackendRSA;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Minato
 *
 */
public class SentSite3 {

    private static final int LIMIT_BIT_TO_CHANGE = 7;
    private static final int NUMBER_BLOCK_CIPHER = 4;
    private static final int NUMBER_BLOCK_CIPHER_INDICATOR = 6;
    private static int increase = 0;
    private static int checkGreenToChange = 0;
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
    public static void divideImageTo8Blocks(BufferedImage image) {

        int height = image.getHeight();
        int width = image.getWidth();

        int temp = (height - 80) / 8;
        int result = 0;

        for (int i = 0; i < 8; i++) {

            if (i == 1) {
                result += 80;
                result += temp;
                continue;
            }

            if (i == 7) {
                break;
            }

            result += temp;
        }
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

    public static void getValueRGB(BufferedImage image, String binaryString) {
        
        int pixels = 0;

        int height = image.getHeight();
        int width = image.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgbValue = image.getRGB(j, i);

                if (increase >= binaryString.length()) {
                    break;
                }

                // Set new rgb
                int newRGBValue = setNewRGBValue(rgbValue, binaryString);
                image.setRGB(j, i, newRGBValue);
                pixels++;
            }

            if (increase >= binaryString.length()) {
                break;
            }
        }
        System.out.println("increase: " + increase);
        System.out.println("pixels: " + pixels);
        increase = 0;
    }

    private static int setNewRGBValue(int rgbValue, String binaryString) {

        // System.out.println("============= Set New RGB =======================");
        int red = (rgbValue >> 16) & 0xff;
        int green = (rgbValue >> 8) & 0xff;
        int blue = rgbValue & 0xff;

        // convert to binary
        String redBinary = convertDecimalToBinary(red);
        String greenBinary = convertDecimalToBinary(green);
        String blueBinary = convertDecimalToBinary(blue);

        // System.out.println("RedBinary : " + redBinary);
        // System.out.println("GreenBinary: " + greenBinary);
        // System.out.println("BlueBinary : " + blueBinary);
        // System.out.println("============= Red: add CipherToBit ==============");
        int redNew = addCipherToBit(redBinary, binaryString);
        // System.out.println("============= changeRed =========================");
        int redChanged = editRed(red, redNew);

        // System.out.println("============= Blue: add CipherToBit =============");
        int blueNew = addCipherToBit(blueBinary, binaryString);
        // System.out.println("============= changeBlue ========================");
        int blueChanged = editBlue(blue, blueNew);

        // System.out.println("============= changeGreen =======================");
        int greenChanged = editGreen(greenBinary, red, redNew, blue, blueNew);

        // Reset check green to change
        checkGreenToChange = 0;

        rgbValue = (redChanged << 16) | (greenChanged << 8) | blueChanged;

        return rgbValue;
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

    // Add cipher to red or blue
    private static int addCipherToBit(String rgbBinary, String cipherText) {

        if (increase + NUMBER_BLOCK_CIPHER > cipherText.length()) {

            // No embed (270 - 255 = 15 > 7)
            return 270;
        }

        String getCipher = cipherText.substring(increase, increase + NUMBER_BLOCK_CIPHER);

        // System.out.println("Cipher: " + getCipher);
        String rgbChanged = rgbBinary.substring(0, NUMBER_BLOCK_CIPHER) + getCipher;

        // System.out.println("rgbChanged: " + rgbChanged);
        int rgbNew = convertBinaryToDecimal(rgbChanged);

        return rgbNew;
    }

    // Add cipher to indicator
    private static int editGreen(String greenBinary, int red, int redNew, int blue, int blueNew) {

        // 00
        if (checkGreenToChange == 1) {
            // System.out.println("GreenChanged: " + greenBinary.substring(0,
            // NUMBER_BLOCK_CIPHER_INDICATOR) + "00");
            return convertBinaryToDecimal(greenBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + "00");
        }

        // 01
        if (checkGreenToChange == 2) {
            // System.out.println("GreenChanged: " + greenBinary.substring(0,
            // NUMBER_BLOCK_CIPHER_INDICATOR) + "01");
            return convertBinaryToDecimal(greenBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + "01");
        }

        // 10
        if (checkGreenToChange == 3) {
            // System.out.println("GreenChanged: " + greenBinary.substring(0,
            // NUMBER_BLOCK_CIPHER_INDICATOR) + "10");
            return convertBinaryToDecimal(greenBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + "10");
        }

        // 11
        // System.out.println("GreenChanged: " + greenBinary.substring(0,
        // NUMBER_BLOCK_CIPHER_INDICATOR) + "11");
        return convertBinaryToDecimal(greenBinary.substring(0, NUMBER_BLOCK_CIPHER_INDICATOR) + "11");
    }

    // Check RGB to change
    private static int editRed(int red, int redNew) {

        // System.out.println("Red: " + red + " RedNew: " + redNew + " Result: " +
        // Math.abs(red - redNew));
        if (Math.abs(red - redNew) > LIMIT_BIT_TO_CHANGE) {
            // System.out.println("No Embed");
            return red;
        }

        increase = increase + NUMBER_BLOCK_CIPHER;

        checkGreenToChange++;

        // System.out.println("Embed");
        // System.out.println("increase: " + increase);
        return redNew;
    }

    private static int editBlue(int blue, int blueNew) {

        // System.out.println("Blue: " + blue + " BlueNew: " + blueNew + " Result: " +
        // Math.abs(blue - blueNew));
        if (Math.abs(blue - blueNew) > LIMIT_BIT_TO_CHANGE) {
            // System.out.println("No Embed");
            return blue;
        }

        increase = increase + NUMBER_BLOCK_CIPHER;

        checkGreenToChange = checkGreenToChange + 2;

        // System.out.println("Embed");
        // System.out.println("increase: " + increase);
        return blueNew;
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

}
