package me.bloodwiing.tarotdb.managers;

import javafx.scene.paint.Color;
import me.bloodwiing.tarotdb.data.Deck;
import me.bloodwiing.tarotdb.listeners.SettingUpdateListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class SettingsManager {
    private final List<Deck> decks = new ArrayList<>();
    private Deck activeDeck;
    private Color accentColor = Color.web("7b4fcf");

    private final String version = "v1.4";

    private final Collection<WeakReference<SettingUpdateListener>> settingUpdateListeners = ConcurrentHashMap.newKeySet();

    private static final SettingsManager instance = new SettingsManager();

    private SettingsManager() {

    }

    public static SettingsManager getInstance() {
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

    public Color getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(Color accentColor) {
        this.accentColor = accentColor;
    }

    public String getVersion() {
        return version;
    }

    public void addSettingUpdateListener(SettingUpdateListener listener) {
        settingUpdateListeners.add(new WeakReference<>(listener));
    }

    public void removeSettingUpdateListener(SettingUpdateListener listener) {
        settingUpdateListeners.remove(new WeakReference<>(listener));
    }

    public void notifySettingUpdateListeners() {
        for (WeakReference<SettingUpdateListener> listener : settingUpdateListeners) {
            if (listener.get() != null) {
                Objects.requireNonNull(listener.get()).settingUpdate();
            }
        }
    }
}
