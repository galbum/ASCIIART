package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import java.util.*;
import java.util.stream.Stream;

public class Shell {
    private static final int INITIAL_CHARS_IN_ROW = 64;
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final int TWO = 2;
    private static final int ONE = 1;
    private static final int ZERO = 0;
    private static final String CMD_EXIT = "exit";
    private static final String INITIAL_CHARS_RANGE = "0-9";
    private static final String FONT_NAME = "Courier New";
    private static final String OUTPUT_FILENAME = "out.html";
    private static boolean isConsole = false;
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private Set<Character> charSet ;
    private Image img;
    private BrightnessImgCharMatcher charMatcher;
    private AsciiOutput output;

    /**
     * Constructs a Shell object
     * @param img - The image that we are going to convert to Ascii Art
     */
    public Shell(Image img) {
        this.charSet = new HashSet<>(); // Create a HashSet object
        this.img = img;
        this.minCharsInRow = Math.max(ONE, img.getWidth()/img.getHeight());
        this.maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        this.charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        this.charMatcher = new BrightnessImgCharMatcher(this.img, FONT_NAME);
        this.output = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
        addChars(INITIAL_CHARS_RANGE);
    } // end of Shell constructor

    /**
     * Run method for shell
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">>> ");
        String cmd = scanner.nextLine().trim();
        String[] words = cmd.split("\\s+");
        while (!words[ZERO].equals(CMD_EXIT)) {
            if (!words[ZERO].equals("")) {
                String param = "";
                if (words.length > ONE) {
                    param = words[ONE];
                } // end of if
            } // end of if
            switch(words[ZERO]){
                case "chars":
                    showChars();
                    break;
                case "res":
                    resChange(words[ONE]);
                    break;
                case "add":
                    addChars(words[ONE]);
                    break;
                case "remove":
                    removeChars(words[ONE]);
                    break;
                case "console":
                    isConsole = true;
                    break;
                case "render":
                    render();
                    break;
                default:
                    System.out.println("Entered wrong word, please try again");
            } // end of switch
            System.out.print(">>> ");
            cmd = scanner.nextLine().trim();
            words = cmd.split("\\s+");
        } // end of while
    } // end of method run

    /**
        private methods
     */
    // shows the characters that are currently active
    private void showChars() {
        charSet.stream().sorted().forEach(c-> System.out.print(c + " "));
        System.out.println();
    } // end of showChars method

    // parses throw the string and returns the relavent chars
    private static char[] parseCharRange(String param){
        char[] result = new char[TWO];
        if(param.length()==ONE) {
            result[ZERO] = param.charAt(ZERO);
            result[ONE] = param.charAt(ZERO);
        }// end of if param.length == 1
        else if(param.equals("all")) {
            result[ZERO] = ' ';
            result[ONE] = '~';
        } // end of if
        else if(param.equals("space")){
            result[ZERO] = ' ';
            result[ONE] = ' ';
        }// end of if
        else if(param.charAt(ONE) == '-'){
            result[ZERO] = param.charAt(ZERO);
            result[ONE] = param.charAt(TWO);
            if((int)result[ZERO] > (int) result[ONE]) {
                char temp = result[ZERO];
                result[ZERO] = result[ONE];
                result[ONE] = temp;
            } // end of if
        } // end of if
        else
            return null;
        return result;
    } //end of parseCharRange method

    //Add chars to charList
    private void addChars(String s) {
        char[] range = parseCharRange(s);
        if(range != null){
            Stream.iterate(range[ZERO], c -> c <= range[ONE], c -> (char)((int)c+ONE)).forEach(charSet::add);
        } //end of if
    } // end of method addChars

    //Remove chars from charList
    private void removeChars(String s) {
        char[] range = parseCharRange(s);
        if(range != null){
            Stream.iterate(range[ZERO], c -> c <= range[ONE], c -> (char)((int)c+ONE)).forEach(charSet::remove);
        } //end of if
    } // end of method addChars

    //changes the resolution
    private void resChange(String s){
        if(s.equals("up") ){
            if(this.charsInRow * TWO < this.maxCharsInRow) {
                this.charsInRow = this.charsInRow * TWO;
                System.out.println("Width set to " +this.charsInRow);
            } // end of if
            else
                System.out.println("You are using the maximum resolution");
        } // end of if
        else if(s.equals("down") ){
            if(this.charsInRow / TWO > this.minCharsInRow) {
                this.charsInRow = this.charsInRow / TWO;
                System.out.println("Width set to " + this.charsInRow);
            }// end of if
            else
                System.out.println("You are using the minimum resolution");
        } //end of if
        System.out.println("The amount of chars in row is: "+ this.charsInRow);
    } //end of resChange method

    // renders the project
    private void render(){
        Character[] cSet = new Character[this.charSet.size()];
        this.charSet.toArray(cSet);
        char[][] chars = this.charMatcher.chooseChars(this.charsInRow, cSet);
        if(isConsole) {
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < chars[i].length; j++) {
                    System.out.print (chars[i][j] + " ");
                } // end of inner for loop
                System.out.println();
            } // end of outer for loop
            isConsole = false;
        }// end of if
        else
            this.output.output(chars);
    } // end of render method


} // end of class Shell