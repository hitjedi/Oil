package name.babkov.oilproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OilProject {

    private final int numOfInstallation = 5;
    
    private final int numOfWorkInstallation = 3;
    
    private final int volumeInterval = 50;
    
    private final int distanceInterval = 10;
    
    private List<Installation> listOfInstallation; 
            
    private List<InstallationEngine> listOfInstallationEngine;
       
    private Dispatcher dispatcher;   
    
    private Pipe pipe;
    
    
    public static void main(String[] args) {
        
        
       new OilProject().Start(); 
                     
    }
        
    public void Start(){
             
       Random random = new Random();              
               
       listOfInstallation = new ArrayList<Installation>(numOfInstallation);
       
       listOfInstallationEngine = new ArrayList<InstallationEngine>(numOfWorkInstallation);
       
              
       for(int i = 0;i<numOfInstallation;i++){
       
           listOfInstallation.add(new Installation(random.nextDouble()*volumeInterval, 
                                                       random.nextDouble()*distanceInterval,
                                                       "Installation #"+Integer.toString(i)));
           
       }
       
       pipe = new Pipe(500);
       
       dispatcher = new Dispatcher(listOfInstallation, numOfWorkInstallation, pipe);
       
       dispatcher.start();
       
       
        
    }
}
