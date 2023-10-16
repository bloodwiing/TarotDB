package me.bloodwiing.tarotdb.managers;

import me.bloodwiing.tarotdb.data.Deck;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class SettingsManager {
    private final List<Deck> decks = new ArrayList<>();
    private Deck activeDeck;

    private static SettingsManager instance;

    private SettingsManager() {

    }

    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    public void purgeDecks() {
        decks.clear();
    }

    public void loadDecks(InputStream stream) {
        purgeDecks();

        JSONObject object = new JSONObject(new JSONTokener(stream));
        JSONArray array = object.getJSONArray("decks");

        for (Object obj : array) {
            JSONObject deck = (JSONObject) obj;

            String name = deck.getString("name");
            String artist = deck.getString("artist");
            String path = deck.getString("path");
            String format = deck.getString("format");

            decks.add(new Deck(name, artist, path, format));
        }

        activeDeck = decks.get(0);
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }
}