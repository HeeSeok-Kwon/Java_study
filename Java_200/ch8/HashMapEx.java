import java.util.HashMap;

class Geo {
    private double latitude;
    private double longitude;
    
    public Geo() {
        this(37.5, 127.0);
    }
    
    public Geo(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

public class ArrayListEx {
    public static void main(String args[]) {
      HashMap<String, Geo> cities = new HashMap<String, Geo>();
      System.out.println(cities.size());
      cities.clear();
      cities.put("Korea",new Geo(37.5670, 126.9807));
      cities.put("Austria", new Geo(47.01, 10.2));
      cities.put("US", new Geo(40.714086, -74.228697));
      cities.put("Mexico", new Geo(19.42847, -99.12766));
      cities.put("China", new Geo(39.9075, 116.39723));
      cities.put("Rusia", new Geo(55.75222, 37.61556));
      System.out.println(cities.size());
      System.out.println(cities.containsKey("Austria"));
      Geo geo = cities.get("Austria");
      System.out.printf("위도:%f, 경도:%f\n",geo.getLatitude(), geo.getLongitude());
    }
}