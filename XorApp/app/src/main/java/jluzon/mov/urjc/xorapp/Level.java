package jluzon.mov.urjc.xorapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

public abstract class Level {
    private int netries;
    private static ImageView imgV;
    private int idImg;
    private Switch[] cb;

    public Level(ImageView img, int id, Switch[] chb){
        imgV = img;
        idImg = id;
        cb = chb;
    }
    // &&--AND ||--OR
    public abstract boolean badOut(boolean cB[]);
    public abstract boolean goodOut(boolean cB[]);

    private static void resetButtons(Switch[] chBArray){
        for(int i=0;i<chBArray.length;i++){
            chBArray[i].setVisibility(View.GONE);
        }
    }

    public void setNetries(int n){ netries = n; }
    private void setImg() {
        imgV.setImageResource(idImg);
    }

    private void setButtons(){
        resetButtons(cb);
        for(int i=0;i<netries;i++){
            cb[i].setVisibility(View.VISIBLE);
        }
    }

    public void initLevel(){
        setImg();
        setButtons();
    }

    public int calcScore(int[] times){
        int result = 0;
        for(int i=0;i<times.length;i++){
            if(times[i] != 0){
                result = result + ((i+1)*100)-times[i];
            }
        }
        if(result <= 0){
            return 0;
        }
        return result;
    }
}
