import java.util.ArrayList;

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
      ArrayList<Geo> geolist = new ArrayList<Geo>();
      System.out.println(geolist.size());
      geolist.clear();
      geolist.add(new Geo(37.5670, 126.9807));
      geolist.add(new Geo(47.01, 10.2));
      geolist.add(new Geo(40.714086, -74.228697));
      geolist.add(new Geo(19.42847, -99.12766));
      geolist.add(new Geo(39.9075, 116.39723));
      geolist.add(new Geo(55.75222, 37.61556));
      System.out.println(geolist.size());
      Geo gt = geolist.get(1);
      System.out.printf("위도:%f, 경도:%f\n",gt.getLatitude(), gt.getLongitude());
    }
}