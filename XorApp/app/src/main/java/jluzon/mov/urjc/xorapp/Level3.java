package jluzon.mov.urjc.xorapp;

import android.widget.ImageView;
import android.widget.Switch;

public class Level3 extends Level {
    private final int ENTRIES = 4;

    public Level3(ImageView img, int id, Switch[] chb){
        super(img,id,chb);
        this.setNetries(ENTRIES);
    }

    // &&--AND ||--OR
    @Override
    public boolean badOut(boolean cb[]){
        return (cb[2]||cb[3])||!((!cb[0]||cb[1])&&!(cb[2]||cb[3]));
    }

    @Override
    public boolean goodOut(boolean cb[]){
        return !((!(!cb[0]||cb[1]))&&!((!(cb[2]||cb[3]))&&(!cb[0]||cb[1])));
    }
}
