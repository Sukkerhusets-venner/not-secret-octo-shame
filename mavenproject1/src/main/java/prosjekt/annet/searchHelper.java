package prosjekt.annet;

import java.util.ArrayList;
import prosjekt.Domene.UserScoreOverview;


public class searchHelper {
    
    public searchHelper(){
    
    }
    public ArrayList<UserScoreOverview> getSearch(String sokt, ArrayList<UserScoreOverview> listen, int maxLengde){
        if(listen == null){
            return null;
        }
        
        ArrayList<Integer> rtList = new ArrayList<>();
        
        int k = 0;
        for(UserScoreOverview uso : listen){ // får alle distanser
            rtList.add(LevenshteinDistance(sokt.toLowerCase(), uso.getUser().getUsername().toLowerCase()));
            k++;
        } 
        ArrayList<UserScoreOverview> rt = new ArrayList<>();
        int minElem = 0;
        int elemIndex = 0;
        for(int i = 0; i < maxLengde; i++){
            if(rtList.isEmpty()){
                break;
            }
            for(int j = 0; j < rtList.size(); j++){
                if(j == 0){
                    minElem = rtList.get(j);
                    elemIndex = 0;
                }else if(rtList.get(j) < minElem){
                    minElem = rtList.get(j);
                    elemIndex = j;
                }
            }
            rt.add(listen.get(elemIndex));
            listen.remove(elemIndex);
            rtList.remove(elemIndex);
        }
        return rt;
    }
    
     // Lavenstein distance implementasjon
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
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] +0;
                int cost_delete = newcost[i - 1] +1;

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
