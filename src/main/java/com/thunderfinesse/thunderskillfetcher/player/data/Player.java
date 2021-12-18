package com.thunderfinesse.thunderskillfetcher.player.data;

import java.util.LinkedList;
import java.util.List;

public class Player {

    private final String login;

    private List<Vehicle> air_ab;
    private List<Vehicle> air_rb;
    private List<Vehicle> air_sb;
    private List<Vehicle> ground_ab;
    private List<Vehicle> ground_rb;
    private List<Vehicle> ground_sb;

    public Player(String login){
        this.login = login;
        air_ab = new LinkedList<>();
        air_rb = new LinkedList<>();
        air_sb = new LinkedList<>();
        ground_ab = new LinkedList<>();
        ground_rb = new LinkedList<>();
        ground_sb = new LinkedList<>();
    }

    public String getLogin() {
        return login;
    }

    public List<Vehicle> getAir_ab() {
        return air_ab;
    }
    public List<Vehicle> getAir_rb() {
        return air_rb;
    }
    public List<Vehicle> getAir_sb() {
        return air_sb;
    }
    public List<Vehicle> getGround_ab() {
        return ground_ab;
    }
    public List<Vehicle> getGround_rb() {
        return ground_rb;
    }
    public List<Vehicle> getGround_sb() {
        return ground_sb;
    }

    public void setAir_ab(List<Vehicle> air_ab) {
        this.air_ab = air_ab;
    }
    public void setAir_rb(List<Vehicle> air_rb) {
        this.air_rb = air_rb;
    }
    public void setAir_sb(List<Vehicle> air_sb) {
        this.air_sb = air_sb;
    }
    public void setGround_ab(List<Vehicle> ground_ab) {
        this.ground_ab = ground_ab;
    }
    public void setGround_rb(List<Vehicle> ground_rb) {
        this.ground_rb = ground_rb;
    }
    public void setGround_sb(List<Vehicle> ground_sb) {
        this.ground_sb = ground_sb;
    }

}
