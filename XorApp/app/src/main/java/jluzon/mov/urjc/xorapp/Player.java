package jluzon.mov.urjc.xorapp;

public class Player implements Comparable<Player>{
    private String playerName;
    private int []times;
    private int score;

    public Player(String n,int[] t,int sc){
        this.playerName = n;
        this.times = t;
        this.score = sc;
    }

    public int getScore(){
        return score;
    }

    public String getPlayerName(){
        return playerName;
    }

    private String getTimes(){
        String t="";
        for(int i=0;i<times.length;i++){
            t=t+","+times[i];
        }
        return t;
    }

    @Override
    public int compareTo(Player p) {
        int compareScore = p.getScore();
        return compareScore - this.score;
    }

    @Override
    public String toString(){
        return this.getPlayerName()+this.getTimes()+","+this.getScore();
    }
}
