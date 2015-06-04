package com.bluefin.vraptor.business;

import dto.League.League;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Transforma objetos retornados pela api em mapeamentos simplificados para a exibição no front end da aplicação.
 * <p/>
 * Created by Jairo on 20/03/2015.
 */
public class GgiziTransformer {


    private final String _ASSETS_PATH = "assets/tier/";
    private final String _PNG = ".png";
    private final String[] _URL_KEYS = {"rankedSoloUrl", "ranked3x3Url", "ranked5x5Url"};
    private final String _URL_SUFIX = "Url";
    private final String[] _LABEL_KEYS = {"rankedSoloLabel", "ranked3x3Label", "ranked5x5Label"};
    private final String _LABEL_SUFIX = "Label";
    private final String _TIER = "Tier";

    public Map<String, String> transformLeagueInfo(Map<String, List<League>> map, String key) {
        Map<String, String> transformedMap = initializeTransformedMap();
        if(!map.isEmpty()){
            for (League league : map.get(key)) {
                fillProperties(league, transformedMap);
            }
        }
        return transformedMap;
    }

    private Map<String, String> initializeTransformedMap() {
        Map<String, String> transformedMap = new HashMap<>();
        for (String key : _URL_KEYS) {
            transformedMap.put(key, "assets/tier/unknown.png");
        }
        for (String key : _LABEL_KEYS) {
            transformedMap.put(key, "Não Ranqueado");
        }
        return transformedMap;
    }

    private String resolveKey(League league, String sufix) {
        switch (league.getQueue()) {
            case "RANKED_SOLO_5x5":
                return "rankedSolo" + sufix;
            case "RANKED_TEAM_3x3":
                return "ranked3x3" + sufix;
            default:
                return "ranked5x5" + sufix;
        }
    }

    private void fillProperties(League league, Map<String, String> transformedMap) {

        String tier = league.getTier();
        String division = league.getEntries().get(0).getDivision();
        transformedMap.put(resolveKey(league, _URL_SUFIX), _ASSETS_PATH + tier + "_" + division + _PNG);
        transformedMap.put(resolveKey(league, _LABEL_SUFIX), localizedTier(tier) + " " + division);
        transformedMap.put(resolveKey(league, _TIER), tier.toLowerCase());

    }

    private String localizedTier(String tier){
        switch (tier){
            case "BRONZE":
                return "Bronze";
            case "SILVER":
                return "Prata";
            case "GOLD":
                return "Ouro";
            case "PLATINUM":
                return "Platina";
            case "DIAMOND":
                return "Diamante";
            case "MASTER":
                return "Mestre";
            case "CHALLENGER":
                return "Desafiante";
            default:
                return "Não ranqueado";


        }
    }


}
