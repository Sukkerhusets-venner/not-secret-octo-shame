package prosjekt.Domene;

import java.util.Random;

public class Task {

    int tasknr;
    String strTask = null; // streng fra database
    String actualTask = null; // kun getter. Oppgaven som tolket streng
    String answer = null; // kun setter. Svaret fra bruker
    int maxPoeng; // max poeng på oppgaven
    int poeng; // kun getter. Brukerens poeng på oppgaven

    private Task(int tasknr, String stask) {
        this.tasknr = tasknr;
        this.strTask = stask;

        loadTask(); //tolker oppgaven og oversetter til css & html
    }

    // Setter lagret streng til noe vi kan bruke som en oppgave
    private void loadTask() {
        String[] bygg = stripString(strTask);
        this.actualTask = buildString(bygg); // setter oppgave
    }

    private String buildString(String[] list) {
        StringBuilder bygg = new StringBuilder();
        for (String s : list) {
            bygg.append(s);
        }
        return bygg.toString();
    }

    private String[] rndStrList(String[] list) {
        Random rn = new Random();
        rn.nextInt();
        int len = list.length;
        for (int i = 0; i < len; i++) {
            int change = i + rn.nextInt(len - i);
            String helper = list[i];
            list[i] = list[change];
            list[change] = helper;
        }
        return list;
    }

    // Tolker streng fra database. MANGLER STØTTE!
    private String[] stripString(String str) {
        String[] list = new String[strTask.length()];
        for (int i = 0; i < strTask.length(); i++) {
            char charAt = strTask.charAt(i);
            switch (charAt) {
                case 'd':
                    list[i] = "<div>";
                    break;
                case 'D':
                    list[i] = "<div>";
                    break;
                //*** Legg til flere! ***
                default:
                    list[i] = "";
            }
        }
        return list;
    }

    // Tolker streng fra bruker.
    private String[] stripAnswer(String str) {
        String[] list = new String[0];
        boolean codeWord = false;
        StringBuilder word = new StringBuilder();
        int nextItem = 0;

        for (int i = 0; i < strTask.length(); i++) {
            char charAt = strTask.charAt(i);
            if (charAt == '<' || charAt == '{') {
                list = addStr(list, word.toString());
                word = new StringBuilder();
                codeWord = true;
            }
            word.append(charAt);
            if ((codeWord && charAt == '>') || (codeWord && charAt == '}')) {
                list = addStr(list, word.toString());
                word = new StringBuilder();
            }
        }
        // dersom koden ikke sluttet med > eller }
        if (word.length() > 0) {
            list = addStr(list, word.toString());
        }
        return list;
    }

    // List utility
    private String[] addStr(String[] list, String item) {
        String[] tmp = list;
        list = new String[list.length + 1];
        int i = 0;
        for (String s : tmp) {
            list[i] = s;
            i++;
        }
        list[i] = item;
        return list;
    }

    public int getPoeng() {
        if (answer == null) {
            return 0;
        }
        int d = LevenshteinDistance(answer, actualTask);
        if (d > maxPoeng) {
            poeng = 0;
        } else {
            poeng = maxPoeng - d;
        }
        // this.poeng = quickCompare(answer, actualTask);
        return poeng;
    }

    // Sammenlikner ord istedet for bokstaver. Return = poeng 
    private int quickCompare(String ans, String fasit) {
        int points = 0;
        int len = 0;
        String[] svar = stripAnswer(ans);
        String[] actual = stripString(fasit);

        if (svar.length >= actual.length) {
            len = actual.length;
        } else {
            len = svar.length;
        }
        for (int i = 0; i < len; i++) {
            if (svar[i].equals(actual[i])) {
                points++;
            }
        }
        Double pros = (poeng) / ((double) svar.length);
        points = (int) java.lang.Math.floor(pros * maxPoeng);

        return points;
    }

    // Lavenstein distance implementasjon
    public int LevenshteinDistance(String s0, String s1) {
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
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

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

    public int getMaxPoeng() {
        return maxPoeng;
    }

    public void setMaxPoeng(int maxPoeng) {
        this.maxPoeng = maxPoeng;
    }

    public String getActualTask() {
        return actualTask;
    }

    public int getTasknr() {
        return tasknr;
    }

    public String getStrTask() {
        return strTask;
    }

    public void setTasknr(int tasknr) {
        this.tasknr = tasknr;
    }

    public void setStrTask(String strTask) {
        this.strTask = strTask;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
