package com.thunderfinesse.thunderskillfetcher.player.enums;

public enum Mode {
    ARCADE("a"),
    REALISTIC("r"),
    SIMULATION("s");

    /** depends on the mode designation on thunderskill.com */
    String abbrev;

    Mode(String abbrev) {
     this.abbrev = abbrev;
    }

    public String getAbbrev() {
        return abbrev;
    }



}
