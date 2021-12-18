package com.thunderfinesse.thunderskillfetcher.player.data;

import com.mongodb.client.model.Updates;
import com.thunderfinesse.thunderskillfetcher.player.enums.Nation;
import com.thunderfinesse.thunderskillfetcher.player.enums.Rank;
import org.bson.Document;
import org.bson.conversions.Bson;

public class Vehicle {
    private String id;
//    private Nation nation;
//    private Rank rank;
    private int battles;
    private int respawns;
    private int deaths;
    private int victories;
    private int defeats;
    private int airKills;
    private int groundKills;
    private int allKills;
    private double winRatio;
    private double kdRatio;

    public Vehicle(String id, int battles, int respawns, int deaths, int victories,
                   int defeats, int airKills, int groundKills) {
        this.id = id;
//        this.nation = nation;
//        this.rank = rank;
        this.battles = battles;
        this.respawns = respawns;
        this.deaths = deaths;
        this.victories = victories;
        this.defeats = defeats;
        this.airKills = airKills;
        this.groundKills = groundKills;
        this.allKills = airKills + groundKills;
        this.winRatio = victories/(double) battles;
        this.kdRatio = allKills/(double) deaths;

    }

    public String getId() {
        return id;
    }

    public Bson toUpdate() {
        return Updates.combine(
                Updates.setOnInsert("_id",id),
                Updates.set("victories",victories),
                Updates.set("defeats",defeats),
                Updates.set("battles",battles),
                Updates.set("respawns",respawns),
                Updates.set("deaths",deaths),
                Updates.set("airKills",airKills),
                Updates.set("groundKills",groundKills),
                Updates.set("allKills",allKills),
                Updates.set("winRatio",winRatio),
                Updates.set("kdRatio",kdRatio));
    }
}
