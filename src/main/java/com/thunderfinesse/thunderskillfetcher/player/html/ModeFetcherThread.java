package com.thunderfinesse.thunderskillfetcher.player.html;

import com.thunderfinesse.thunderskillfetcher.player.data.Player;
import com.thunderfinesse.thunderskillfetcher.player.enums.Mode;
import com.thunderfinesse.thunderskillfetcher.player.enums.VehicleType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * tables containing statistics on thunderskill.com are divide by mode (arcade, realistic, simulation)
 * and inside these divide by types of vehicles (aircraft and ground vehicles)
 */
public class ModeFetcherThread implements Runnable{

    private String thunderskillUrl;
    private Mode mode;
    private CountDownLatch latch;
    private Player player;

    public ModeFetcherThread(String thunderskillUrl, Player player, Mode mode, CountDownLatch latch){
        this.thunderskillUrl = thunderskillUrl;
        this.player = player;
        this.mode = mode;
        this.latch = latch;
    }


    /**
     * fetch tables containing vehicles statistic from {@link <a href="https://thunderskill.com/en/stat/Luigi012/vehicles/a">thunderskil.com</a>}
     * starts 2 threads for each type of vehicles (aircraft and ground vehicles)
     */
    @Override
    public void run() {
        CountDownLatch typeLatch = new CountDownLatch(VehicleType.values().length);
            try {
                Elements tables[] = fetchTables();
                new Thread(new TypeFetcherThread(tables[0], player, mode, VehicleType.Aircraft, typeLatch)).start();
                new Thread(new TypeFetcherThread(tables[1], player, mode, VehicleType.GroundVehicle, typeLatch)).start();
                typeLatch.await();
            } catch (IOException e) {
                System.err.printf("ModeFetcherThread.run() could not fetch table from %s\n\treason: %s\n" , thunderskillUrl, e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
    }

    private Elements[] fetchTables() throws IOException {
        Elements[] tables = new Elements[2];
        InputStream inputStream = new URL(thunderskillUrl).openConnection().getInputStream();
        Document doc = Jsoup.parse(inputStream, "UTF-8",thunderskillUrl);
        inputStream.close();
        tables[0] = doc.getElementsByTag("tbody").first().children(); //table with air vehicles
        tables[1] = doc.getElementsByTag("tbody").last().children(); //table with air vehicles
        return tables;
    }
}
