package com.thunderfinesse.thunderskillfetcher.player.mongodb;

import com.thunderfinesse.thunderskillfetcher.player.data.Player;

import java.util.concurrent.CountDownLatch;

public class DBWriter {

    private AccessPoint accessPoint;
    private String collectionNameTemplate; //%login%.%type%_%mode%

    public DBWriter(AccessPoint accessPoint, String collectionNameTemplate){
        this.accessPoint = accessPoint;
        this.collectionNameTemplate = collectionNameTemplate;
    }

    public void update(Player player){
        CountDownLatch latch = new CountDownLatch(6);
        String playerCollectionName = collectionNameTemplate.replace("%login%",player.getLogin());

        String airCollectionsName = playerCollectionName.replace("%type%","air");
        new Thread(new DBWriterThread(
                accessPoint.getCollection(airCollectionsName.replace("%mode%","ab")), player.getAir_ab(),latch)
        ).start();
        new Thread(new DBWriterThread(
                accessPoint.getCollection(airCollectionsName.replace("%mode%","rb")), player.getAir_rb(),latch)
        ).start();
        new Thread(new DBWriterThread(
                accessPoint.getCollection(airCollectionsName.replace("%mode%","sb")), player.getAir_sb(),latch)
        ).start();

        String groundCollectionsName = playerCollectionName.replace("%type%","ground");
        new Thread(new DBWriterThread(
                accessPoint.getCollection(groundCollectionsName.replace("%mode%","ab")), player.getGround_ab(),latch)
        ).start();
        new Thread(new DBWriterThread(
                accessPoint.getCollection(groundCollectionsName.replace("%mode%","rb")), player.getGround_rb(),latch)
        ).start();
        new Thread(new DBWriterThread(
                accessPoint.getCollection(groundCollectionsName.replace("%mode%","sb")), player.getGround_sb(),latch)
        ).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
