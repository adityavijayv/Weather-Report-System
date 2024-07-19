package JavaClasses.src;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class WeatherReport {
    public static void main(String[] args) {
        try{
            System.out.println("Welcome to the weather report system");
            System.out.println("This is created by Aditya Vj");

            Scanner sc = new Scanner(System.in);
            String city,state,country;
            System.out.println("Enter city, state and country :");
            city = sc.next();
            state = sc.next();
            country = sc.next();

            URL url = new URL("https://api.openweathermap.org/geo/1.0/direct?q=" +city+","+state+","+country+"&limit=1&appid=2baf9979477269295f7cb810286360da");
           HttpURLConnection con = (HttpURLConnection) url.openConnection();
           con.setRequestMethod("GET");
           con.connect();

           int rc = con.getResponseCode();
            //System.out.println(rc);
            if (rc != 200){
                System.out.println("Error while connection establishment");
            }
            else {
                String data ="";
                Scanner sc1 = new Scanner(url.openStream());

                while(sc1.hasNext()){
                    data += sc1.nextLine();
                }
                sc1.close();
               // System.out.println(data);

                JSONParser par = new JSONParser();
                JSONArray arr = (JSONArray) par.parse(data);
                //System.out.println(arr);
               // System.out.println(arr.get(0));
                JSONObject obj = null;

                for (int i =0;i < arr.size() ;i++){
                    obj = (JSONObject) arr.get(i);

                    double lat = (double)obj.get("lat");
                    double lon = (double)obj.get("lon");

                    System.out.println("Name of city:" +obj.get("name"));
                    //System.out.println("Latitude : " +lat);
                   // System.out.println("Longitude :"+lon);

                    URL url2 = new URL("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=2baf9979477269295f7cb810286360da");

                    HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
                    con2.setRequestMethod("GET");
                    con2.connect();

                    int rc2 = con2.getResponseCode();
                    //System.out.println(rc2);
                    if (rc2 != 200){
                        System.out.println("Error while connection establishment");
                    }
                    else {
                        String data2 ="";
                        Scanner sc2 = new Scanner(url2.openStream());

                        while(sc2.hasNext()){
                            data2 += sc2.nextLine();
                        }
                        sc2.close();
                        // System.out.println(data2);


                        JSONParser par1 = new JSONParser();
                        JSONObject obj1 = (JSONObject) par1.parse(data2);
                        // System.out.println(obj1);
                        JSONObject main = (JSONObject) obj1.get("main");
                        //  System.out.println(main);
                        double temperature = (double) main.get("temp");
                        temperature = temperature - 273.15;
                        JSONObject wind = (JSONObject) obj1.get("wind");

                        System.out.println("Weather Report of your city is:\n");
                        System.out.println("Temperature of city: \t" + temperature);
                        System.out.println("Humidity of city: \t" + main.get("humidity"));
                        System.out.println("Wind Speed of city: \t" + wind.get("speed"));


                        }
                }
            }
        }
        catch(Exception e){}
    }

}
