package com.bluefin.vraptor.business;

import constant.Region;
import constant.staticdata.ChampData;
import constant.staticdata.SpellData;
import dto.Game.Game;
import dto.Game.RecentGames;
import dto.League.League;
import dto.Static.Champion;
import dto.Static.ChampionList;
import dto.Static.SummonerSpell;
import dto.Static.SummonerSpellList;
import dto.Summoner.Summoner;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jairo on 18/03/2015.
 */
@SessionScoped
public class RiotApiConnector implements Serializable{

    private final String _API_KEY = "bfea1361-9fff-45f8-a8c0-1ff8025da116";

    private final RiotApi api = new RiotApi(_API_KEY, Region.BR);

    private ChampionList championList;

    private SummonerSpellList spellList;

    private String version;

    private Map<Integer, String> championsImageMap;

    private Map<Integer, String> summonerSpellsImageMap;

    public RiotApiConnector(){
        try {
            this.version = api.getDataVersions().get(0);
            this.championList = api.getDataChampionList(null, null, false, ChampData.ALL);
            this.spellList = api.getDataSummonerSpellList(null, null, false, SpellData.ALL);
            fillChampionsImageMap();
            fillSpellsImageMap();
        }catch (RiotApiException re){
            this.version = "5.2.2";
        }
    }

    public void fillChampionsImageMap(){
        this.championsImageMap = new HashMap<>();
        String baseUrl = "http://ddragon.leagueoflegends.com/cdn/" + version + "/img/champion/";
        for(Champion championData : this.championList.getData().values()){
            this.championsImageMap.put(championData.getId(), baseUrl + championData.getImage().getFull());
        }
    }

    public void fillSpellsImageMap(){
        this.summonerSpellsImageMap = new HashMap<>();
        String baseUrl = "http://ddragon.leagueoflegends.com/cdn/" + version + "/img/spell/";
        for(SummonerSpell summonerSpell : this.spellList.getData().values()){
            this.summonerSpellsImageMap.put(summonerSpell.getId(), baseUrl + summonerSpell.getImage().getFull());
        }
    }

    public void changeRegion(Region region){
        api.setRegion(region);
    }

    public void changeLocale(String locale){
    }


    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Summoner searchByName(String name) throws Exception{

        Map<String, Summoner> map = api.getSummonerByName(Region.BR, name);

        if(!map.isEmpty()){
            return map.get(name);
        }

        return null;

    }

    /**
     *
     * @return
     */
    public ChampionList getChampionList(){
        return championList;
    }

    /**
     *
     * @return
     */
    public Map<Integer, String> getChampionsImageMap(){
        return championsImageMap;
    }

    /**
     *
     * @return
     */
    public String getVersion(){
        return version;
    }

    /**
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Summoner getSummonerByName(String name) throws Exception{
        Map<String, Summoner> map = api.getSummonerByName(name);
        if(map.isEmpty()){
            return null;
        }else{
            return map.get(name.toLowerCase());
        }
    }

    public Set<Game> getRecentGamesBySummonerId(long id) throws Exception{
        RecentGames recentGames = api.getRecentGames(id);
        if(recentGames != null && recentGames.getGames() != null){
            return recentGames.getGames();
        }else{
            return null;
        }
    }

    public Map<String, List<League>> getLeaguesByIds(long... ids) {
        try{
            Map<String, List<League>> leagues = api.getLeagueBySummoners(ids);
            return leagues;
        }catch (RiotApiException re){
            return new HashMap<>();
        }
    }

    private long[] commaSeparatedStringToLongArray(String ids){
        String[] idsArray = ids.split(",");
        long[] idsLongArray = new long[idsArray.length];

        for(int i = 0; i < idsArray.length ; i++){
            idsLongArray[i] = Long.valueOf(idsArray[i]);
        }
        return idsLongArray;
    }

    /**
     *
     * @param ids
     * @return
     * @throws Exception
     */
    public Map<String, Summoner> getSummonersByIds(String ids) throws Exception{
        return api.getSummonersById(commaSeparatedStringToLongArray(ids));
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public Map<Integer, String> getSummonerSpellsImageMap() {
        return summonerSpellsImageMap;
    }
}
