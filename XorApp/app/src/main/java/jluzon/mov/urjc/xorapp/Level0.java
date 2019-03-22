package jluzon.mov.urjc.xorapp;

import android.widget.CheckBox;
import android.widget.ImageView;

public class Level0 extends Level{
    private final int ENTRIES = 2;

    public Level0(ImageView img, int id, CheckBox[] chb){
        super(img,id,chb);
        this.setNetries(ENTRIES);
    }

    // &&--AND ||--OR
    @Override
    public boolean badOut(boolean cb[]){
        return !(!cb[0]&&cb[1]);
    }

    @Override
    public boolean goodOut(boolean cb[]){
        return cb[1];
    }
}
