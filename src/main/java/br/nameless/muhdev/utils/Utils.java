package br.nameless.muhdev.utils;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Utils {

    public static String[] curl(String site) {
        try {
            URLConnection connect = new URL(site).openConnection();
            connect.addRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            Scanner scan = new Scanner(connect.getInputStream());
            StringBuilder b = new StringBuilder();
            while (scan.hasNext()) {
                String text = scan.next();
                b.append(text);
            }
            scan.close();

            return b.toString().replace(";", " ")
                    .replace("host", "")
                    .replace("=", "")
                    .replace("'", "")
                    .replace("port", "")
                    .replace("user", "")
                    .replace("db", "")
                    .replace("password", "")
                    .replace("nmversion", "")
                    .split(" ");
        } catch (Exception e) {
            e.printStackTrace();
            return new String[] {""};
        }
    }
	
}
