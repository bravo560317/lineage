package com.lineage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class TestAddr
{
    public static void main(String[] args) {
        new IP();
    }
    
    public void IP() {
        try {
            URL ipAdress = new URL("http://myexternalip.com/raw");
            BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));
            String ip = in.readLine();
            System.out.println(ip);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
