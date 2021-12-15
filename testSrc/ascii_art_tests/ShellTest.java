//package ex4_tests.ex4_2_tests;
//
//import ascii_art.Driver;
//import ascii_art.Shell;
//import ascii_art.img_to_char.BrightnessImgCharMatcher;
//import ascii_output.HtmlAsciiOutput;
//import ex4_tests.ex4_1_tests.BrightnessImgCharMatcherTest;
//import image.Image;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//
//public class ShellTest extends BrightnessImgCharMatcherTest {
//	private static final String OUTPUT_FILENAME = "out";
//	private static final String PROJECT_NAME = "ex4";
//	private static final String COMPILED_PACKAGES_DIR = TESTS_DIR + "../out/" + PROJECT_NAME + "/";
//
//
//	@Override
//	/* Runs chooseChar and checks that the output is identical to the compare file. */
//	protected long CompareOutputWithPicture(String imageName, int numCharsInRow) throws IOException {
//		System.
//		return compareFilesByLine(
//				Paths.get(TESTS_DIR + COMPARE_DIR + imageName + numCharsInRow + HTML),
//				Paths.get(OUTPUT_FILENAME + HTML)
//		);
//	}
//}
