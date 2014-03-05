package name.babkov.oilproject;

import java.util.ArrayList;
import name.babkov.oilproject.listener.PipeListener;
import name.babkov.oilproject.listener.InstallationEngineListener;
import java.util.EventObject;
import java.util.List;

public class Dispatcher implements  PipeListener, InstallationEngineListener{

    private int numOfWorkInstallation;
    
    private List<Installation> listOfInstallation;
    
    private List<InstallationEngine> listOfReservedInstallationEngine;
    
    private List<InstallationEngine> listOfInstallationEngine;

    private Pipe pipe;
    
    public Dispatcher(List<Installation> listOfInstallation, int numOfWorkInstallation, Pipe pipe) {
                        
        this.numOfWorkInstallation = numOfWorkInstallation;
                       
        this.listOfInstallation = listOfInstallation;
        
        this.pipe = pipe;
        
        this.listOfInstallationEngine = new ArrayList<InstallationEngine>();
        
        this.listOfReservedInstallationEngine = new ArrayList<InstallationEngine>();
        
    }
    
           
    @Override
    public void pipeFulled(EventObject event) {
        
        stop();
                                                
    }

    
    @Override
    public void failureOccured(EventObject event) {
    
        InstallationEngine engine = (InstallationEngine)event.getSource();
        
        System.out.println("!!!!!!!!!!!!!!!! " +engine.getInstallation().getName() + " failure !!!!!!!!!!!!!!!!!!");                
        
        if(listOfReservedInstallationEngine.isEmpty()){
            
            stop();
            
        }
        else{                                               
            
            int minIndex = -1;
            
            double min = Double.MAX_VALUE;
            
            for(int i=0; i<listOfReservedInstallationEngine.size();i++){
                
                if(listOfReservedInstallationEngine.get(i).getInstallation().getDistance()<min){
                    
                  minIndex = i;
                    
                }
                                    
            }
            
            engine = listOfReservedInstallationEngine.remove(minIndex);
            
            listOfInstallationEngine.add(engine);
            
            System.out.println("!!!!!!!!!!!!!!!! " +engine.getInstallation().getName() + " started !!!!!!!!!!!!!!!!!!");                
            
            engine.start();
            
        }
    
        
        
    }
    
    
    public void start(){
    
        pipe.addPipeListener(this);
        
        for(int i = 0;i<listOfInstallation.size();i++){

               InstallationEngine engine = new InstallationEngine(listOfInstallation.get(i),pipe);

               engine.addInstallationEngineListener(this);
               
               if(i<numOfWorkInstallation){
                   
                   listOfInstallationEngine.add(engine);
                   
               }
               else{
                   
                   listOfReservedInstallationEngine.add(engine);
                   
               }

           }

        for(int i = 0;i<numOfWorkInstallation;i++){

               InstallationEngine engine = listOfInstallationEngine.get(i);
               
               System.out.println("!!!!!!!!!!!!!!!! " +engine.getInstallation().getName() + " started !!!!!!!!!!!!!!!!!!");                
               
               engine.start();
               
           }
                            
    }
    
    public void stop(){
        
        for(InstallationEngine engine:listOfInstallationEngine){
         
            engine.interrupt();
        
        }
        
        System.out.println("----------------------------------------------");
        
        for(InstallationEngine engine:listOfInstallationEngine){
            
            Installation installation = engine.getInstallation();
            
            System.out.println(installation.getName() + 
                               " produced total "+ 
                               String.format("%6.2f",installation.getFullVolume()) + 
                               " of oil");
            
            if(engine.isFailure()){
                System.out.println("Failure occured at " +engine.getFailureDate());
            }
                                
        }

        System.out.println("Volume in pipe " + String.format("%6.2f", pipe.getVolume()));
        
        System.out.println("End system");
    }
    
}
