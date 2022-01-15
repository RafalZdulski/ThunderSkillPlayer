package com.thunderfinesse.thunderskillfetcher.player.html;

import com.thunderfinesse.thunderskillfetcher.player.data.Vehicle;
import com.thunderfinesse.thunderskillfetcher.player.enums.Nation;
import com.thunderfinesse.thunderskillfetcher.player.enums.Rank;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

    /**
     * parse given element into Vehicle.class
     * @param row org.jsoup.nodes.Element containing row from table with vehicle stats from {@link <a href="https://thunderskill.com/en/stat/Luigi012/vehicles/a">thunderskill tables</a>}
     * @return stats of given vehicle
     * @throws IllegalStateException when amount of battles in statistics equals 0 (player did not play certain vehicle in that mode)
     */
    public Vehicle parseVehicle(Element row) throws IllegalStateException {
        String id = row.getElementsByAttribute("href").attr("href");
        id = id.substring(id.lastIndexOf("/")+1);

//        Element rankElement = row.getElementsByAttribute("data-sort").get(0);
//        Element nameElement = row.getElementsByAttribute("data-sort").get(1).child(1);
//        Element nationElement = row.getElementsByAttribute("data-sort").get(1).child(0);

        Elements details = row.getElementsByClass("params").first().children();


        Element battlesElement = (Element) details.stream().filter(c -> c.toString().contains("Battles")).toArray()[0];
        int battles = Integer.parseInt(battlesElement.childNode(1).childNode(0).childNode(0).toString());

        if(battles == 0){
            throw new IllegalStateException("zero battles for " + id);
        }

        Element respawnsElement = (Element) details.stream().filter(c -> c.toString().contains("Respawns")).toArray()[0];
        Element victoriesElement = (Element) details.stream().filter(c -> c.toString().contains("Victories")).toArray()[0];
        Element defeatsElement = (Element) details.stream().filter(c -> c.toString().contains("Defeats")).toArray()[0];
        Element deathsElement = (Element) details.stream().filter(c -> c.toString().contains("Deaths")).toArray()[0];
        Element airKillsElement = (Element) details.stream().filter(c -> c.toString().contains("Overall air frags")).toArray()[0];
        Element groundKillsElement = (Element) details.stream().filter(c -> c.toString().contains("Overall ground frags")).toArray()[0];

//        String name = nameElement.childNode(0).toString().replace("&nbsp;"," ");
//        Nation nation = Nation.getNation(nationElement.attr("class"));
//        Rank rank = Rank.getRank(rankElement.childNode(0).toString());
        int respawns = Integer.parseInt(respawnsElement.childNode(1).childNode(0).childNode(0).toString());
        int victories = Integer.parseInt(victoriesElement.childNode(1).childNode(0).childNode(0).toString());
        int defeats = Integer.parseInt(defeatsElement.childNode(1).childNode(0).childNode(0).toString());
        int deaths = Integer.parseInt(deathsElement.childNode(1).childNode(0).childNode(0).toString());
        int airKills = Integer.parseInt(airKillsElement.childNode(1).childNode(0).childNode(0).toString());
        int groundKills = Integer.parseInt(groundKillsElement.childNode(1).childNode(0).childNode(0).toString());

        return new Vehicle(id, battles, respawns, deaths,
                victories, defeats, airKills, groundKills);
        }
}
