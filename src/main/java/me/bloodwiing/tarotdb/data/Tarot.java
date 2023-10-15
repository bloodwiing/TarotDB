package me.bloodwiing.tarotdb.data;

import javafx.scene.image.Image;
import me.bloodwiing.tarotdb.Program;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Tarot {
    private final String name;
    private final String image;

    private final List<String> fortuneTellings = new ArrayList<>();
    private final List<String> lightMeanings = new ArrayList<>();
    private final List<String> shadowMeanings = new ArrayList<>();

    private String numerology;
    private String elemental;

    public Tarot(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Image getImageResource() {
        try (InputStream stream = Program.class.getResourceAsStream("rws/" + getImage())) {
            if (stream == null)
                return null;

            return new Image(stream);

        } catch (IOException e) {
            return null;
        }
    }

    public List<String> getFortuneTellings() {
        return fortuneTellings;
    }

    public List<String> getLightMeanings() {
        return lightMeanings;
    }

    public List<String> getShadowMeanings() {
        return shadowMeanings;
    }

    public String getNumerology() {
        return numerology;
    }

    public void setNumerology(String numerology) {
        this.numerology = numerology;
    }

    public String getElemental() {
        return elemental;
    }

    public void setElemental(String elemental) {
        this.elemental = elemental;
    }

    public abstract String getListHead();
    public abstract String getListLabel();
}
