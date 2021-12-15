package ascii_art.img_to_char;

import image.Image;
import java.awt.*;
import java.util.HashMap;

public class BrightnessImgCharMatcher {
    private final HashMap<Image, Double> cache = new HashMap<>();
    private final Image img;
    private final String font;

    /**
     * Constructs a BrightnessImgCharMatcher object.
     * @param img The image which is going to be used.
     * @param font The font which is going to be used.
     */
    public BrightnessImgCharMatcher(Image img, String font){
        this.img = img;
        this.font = font;
    } //end of constructor

    /**
     * A method which returns A 2D array of Ascii letters.
     * @param numCharsInRow Number of charachters in a row
     * @param charSet Array of charachters which are going to be used.
     * @return A 2D array where in every cell is the corespondent Ascii letter.
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet){
        char[] cSet = new char[charSet.length];
        for (int i = 0; i < charSet.length; i++) {
            cSet[i] = charSet[i].charValue();
        } //end of for loop
        int pixels = this.img.getWidth() / numCharsInRow;
        int amountOfSquaresInRow = pixels/numCharsInRow ;
        int amountOfSquares = (int) Math.pow(amountOfSquaresInRow,2);
        return convertImageToAscii(numCharsInRow, cSet);
    } // end of choosrChars method

    /**
     * private methods
     */
    private char[][] convertImageToAscii(int numCharsInRow, char[] charSet){
        float[] brightnessToAscii = brightnessToAsciiConverter(charSet);
        float[] newCharBrightness = linearStretch(brightnessToAscii);
        int pixels = this.img.getWidth() / numCharsInRow;
        char[][] asciiArt = new char[(int) (this.img.getHeight()/pixels)][(int) (this.img.getWidth()/pixels)];
        this.img.squareSubImagesOfSize(pixels);
        int i = 0;
        double averageGray;
        for(Image subImage : this.img.squareSubImagesOfSize(pixels)) {
            int row = i / numCharsInRow;
            int col = i % numCharsInRow;
            i++;
            averageGray = averageGrayColour(subImage);
            if(!cache.containsValue(averageGray)) {
                cache.put(subImage, averageGray);
            } //end of if
            int index = closestIndex(averageGray, newCharBrightness);
            asciiArt[row][col] = charSet[index];

        } //end of outer for
        return asciiArt;
    } // end of convertImageToAscii method

    private int closestIndex(double averageGray, float[] newCharBrightness){
        double minDiff = Math.abs(newCharBrightness[0] - averageGray);
        int index = 0;
        for (int i = 1; i < newCharBrightness.length; i++) {
            if(Math.abs(newCharBrightness[i] - averageGray) < minDiff ) {
                minDiff = Math.abs(newCharBrightness[i] - averageGray);
                index = i;
            }//end of if
        } //end of inner for
        return index;
    } // end of private method closestIndex

    private float[] brightnessToAsciiConverter(char[] charSet){
        float[] brightnessCharSet = new float[charSet.length];
        for (int i = 0; i < brightnessCharSet.length ; i++) {
            boolean[][] charTable = CharRenderer.getImg(charSet[i], 16, this.font);
            int count = 0;
            int sizeOfTable = 0;
            for (int j = 0; j < charTable.length; j++) {
                for (int k = 0; k < charTable[j].length; k++) {
                    if(charTable[j][k])
                        count++;
                    sizeOfTable++;
                } // end of inner loop
            } // end of med for loop
            brightnessCharSet[i]= (float)count/sizeOfTable; //deviding 2 int numbers
            //System.out.println("the char is: "+ charSet[i]+ " and the brightness is: "+ brightnessCharSet[i]);
        } //end of outer for loop
        //Arrays.sort(brightnessCharSet);
        return brightnessCharSet;
    } // end of BrightnessImgCharMatcher method

    private float[] linearStretch(float[] brightnessCharSet){
        float maxBrightness = brightnessCharSet[0], minBrightness = brightnessCharSet[0];
        for (int i = 1; i < brightnessCharSet.length; i++) {
            if(brightnessCharSet[i] > maxBrightness)
                maxBrightness = brightnessCharSet[i];
            else if(brightnessCharSet[i] < minBrightness)
                minBrightness = brightnessCharSet[i];
        } // end of for loop
        float[] newCharBrightness = new float[brightnessCharSet.length];
        for (int i = 0; i < brightnessCharSet.length; i++) {
            newCharBrightness[i] = (brightnessCharSet[i] - minBrightness) /(maxBrightness - minBrightness);
            //System.out.println("the new brightness is: "+ newCharBrightness[i]);
        } // end of for loop
        return newCharBrightness;
    } //end of LinearStretch method

    private double averageGrayColour(Image imag) {
        double sum = 0 ,amountOfPixels = 0 ;
        for (Color pixel : imag.pixels()) {
            amountOfPixels++;
            sum += (pixel.getRed() * 0.2126 + pixel.getGreen() * 0.7152 + pixel.getBlue() * 0.0722)/255;
        } //end of for
        return sum/amountOfPixels;
    } //end of method averageGrayColour

} //end of class BrightnessImgCharMatcher