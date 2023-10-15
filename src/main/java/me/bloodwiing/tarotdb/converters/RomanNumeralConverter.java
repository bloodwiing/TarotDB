package me.bloodwiing.tarotdb.converters;

public final class RomanNumeralConverter {
    private final static String[] letters = {
            "M",    "CM",   "D",    "CD",   "C",    "XC",
            "L",    "XL",   "X",    "IX",   "V",    "IV",   "I"
    };

    private final static int[] numbers = {
            1000,   900,    500,    400,    100,    90,
            50,     40,     10,     9,      5,      4,      1
    };

    public static String intToRoman(int number) {
        if (number == 0) {
            return "0";
        }

        int index = 0;
        StringBuilder result = new StringBuilder();

        while (index < letters.length && number > 0) {
            if (number >= numbers[index]) {
                result.append(letters[index]);
                number -= numbers[index];
            } else {
                ++index;
            }
        }

        return result.toString();
    }
}
