package me.bloodwiing.tarotdb.managers;

import me.bloodwiing.tarotdb.data.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.*;

public final class TarotManager {
    private final Map<String, Suit> suits = new HashMap<>();
    private final List<Tarot> cards = new ArrayList<>();

    private final Set<String> keywords = new HashSet<>();

    private static TarotManager instance;

    private TarotManager() {

    }

    public static TarotManager getInstance() {
        if (instance == null) {
            instance = new TarotManager();
        }
        return instance;
    }

    public void purgeData() {
        suits.clear();
    }

    public void loadData(InputStream stream) {
        purgeData();

        JSONObject data = new JSONObject(new JSONTokener(stream));
        JSONArray cards = data.getJSONArray("cards");

        for (Object o : cards) {
            JSONObject card = (JSONObject) o;

            String arcana = card.getString("arcana");
            boolean majorArcana = arcana.equalsIgnoreCase("Major Arcana");

            if (majorArcana) {
                loadMajorArcana(card);
            } else {
                loadMinorArcana(card);
            }
        }
    }

    private void loadMajorArcana(JSONObject data) {
        String name = data.getString("name");
        String image = data.getString("img");
        int number = Integer.parseInt(data.getString("number"));

        MajorTarot tarot = new MajorTarot(name, image, number);

        tarot.setArchetype(data.getString("Archetype"));
        tarot.setHebrew(data.getString("Hebrew Alphabet"));
        tarot.setMythical(data.getString("Mythical/Spiritual"));

        loadExtraDetails(tarot, data);

        cards.add(tarot);
    }

    private void loadMinorArcana(JSONObject data) {
        String suitName = data.getString("suit");
        if (!suits.containsKey(suitName)) {
            suits.put(suitName, new Suit(suitName));
        }
        Suit suit = suits.get(suitName);

        String name = data.getString("name");
        String image = data.getString("img");
        int number = Integer.parseInt(data.getString("number"));

        MinorTarot tarot = new MinorTarot(name, image, number, suit);

        tarot.setAffirmation(data.getString("Affirmation"));

        if (data.has("Astrology")) {
            tarot.setAstrology(data.getString("Astrology"));
        }

        loadExtraDetails(tarot, data);

        cards.add(tarot);
    }

    private void loadExtraDetails(Tarot tarot, JSONObject data) {
        if (data.has("Numerology")) {
            tarot.setNumerology(data.getString("Numerology"));
        }

        if (data.has("Elemental")) {
            tarot.setElemental(data.getString("Elemental"));
        }

        for (Object o : data.getJSONArray("fortune_telling")) {
            tarot.getFortuneTellings().add((String)o);
        }

        for (Object o : data.getJSONObject("meanings").getJSONArray("light")) {
            tarot.getLightMeanings().add((String)o);
        }

        for (Object o : data.getJSONObject("meanings").getJSONArray("shadow")) {
            tarot.getShadowMeanings().add((String)o);
        }

        for (Object o : data.getJSONArray("keywords")) {
            tarot.getKeywords().add((String)o);
        }

        for (Object o : data.getJSONArray("Questions to Ask")) {
            tarot.getQuestions().add((String)o);
        }

        getKeywords().addAll(tarot.getKeywords());
    }

    public List<Tarot> getCards() {
        return cards;
    }

    public Set<String> getKeywords() {
        return keywords;
    }
}
