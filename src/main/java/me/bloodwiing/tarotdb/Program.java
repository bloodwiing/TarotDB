package me.bloodwiing.tarotdb;

import javafx.application.Application;
import me.bloodwiing.tarotdb.managers.TarotManager;

public class Program {
    public static void main(String[] args) {
        TarotManager.getInstance().loadData(Program.class.getResourceAsStream("tarot-images.json"));
        Application.launch(TarotDB.class, args);
    }
}
