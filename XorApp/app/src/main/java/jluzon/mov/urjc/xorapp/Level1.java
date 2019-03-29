package jluzon.mov.urjc.xorapp;

import android.widget.ImageView;
import android.widget.Switch;

public class Level1 extends Level{
    private final int ENTRIES = 3;

    public Level1(ImageView img, int id, Switch[] chb){
       super(img,id, chb);
       this.setNetries(ENTRIES);
    }
    // &&--AND ||--OR
    @Override
    public boolean badOut(boolean cb[]){
        return !(cb[0]||cb[1]);
    }

    @Override
    public boolean goodOut(boolean cb[]){
        return (cb[0]||cb[1])||!cb[2];
    }
}
