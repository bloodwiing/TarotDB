package me.bloodwiing.tarotdb.data;

import javafx.scene.control.Label;
import me.bloodwiing.tarotdb.builders.InspectBuilder;
import me.bloodwiing.tarotdb.controllers.InspectRowController;

import java.util.List;

import static me.bloodwiing.tarotdb.builders.InspectBuilder.createWrappingLabel;

public class MinorTarot extends Tarot {

    public static final String[] courts = new String[] { "Page", "Knight", "Queen", "King" };

    private final int number;
    private final Suit suit;

    private String astrology;
    private String affirmation;

    public MinorTarot(String name, String image, int number, Suit suit) {
        super(name, image);
        this.number = number;
        this.suit = suit;
    }

    public int getNumber() {
        return number;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getAstrology() {
        return astrology;
    }

    public void setAstrology(String astrology) {
        this.astrology = astrology;
    }

    public String getAffirmation() {
        return affirmation;
    }

    public void setAffirmation(String affirmation) {
        this.affirmation = affirmation;
    }

    @Override
    public String getListHead() {
        return Integer.toString(getNumber());
    }

    @Override
    public String getListLabel() {
        return getName();
    }

    @Override
    public void buildInfo(InspectBuilder builder) {
        super.buildInfo(builder);

        if (getAstrology() != null) {
            var astrology = builder.addParagraph("☄ Astrology");
            astrology.getListItems().add(createWrappingLabel(getAstrology()));
        }

        var affirmation = builder.addParagraph("⚖ Affirmation");
        affirmation.getListItems().add(createWrappingLabel(getAffirmation()));
    }
}
