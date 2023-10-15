package me.bloodwiing.tarotdb.data;

public class MinorTarot extends Tarot {
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
}
