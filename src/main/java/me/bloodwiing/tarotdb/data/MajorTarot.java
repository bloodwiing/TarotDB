package me.bloodwiing.tarotdb.data;

import me.bloodwiing.tarotdb.converters.RomanNumeralConverter;

public class MajorTarot extends Tarot {

    private static final String[] numerals = new String[] {
            "0",
            "I", "II", "III",
            "IV",
            "V", "VI", "VII", "VIII",
            "IX",
            "X",
    };

    private final int number;

    private String archetype;
    private String hebrew;
    private String mythical;

    public MajorTarot(String name, String image, int number) {
        super(name, image);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public String getHebrew() {
        return hebrew;
    }

    public void setHebrew(String hebrew) {
        this.hebrew = hebrew;
    }

    public String getMythical() {
        return mythical;
    }

    public void setMythical(String mythical) {
        this.mythical = mythical;
    }

    @Override
    public String getListHead() {
        return RomanNumeralConverter.intToRoman(getNumber());
    }

    @Override
    public String getListLabel() {
        return getName();
    }
}
