package com.thunderfinesse.thunderskillfetcher.player.enums;

public enum Nation {
    USA,
    Germany,
    USSR,
    Britain,
    Japan,
    China,
    Italy,
    France,
    Sweden,
    Israel;

    /** function to parse nations names used by thunderskill.com */
    public static Nation getNation(String name){
        for (var nation : values()) {
            if (name.contains(nation.toString().toLowerCase())) //if statement should be written better
                return nation;
        }
        throw new IllegalArgumentException("no such nation as " + name);
    }
}
