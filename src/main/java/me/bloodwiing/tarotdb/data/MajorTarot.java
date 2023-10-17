package me.bloodwiing.tarotdb.data;

import javafx.scene.control.Label;
import me.bloodwiing.tarotdb.builders.InspectBuilder;
import me.bloodwiing.tarotdb.converters.RomanNumeralConverter;

import static me.bloodwiing.tarotdb.builders.InspectBuilder.createWrappingLabel;

public class MajorTarot extends Tarot {

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

    @Override
    public String getDisplayName() {
        return RomanNumeralConverter.intToRoman(getNumber()) + ". " + getName();
    }

    @Override
    public void buildInfo(InspectBuilder builder) {
        super.buildInfo(builder);

        var archetype = builder.addParagraph("🪆 Archetype");
        archetype.getListItems().add(createWrappingLabel(getArchetype()));

        var hebrew = builder.addParagraph("✡ Hebrew Alphabet");
        hebrew.getListItems().add(createWrappingLabel(getHebrew()));

        var mythical = builder.addParagraph("❂ Mythical/Spiritual");
        mythical.getListItems().add(createWrappingLabel(getMythical()));
    }
}
