package com.thunderfinesse.thunderskillfetcher.player;

import com.thunderfinesse.thunderskillfetcher.player.data.Player;
import com.thunderfinesse.thunderskillfetcher.player.enums.Mode;
import com.thunderfinesse.thunderskillfetcher.player.html.ModeFetcherThread;

import java.util.concurrent.CountDownLatch;

public class ThunderskillPlayerFetcher {

    private String urlVehicles;
    //https://thunderskill.com/en/stat/%login%/vehicles/%mode-short%

    ThunderskillPlayerFetcher(String urlVehicles){
        this.urlVehicles = urlVehicles;
    }

    public Player fetch(String login){
        Player player = new Player(login);

        CountDownLatch modeLatch = new CountDownLatch(Mode.values().length);

        for (var mode : Mode.values()){
            String url = urlVehicles.replace("%login%",login).replace("%mode-short%",mode.getAbbrev());
            new Thread(new ModeFetcherThread(url, player, mode, modeLatch)).start();
        }

        try {
            modeLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return player;
    }

    public Player fetch(String login, Mode mode){
        Player player = new Player(login);

        String url = urlVehicles.replace("%login%",login).replace("%mode-short%",mode.getAbbrev());
        new ModeFetcherThread(url, player, mode, new CountDownLatch(1)).run();

        return player;
    }

}
