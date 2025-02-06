package helper;

public class Helper {
    /**
     * @param input
     * @return Converts a character array of raw characters into an array of printable characters.
     */
    public static char[] convertToPrintableChars(char[] input) {
        char[] converted = new char[input.length];

        for (int i = 0; i < input.length; i++) {
            converted[i] = (char) (input[i] + '0'); // Convert raw number to character
        }

        return converted;
    }
}
