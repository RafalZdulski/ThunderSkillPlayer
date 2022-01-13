package com.thunderfinesse.thunderskillfetcher.player.html;

import com.thunderfinesse.thunderskillfetcher.player.data.Player;
import com.thunderfinesse.thunderskillfetcher.player.data.Vehicle;
import com.thunderfinesse.thunderskillfetcher.player.enums.Mode;
import com.thunderfinesse.thunderskillfetcher.player.enums.Nation;
import com.thunderfinesse.thunderskillfetcher.player.enums.Rank;
import com.thunderfinesse.thunderskillfetcher.player.enums.VehicleType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TypeFetcherThread implements Runnable{

    private Elements table;
    private Player player;
    private Mode mode;
    private VehicleType type;
    private CountDownLatch latch;

    public TypeFetcherThread(Elements table, Player player, Mode mode, VehicleType type, CountDownLatch latch) {
        this.table = table;
        this.player = player;
        this.mode = mode;
        this.type = type;
        this.latch = latch;

    }

    @Override
    public void run() {
        Parser parser = new Parser();
        List<Vehicle> list = new LinkedList<>();
        for (Element row : table) {
            try {
                list.add(parser.parseVehicle(row));
            } catch (Exception e) {}
        }

        switch (mode){
            case ARCADE :
                if ((type == VehicleType.GroundVehicle)) {
                    player.setGround_ab(list);
                } else {
                    player.setAir_ab(list);
                }
                break;
            case REALISTIC :
                if ((type == VehicleType.GroundVehicle)) {
                    player.setGround_rb(list);
                } else {
                    player.setAir_rb(list);
                }
                break;
            case SIMULATION :
                if ((type == VehicleType.GroundVehicle)) {
                    player.setGround_sb(list);
                } else {
                    player.setAir_sb(list);
                }
                break;
            default : throw new IllegalStateException("no such mode as " + mode);
        }
        latch.countDown();
    }

}
