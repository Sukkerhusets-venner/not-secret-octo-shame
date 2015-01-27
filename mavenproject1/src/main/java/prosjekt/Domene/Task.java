package prosjekt.Domene;

import java.util.Random;

public class Task {

    private int tasknr;
    private String strTask = null; // streng fra database
    private String text = null;
    private String taskHtml = null; // html delen av oppgaven
    private String taskCss = null; //css delen av oppgaven
    private String answerHtml = null; //Svaret fra bruker
    private String answerCss = null;
    private int maxPoeng; // max poeng på oppgaven
    private int diff;
    private String type = null; // setter type (snake, hangman osv)

       public Task(int tasknr, String type, String text, int diff, String taskHtml, String answerHtml, String taskCss, String answerCss, int poeng) {
        this.tasknr = tasknr;
        this.type = type;
        this.text = text;
        this.diff = diff;
        this.taskHtml = taskHtml;
        this.answerHtml = answerHtml;
        this.taskCss = taskCss;
        this.answerCss = answerCss;
        this.maxPoeng =poeng;
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
        return taskCss;
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

    public String getAnswerHtml() {
        return answerHtml;
    }

    public void setAnswerHtml(String answerHtml) {
        this.answerHtml = answerHtml;
    }
    
    public String getAnswerCss() {
        return answerCss;
    }
    
    public void setAnswerCss(String answerCss) {
        this.answerCss = answerCss;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }
}
