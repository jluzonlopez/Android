package jluzon.mov.urjc.xorapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

public abstract class Level {
    private int netries;
    private static ImageView imgV;
    private int idImg;
    private CheckBox[] cb;

    public Level(ImageView img, int id, CheckBox[] chb){
        imgV = img;
        idImg = id;
        cb = chb;
    }
    // &&--AND ||--OR
    public abstract boolean badOut(boolean cB[]);
    public abstract boolean goodOut(boolean cB[]);

    private static void resetButtons(CheckBox[] chBArray){
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
}
