package prosjekt.annet;

import java.util.ArrayList;
import prosjekt.Domene.UserScoreOverview;

/*
 * Brukes i hovedsiden for å sortere godkjentlisten   
 * Implementerer en metode som gir kostnader istedenfor å filtrere, dermed kan vi få mange resultater
 * Resultatene vil da være sortert etter 'nærmeste' streng. (lek med kostnadene i lavenstein metoden for å se nærmere :D) 
*/


public class searchHelper {
    
    public searchHelper(){
    
    }
    public ArrayList<UserScoreOverview> getSearch(String sokt, ArrayList<UserScoreOverview> listen, int maxLengde){
        if(listen == null){
            return null;
        }
        if(maxLengde > listen.size()){
            maxLengde = listen.size();
        }
        
        ArrayList<Integer> rtList = new ArrayList<>();
        
        int k = 0;
        for(UserScoreOverview uso : listen){ // får alle distanser
            rtList.add(LevenshteinDistance(sokt.toLowerCase(), uso.getUser().getUsername().toLowerCase()));
            k++;
        } // litt meningsløst å bruke en så fin algoritme når vi har allt dette tullet, men men
        // kunne dumpet arraylist.toArray(), men orker ikke 
        ArrayList<UserScoreOverview> rt = new ArrayList<>();
        int minElem = 0;
        int elemIndex = 0;
        for(int i = 0; i < maxLengde; i++){
            if(rtList.isEmpty()){ 
                break; // hvis tom
            }
            for(int j = 0; j < rtList.size(); j++){
                if(j == 0){ // første element
                    minElem = rtList.get(j); // setter 'nærmeste'
                    elemIndex = 0;
                }else if(rtList.get(j) < minElem){ // i >= 1
                    minElem = rtList.get(j); // setter 'nærmeste'
                    elemIndex = j; //hvis legges til, hvor (j)
                }
            }
            rt.add(listen.get(elemIndex));
            listen.remove(elemIndex); // fjernes for å ikke legges til igjen 
            rtList.remove(elemIndex);
        }
        return rt;
    }
    
     // Lavenstein distance implementasjon - fant den på engelsk wikipedia xD
    private int LevenshteinDistance(String s0, String s1) {
        int len0 = s0.length() + 1;
        int len1 = s1.length() + 1;

        // the array of distances                                                       
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0                                 
        for (int i = 0; i < len0; i++) {
            cost[i] = i;
        }
        // dynamicaly computing the array of distances                                  
        // transformation cost for each letter in s1                                    
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1                             
            newcost[0] = j;

            // transformation cost for each letter in s0                                
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings                             
                int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation                               
                int cost_replace = cost[i - 1] + match; // bokstav ulik søkt bokstav (satt .toLowerCase() så vi får ingen store forskjeller her, unntak er æøå da ascii er 130+ for disse tegnene)
                int cost_insert = cost[i] +0; // +1 gir søkt "j" - nora = jonas, det liker vi ikke
                int cost_delete = newcost[i - 1] +1; // navn kortere enn søkt streng

                // keep minimum cost                                                    
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays                                                 
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }
        // the distance is the cost for transforming all letters in both strings        
        return cost[len0 - 1];
    }
}
