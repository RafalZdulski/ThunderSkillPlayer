package com.thunderfinesse.thunderskillfetcher.player;

import com.thunderfinesse.thunderskillfetcher.player.data.Player;
import com.thunderfinesse.thunderskillfetcher.player.mongodb.AccessPoint;
import com.thunderfinesse.thunderskillfetcher.player.mongodb.DBWriter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Properties properties = defaultVals();

        String login;
        if (args == null || args[0] == null)
            throw new IllegalArgumentException("login cant be null try \"ThunderSkillPlayer.jar login\" ");
        else
            login = args[0];

        args[0] = null;
        for (var arg : args) {
            if (arg == null) continue;
            //parsing option name
            Pattern namePattern = Pattern.compile("(--\\w+)");
            Matcher m = namePattern.matcher(arg);
            String cmdName = "";
            if (m.find())
                cmdName = m.group();

            //parsing option values
            Pattern valsPattern = Pattern.compile("-\\w+");
            m = valsPattern.matcher(arg.replaceAll("--\\w+",""));

            List<String> cmdVals = new LinkedList<>();
            while (m.find())
                cmdVals.add(m.group(0).substring(1));

            switch (cmdName.toLowerCase()){
                case "--dbname" -> properties.replace("db.thunderskill", cmdVals.get(0));
                case "--dburi" -> properties.replace("db.uri", cmdVals.get(0));
                case "" -> {}
                default -> throw new IllegalArgumentException("no such option as " + cmdName);
            }
        }

        //main program
        AccessPoint accessPoint = new AccessPoint(
                properties.getProperty("db.uri"),
                properties.getProperty("db.thunderskill")
        );

        ThunderskillPlayerFetcher fetcher = new ThunderskillPlayerFetcher(
                properties.getProperty("url.thunderskill.player.vehicles")
        );
        Player player = fetcher.fetch(login);

        DBWriter dbWriter = new DBWriter(
                accessPoint,
                properties.getProperty("db.thunderskill.collection.name")
        );
        dbWriter.update(player);
    }

    private static Properties defaultVals(){
        Properties p = new Properties();
        p.put("url.thunderskill.player","https://thunderskill.com/en/stat/%login%");
        p.put("url.thunderskill.player.vehicles","https://thunderskill.com/en/stat/%login%/vehicles/%mode-short%");
        p.put("db.thunderskill","tf_thunderskill_players");
        p.put("db.thunderskill.collection.name","%login%_%type%_%mode%");
        p.put("db.uri","mongodb://localhost:27017");

        return p;
    }
}
