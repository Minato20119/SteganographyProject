/**
 *
 */
package BackendRSA;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Minato
 *
 */
public class ReceiveSite3 {

    private static String BINARY_TEXT = "";
    private static final int NUMBER_BLOK_CIPHER_INDICATOR = 6;
    private static final int NUMBER_BLOK_CIPHER = 4;

    // Convert binary to decimal
    public static int convertBinaryToDecimal(String binary) {

        int decimal = Integer.parseInt(binary, 2);

        return decimal;
    }

    public static BufferedImage inputImage(String pathFileImage) {
        BufferedImage image = null;
        File file = null;
        try {

            file = new File(pathFileImage);
            image = ImageIO.read(file);

        } catch (IOException e) {
            System.out.println("Error input image: " + e);
        }
        return image;
    }

    public static String getValueRGB(BufferedImage image) {

        int height = image.getHeight();
        int width = image.getWidth();

        int rgb = 0;
        String textEncrypted = "";

        for (int i = 0; i < height; i++) {
            
            if (textEncrypted.contains("=")) {
                break;
            }
            for (int j = 0; j < width; j++) {
                rgb = image.getRGB(j, i);

                if (BINARY_TEXT.length() >= 8) {
                    String word = BINARY_TEXT.substring(BINARY_TEXT.length() - 8, BINARY_TEXT.length());
                    int charCode = convertBinaryToDecimal(word);
                    textEncrypted += new Character((char) charCode).toString();
                }

                getCipher(rgb);

                if (textEncrypted.contains("=")) {
                    break;
                }
            }
        }

        return BINARY_TEXT;
    }

    private static void getCipher(int rgb) {

        int red = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue = rgb & 0xff;

        // convert to binary
        String redBinary = convertDecimalToBinary(red);
        String greenBinary = convertDecimalToBinary(green);
        String blueBinary = convertDecimalToBinary(blue);

        // System.out.println("Red: " + redBinary);
        // System.out.println("Green: " + greenBinary);
        // System.out.println("Blue: " + blueBinary);
        // Check indicator with binary "00"
        if (greenBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR, greenBinary.length()).equals("00")) {
            BINARY_TEXT = BINARY_TEXT + (redBinary.substring(NUMBER_BLOK_CIPHER, redBinary.length()));
            // System.out.println("Append red: " + redBinary.substring(NUMBER_BLOK_CIPHER,
            // redBinary.length()));
        }

        // Check indicator with binary "01"
        if (greenBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR, greenBinary.length()).equals("01")) {
            BINARY_TEXT = BINARY_TEXT + (blueBinary.substring(NUMBER_BLOK_CIPHER, blueBinary.length()));
            // System.out.println("Append blue: " + blueBinary.substring(NUMBER_BLOK_CIPHER,
            // blueBinary.length()));
        }

        // Check indicator with binary "10"
        if (greenBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR, greenBinary.length()).equals("10")) {

            BINARY_TEXT = BINARY_TEXT + (redBinary.substring(NUMBER_BLOK_CIPHER, redBinary.length()));
            BINARY_TEXT = BINARY_TEXT + (blueBinary.substring(NUMBER_BLOK_CIPHER, blueBinary.length()));
            // System.out.println("Append red and blue: " +
            // redBinary.substring(NUMBER_BLOK_CIPHER, redBinary.length())
            // + " " + blueBinary.substring(NUMBER_BLOK_CIPHER, blueBinary.length()));
        }

        // Check indicator with binary "11"
        if (greenBinary.substring(NUMBER_BLOK_CIPHER_INDICATOR, greenBinary.length()).equals("11")) {
            // System.out.println("No append.");
        }
    }

    // Get binary
    private static String convertDecimalToBinary(int value) {

        String binaryString = "0000000" + Integer.toBinaryString(value);
        binaryString = binaryString.substring(binaryString.length() - 8);

        return binaryString;
    }
}
