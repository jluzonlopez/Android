package es.urjc.mov.jluzon.thermos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ActivePcsActivity extends AppCompatActivity {
    private final static int SERVERPORT = 25050;
    private String pcThemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_pcs);
    }

    private void getThemp(String pc){
        Socket s;
        DataOutputStream out;
        DataInputStream in;
        try{
            s = new Socket(pc,SERVERPORT);
            //s.setSoTimeout(2000);
            out = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());
            Messages m = new Messages.ReqMsg();
            out.writeUTF(m.buildMsg());

            //wait the answer
            String message = in.readUTF();
            Log.d("OVER", "Respuesta: "+message);
            pcThemp = Integer.parseInt(message)/1000+"";
            PaintGraph.writetoFile(ActivePcsActivity.this,message,pc);

            out.close();
            s.close();

        }catch (ConnectException e) {
            Log.d("OVER", "Connection refused " + e.getMessage());
            e.printStackTrace();
        }catch (UnknownHostException e){
            Log.d("OVER","cannot connect to host " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("OVER", "Socket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void connect(String ipc){
        final String pc = ipc;
        Thread c = new Thread() {
            @Override
            public void run() {
                getThemp(pc);
            }
        };
        c.start();
        try {
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getPcThemp(View b){
        EditText pcIP = findViewById(R.id.ipPc);
        String pc = pcIP.getText().toString();
        connect(pc);
        Intent themp = new Intent(ActivePcsActivity.this,ThempActivity.class);
        Bundle thempInfo = new Bundle();
        thempInfo.putString("themp",pcThemp);
        pcThemp = null;
        thempInfo.putString("pc",pc);
        themp.putExtras(thempInfo);
        startActivity(themp);
    }
}
