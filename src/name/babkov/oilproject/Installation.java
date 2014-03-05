package name.babkov.oilproject;

public class Installation {

    private double volume;
    
    private double distance;
    
    private String name;

    private double fullVolume;
    
    public Installation(double volume, double distance, String name) {
        this.volume = volume;
        this.distance = distance;
        this.name = name;
        fullVolume = 0;
    }

    public double getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }

    public double getFullVolume() {
        return fullVolume;
    }

    
    
    public double produceVolume() {
        
        fullVolume+=volume;
                        
        return volume;
    }        
           
}
