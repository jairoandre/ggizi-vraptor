package com.bluefin.vraptor;

import com.bluefin.vraptor.business.RiotApiConnector;
import constant.Region;
import dto.League.League;
import dto.League.LeagueEntry;
import main.java.riotapi.RiotApi;

import java.util.List;
import java.util.Map;

/**
 * Created by Jairo on 20/03/2015.
 */
public class RiotApiConnectorTest {

    public static void main(String[] args){
        RiotApiConnector api = new RiotApiConnector();
        try{
            Map<String, List<League>> map = api.getLeaguesByIds(527093l);
            api.changeRegion(Region.BR);
            for(String key : map.keySet()){
                System.out.println(key);
                for(League league : map.get(key)){
                    System.out.println(league.getQueue());
                    System.out.println(league.getName());
                    System.out.println(league.getTier());
                    for(LeagueEntry entry : league.getEntries()){
                        System.out.println(entry.getDivision());
                    }

                }
            }
        }catch (Exception e){

        }
    }
}
