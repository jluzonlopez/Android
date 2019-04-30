package es.urjc.mov.jluzon.thermos;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class Messages {
    public enum TypeMsg{
        REQTHEMP, THEMP, ACK
    }

    public abstract TypeMsg typeMsg();
    public abstract String buildMsg();

    @Override
    public String toString() {
        return "Type "+typeMsg();
    }

    public static class ReqMsg extends Messages{
        private static final TypeMsg TYPE = TypeMsg.REQTHEMP;

        @Override
        public TypeMsg typeMsg() {
            return TYPE;
        }

        public String buildMsg() {
            System.out.println("Building Request");
            return "Req";
        }
    }

    public static class ThempMsg extends Messages {
        private static final TypeMsg TYPE = TypeMsg.THEMP;
        private static final String PATH = "/sys/class/thermal/thermal_zone0/temp";

        @Override
        public TypeMsg typeMsg() {
            return TYPE;
        }

        private String readFile() {
            File f;
            FileReader fr;
            BufferedReader br;
            String themp = "";
            try {
                f = new File(PATH);
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                String line;
                while((line=br.readLine()) != null) {
                    themp = line;
                }

            }catch(Exception e) {
                e.printStackTrace();
            }

            return themp;
        }

        public String buildMsg() {
            return readFile();
        }
    }

    public static class Ack extends Messages{
        private static final TypeMsg TYPE = TypeMsg.ACK;

        @Override
        public TypeMsg typeMsg() {
            return TYPE;
        }

        public String buildMsg() {
            return "Ack";
        }
    }

    public static Messages readMsg(DataInputStream data) {
        Messages msg = null;
        try {
            String typeMsg = data.readUTF();
            switch(typeMsg) {
                case "Req":
                    msg = new ReqMsg();
                    break;
                case "Ack":
                    msg = new Ack();
                    break;
                default:
                    break;
            }
        }catch(IOException e) {
            System.out.println("Socket: " + e.getMessage());
        }
        return msg;
    }
}