package jluzon.mov.urjc.xorapp;

import java.util.Arrays;

public abstract class Ordenation {

    private static String ordenarScores(int intScores[], String strLine[]){
        String finalData="";
        String playerInfo[];
        int val;
        int currentVal;
        int length = intScores.length;
        for(int i=0;;i=(i+1)%(strLine.length)){
            val = intScores[length-1];
            playerInfo = strLine[i].split(" ");
            currentVal = Integer.parseInt(playerInfo[playerInfo.length-1]);

            if(val == 0){
                break;
            }

            if(val == currentVal){
                finalData = finalData + strLine[i]+"\n";
                length--;
            }

            if(length == 0){
                break;
            }
        }
        return finalData;
    }

    public static String ordenar(String myData){
        String strLine[] = myData.split("\n");
        int scoreArray[] = new int[strLine.length];
        String finalData;

        if(strLine.length == 1){
            return myData;
        }else {
            for (int i = 0; i < strLine.length; i++) {
                String playerInfo[] = strLine[i].split(" ");
                int points = Integer.parseInt(playerInfo[playerInfo.length - 1]);
                scoreArray[i] = points;
            }
            Arrays.sort(scoreArray);
            finalData = ordenarScores(scoreArray,strLine);
        }
        return finalData;
    }

    public static String checkPlayer(String player, int score, String data, String times){
        String strLine[] = data.split("\n");
        int i;
        boolean founded = false;
        int dataScore = 0;
        for(i=0;i<strLine.length;i++){
            String playerInfo[] = strLine[i].split(" ");
            if(player.equals(playerInfo[0])){
                founded = true;
                dataScore = Integer.parseInt(playerInfo[playerInfo.length - 1]);
                break;
            }
        }

        if(founded && (dataScore < score)){
            strLine[i] = player+times+"SCORE: "+score;
        }

        String finalData="";
        for(int k=0;k<strLine.length;k++) {
            finalData = finalData+strLine[k]+"\n";
        }

        if(!founded){
            finalData = finalData+player+times+"SCORE: "+score;
        }

        return finalData;
    }
}
