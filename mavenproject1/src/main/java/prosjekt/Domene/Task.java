package prosjekt.Domene;

import java.util.Random;

public class Task {

    int tasknr;
    String strTask = null; // streng fra database
    String taskHtml = null; // html delen av oppgaven
    String taskCss = null; //css delen av oppgaven
    String answer = null; //Svaret fra bruker
    String description = null; //Oppgavebeskrivelse
    int maxPoeng; // max poeng på oppgaven
    int poeng; // kun getter. Brukerens poeng på oppgaven
    String type = null; // setter type (snake, hangman osv)

    public Task(){
        taskHtml = "<html><body><h1>Hei</h1>Hjelp, formatteringen er helt gal</body></html>";
        taskCss = "body {background-color: white; color: blue;} h1 { color: red; text-align: center; }";
        description = "Her skal du endre på Css-delen slik at bildene under blir like.";
        type = "Css";
    }
    public Task(String s){
        taskHtml = "<html><body><h1>Hei</h1>Løs meg!</body></html>";
        taskCss = "body {background-color: white; color: blue;} h1 { color: red; text-align: center; }";
        description = "Her skal du endre på Html-delen slik at bildene under blir like.";
        type = "Html";
    }
       public Task(int tasknr, String type, String taskHtml, String taskCss, int poeng) {
        this.tasknr = tasknr;
        this.type = type;
        this.taskHtml = taskHtml;
        this.taskCss = taskCss;
        this.poeng =poeng;
    }
    
    public Task(int tasknr, String stask) {
        this.tasknr = tasknr;
        this.strTask = stask;

        loadTask(); //tolker oppgaven og oversetter til css & html
    }

    // Setter lagret streng til noe vi kan bruke som en oppgave
    private void loadTask() {

        String[] del = strTask.split("§D"); // HTMLKODE §D CSSKODE §D type
        try{
            this.type = del[2];
        }catch(ArrayIndexOutOfBoundsException ae){
            this.type = "default";
        }

        String[] bygg = interpretHtmlString(del[0]);
        this.taskHtml = buildString(bygg); // setter oppgave
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
    private String[] interpretHtmlString(String str) {
        String[] list = new String[strTask.length()];
        StringBuilder sentance = new StringBuilder(); //not supported code or sentances
        boolean codeWord = true;
        for (int i = 0; i < strTask.length(); i++) {
            char charAt = strTask.charAt(i);

            if (codeWord) {
                switch (charAt) {
                    case '§':
                        codeWord = !codeWord;
                        break;
                    case 'd':
                        list[i] = "&lt;div&gt;"; // Viktig! ikke bruk < eller >. (blir tolket som tagger i html)
                        break;
                    case 'D':
                        list[i] = "&lt;/div&gt;";
                        break;
                    //*** Legg til flere! ***
                    default:
                        list[i] = String.valueOf(charAt); // skal ikke intreffe
                        break;
                }
            }else{
                if(charAt == '§'){
                    codeWord = !codeWord;
                }else{
                    sentance.append(charAt);
                }
            }
        }
        return list;
    }
    private String interpretAnswer(String str){
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < str.length(); i++){
            char charAt = str.charAt(i);
            switch(charAt){
                case '<': out.append("&lt;"); break;
                case '>': out.append("&gt;"); break;
                default: out.append(charAt); break;
            }
        }
        return out.toString();
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
        int d = LevenshteinDistance(interpretAnswer(answer), taskHtml);
        if (d > maxPoeng) {
            poeng = 0;
        } else {
            poeng = maxPoeng - d;
        }
        return d;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxPoeng() {
        return maxPoeng;
    }

    public void setMaxPoeng(int maxPoeng) {
        this.maxPoeng = maxPoeng;
    }

    public String getTaskHtml() {
        return taskHtml;
    }
    public String getTaskCss(){
        return this.taskCss;
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
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
