package jluzon.mov.urjc.xorapp;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;

public class LevelFactory {
    private static Context c;
    private static ImageView img;
    private static int[] imgArray;
    private static CheckBox[] chBArray;

    public LevelFactory(Context cnt, ImageView iv, int[] imgs, CheckBox[] cb){
        c = cnt;
        img = iv;
        imgArray = imgs;
        chBArray = cb;
    }

    public static Level produce(int desc){
        Level l;
        switch (desc){
            case 0:
                l = new Level0();
                break;
            case 1:
                l = new Level1(img,imgArray[desc]);
                break;
            case 2:
                l = new Level2();
                break;
            case 3:
                l = new Level3();
                break;
            default:
                l = new Level0();
        }
        // pasar la imagen en el constructor para que cargue él la imagen
        l.setImg();
        l.setButtons(chBArray);
        return l;
    }
}
