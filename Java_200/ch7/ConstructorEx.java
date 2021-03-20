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

public class ConstructorEx {
    public static void showGeo(Geo ...goose) {
        for(Geo gg:goose) {
            System.out.printf("위도:%f, 경도:%f\n",gg.getLatitude(), gg.getLongitude());
        }
    }
    
    public static void main(String args[]) {
     
     Geo[] geo = new Geo[] {
         new Geo(37.5670, 126.9807), new Geo(47.01, 10.2), new Geo(40.714086, -74.228697)
     };
     //showGeo(geo[0],geo[1],geo[2]);
    
    Geo[] geo1 = new Geo[3];
    geo1[0] = new Geo(37.5670, 126.9807);
    geo1[1] = new Geo(47.01, 10.2);
    geo1[2] = new Geo(40.714086, -74.228697);
    
    //System.out.printf("위도:%f, 경도:%f\n",geo[0].getLatitude(), geo[0].getLongitude());
    //System.out.printf("위도:%f, 경도:%f\n",geo1[0].getLatitude(), geo1[0].getLongitude());
    
    for(int i=0;i<geo.length;i++) {
        System.out.printf("위도:%f, 경도:%f\n",geo[i].getLatitude(), geo[i].getLongitude());
    }
    
    for(Geo gg:geo1) {
        System.out.printf("위도:%f, 경도:%f\n",gg.getLatitude(), gg.getLongitude());
    }
    //  Geo seoul = new Geo(37.52127220511242, 127.0074462890625);
    //  Geo austria = new Geo(47.01, 10.2);
    //  Geo newyork = new Geo(40.714086, -74.228697);
    //  Geo mexico = new Geo(19.42847, -99.12766);
    //  Geo china = new Geo(39.9075, 116.39723);
    //  Geo rusia = new Geo(55.75222, 37.61556);
     
    //  showGeo(seoul,seoul,newyork);
    //  showGeo(seoul,austria,newyork,mexico,china,rusia);
     
     
    // Geo geo1 = new Geo();
    // geo1.setLatitude(37.52127220511242);
    // geo1.setLongitude(127.0074462890625);
    //  System.out.printf("위도:%f, 경도:%f\n",geo1.getLatitude(), geo1.getLongitude());
    //  Geo geo2 = geo1;
    //  System.out.printf("위도:%f, 경도:%f\n",geo2.getLatitude(), geo2.getLongitude());
    }
}