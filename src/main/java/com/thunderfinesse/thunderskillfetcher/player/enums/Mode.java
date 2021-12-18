package com.thunderfinesse.thunderskillfetcher.player.enums;

public enum Mode {
    ARCADE("a"),
    REALISTIC("r"),
    SIMULATION("s");

    String abbrev;

    Mode(String abbrev) {
     this.abbrev = abbrev;
    }

    public String getAbbrev() {
        return abbrev;
    }



}
