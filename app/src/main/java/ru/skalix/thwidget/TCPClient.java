package ru.skalix.thwidget;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
    private DataOutputStream out = null;
    private BufferedReader in = null;
    private Socket soc = null;

    private String ip = null;
    private int port = 0;

    public TCPClient(String IP, int PORT){
        ip = IP;
        port = PORT;
    }

    public boolean Connect(){
        try{
            InetAddress address = InetAddress.getByName(ip);
            soc = new Socket(address, port);

            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            out = new DataOutputStream(soc.getOutputStream());

            return true;
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void Disconnect(){
        if(soc != null){
            try {
                soc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            soc = null;
        }
        if(out != null){
            try{
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out = null;
        }
        if(in != null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            in = null;
        }
    }

    public void Send(String msg) {
        try {
            out.writeBytes(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Read(){
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
