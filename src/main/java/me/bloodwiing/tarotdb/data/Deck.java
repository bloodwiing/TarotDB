package me.bloodwiing.tarotdb.data;

import me.bloodwiing.tarotdb.Program;

import java.io.InputStream;

public record Deck(String name, String artist, String path, String format) {
    public InputStream getCard(String name) {
        return Program.class.getResourceAsStream(path + name + "." + format);
    }

    public InputStream getDefaultCard() {
        return getCard("m0");
    }

    public InputStream getBackCard() {
        return getCard("b0");
    }
}
