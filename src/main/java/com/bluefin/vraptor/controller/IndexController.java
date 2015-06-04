package com.bluefin.vraptor.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.StringUtils;
import br.com.caelum.vraptor.view.Results;
import com.bluefin.vraptor.business.GgiziTransformer;
import com.bluefin.vraptor.business.RiotApiConnector;
import dto.League.League;
import dto.Summoner.Summoner;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Inject
    private RiotApiConnector riotApiConnector;

    @Inject
    private Result result;

    public IndexController() {
    }

    @Path("/")
    public String index() throws Exception {
        result.include("version", riotApiConnector.getVersion());
        return "You are ready to use VRaptor";
    }

    @Post("/api/searchByName")
    public void searchByName(String name) throws Exception {
        result.include("summoner", riotApiConnector.getSummonerByName(name));
        result.forwardTo(this).index();
    }

    @Path("api/championList")
    public void championList() throws Exception {
        result.use(Results.json()).withoutRoot().from(riotApiConnector.getChampionList().getData()).recursive().serialize();
    }

    @Path("api/championsSquareMap")
    public void championsSquareMap() throws Exception {
        result.use(Results.json()).withoutRoot().from(riotApiConnector.getChampionsImageMap()).serialize();
    }

    @Path("api/currentVersion")
    public void currentVersion() throws Exception {
        result.use(Results.json()).withoutRoot().from(riotApiConnector.getVersion()).serialize();

    }

    private String normalizeString(String param){
        String normalized = param.replaceAll("\\s","");
        return normalized;
    }

    @Path("api/summonerByName/{name}")
    public void summonerByName(String name) throws Exception {
        Summoner summoner = riotApiConnector.getSummonerByName(normalizeString(name));
        if(summoner != null) {
            result.use(Results.json()).withoutRoot().from(summoner).serialize();
        }else{
           result.use(Results.http()).sendError(404);
        }
    }

    @Path("api/summonerTierInfo/{id}")
    public void summonerTierInfo(String id) throws Exception {
        if (id != null && !id.isEmpty()) {
            Map<String, List<League>> mapLeague = riotApiConnector.getLeaguesByIds(Long.valueOf(id));
            GgiziTransformer transformer = new GgiziTransformer();
            Map<String, String> tMap = transformer.transformLeagueInfo(mapLeague, id);
            result.use(Results.json()).withoutRoot().from(tMap).serialize();
        } else {
            result.nothing();
        }
    }


    private final String[] gameTypes = {"NONE", "NORMAL", "BOT", "RANKED_SOLO_5x5", "RANKED_PREMADE_3x3", "RANKED_PREMADE_5x5", "ODIN_UNRANKED", "RANKED_TEAM_3x3", "RANKED_TEAM_5x5", "NORMAL_3x3", "BOT_3x3", "CAP_5x5", "ARAM_UNRANKED_5x5", "ONEFORALL_5x5", "FIRSTBLOOD_1x1", "FIRSTBLOOD_2x2", "SR_6x6", "URF", "URF_BOT", "NIGHTMARE_BOT", "ASCENSION", "HEXAKILL", "KING_PORO", "COUNTER_PICK"};

    @Path("api/gameTypeLabels")
    public void gameTypeLabels() {
        Map<String, String> map = new HashMap<>();
        for (String key : gameTypes) {
            String label = "";
                    switch (key){
                        case "NONE":
                            label = "Nenhum";
                            break;
                        case "NORMAL":
                            label = "Normal";
                            break;
                        case "BOT":
                            label = "Bot";
                            break;
                        case "RANKED_SOLO_5x5":
                            label = "Ranqueada Solo";
                            break;
                        case "RANKED_PREMADE_3x3":
                            label= "Ranqueada Premade 3x3";
                            break;
                        case "RANKED_PREMADE_5x5":
                            label = "Ranqueada Premade 5x5";
                            break;
                        case "ODIN_UNRANKED":
                            label = "Odin";
                            break;
                        case "RANKED_TEAM_3x3":
                            label = "Ranqueada Time 3x3";
                            break;
                        case "RANKED_TEAM_5x5":
                            label = "Ranqueada Time 5x5";
                            break;
                        case "NORMAL_3x3":
                            label = "Normal 3x3";
                            break;
                        case "BOT_3x3":
                            label = "Bot 3x3";
                            break;
                        case "CAP_5x5":
                            label = "Cap 5x5";
                            break;
                        case "ARAM_UNRANKED_5x5":
                            label = "ARAM";
                            break;
                        case "ONEFORALL_5x5":
                            label = "Um por Todos 5x5";
                            break;
                        case "FIRSTBLOOD_1x1":
                            label = "First Blood 1x1";
                            break;
                        case "FIRSTBLOOD_2x2":
                            label = "First Blood 2x2";
                            break;
                        case "SR_6x6":
                            label = "Hexakill";
                            break;
                        case "URF":
                            label = "URF";
                            break;
                        case "URF_BOT":
                            label = "URF Bot";
                            break;
                        case "NIGHTMARE_BOT":
                            label = "Pesadelo";
                            break;
                        case "ASCENSION":
                            label = "Ascenção";
                            break;
                        case "HEXAKILL":
                            label = "Hexakill";
                            break;
                        case "KING_PORO":
                            label = "Rei Poro";
                            break;
                        case "COUNTER_PICK":
                            label = "Counter Pick";
                            break;
                        default:
                            label = "Indefinido";
                    }
            map.put(key, label);
        }
        result.use(Results.json()).withoutRoot().from(map).serialize();
    }

    @Path("api/recentGamesBySummonerId/{id}")
    public void recentGamesBySummonerId(long id) throws Exception {
        result.use(Results.json()).withoutRoot().from(riotApiConnector.getRecentGamesBySummonerId(id)).recursive().serialize();
    }

    @Path("api/summonerSpellsImageMap")
    public void summonerSpells() {
        result.use(Results.json()).withoutRoot().from(riotApiConnector.getSummonerSpellsImageMap()).serialize();
    }

    private long[] commaSeparatedStringToLongArray(String ids) {
        String[] idsArray = ids.split(",");
        long[] idsLongArray = new long[idsArray.length];

        for (int i = 0; i < idsArray.length; i++) {
            idsLongArray[i] = Long.valueOf(idsArray[i]);
        }
        return idsLongArray;
    }

    @Path("api/summonersByIds/{ids}")
    public void summonersByIds(String ids) throws Exception {

        result.use(Results.json()).withoutRoot().from(riotApiConnector.getSummonersByIds(ids)).recursive().serialize();
    }

    public String version() {
        return "0.0.1";
    }
}
