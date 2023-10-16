package me.bloodwiing.tarotdb.data;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import me.bloodwiing.tarotdb.Program;
import me.bloodwiing.tarotdb.builders.InspectBuilder;
import me.bloodwiing.tarotdb.managers.SettingsManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static me.bloodwiing.tarotdb.builders.InspectBuilder.createWrappingLabel;

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
        return new Image(SettingsManager.getInstance().getActiveDeck().getCard(getImage()));
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

    public String getDisplayName() {
        return getName();
    }

    public void buildInfo(InspectBuilder builder) {
        if (getNumerology() != null) {
            var numerology = builder.addParagraph("🔢 Numerology");
            numerology.getListItems().add(createWrappingLabel(getNumerology()));
        }

        if (getElemental() != null) {
            var elemental = builder.addParagraph("🌿 Elemental");
            elemental.getListItems().add(createWrappingLabel(getElemental()));
        }
    }
}
