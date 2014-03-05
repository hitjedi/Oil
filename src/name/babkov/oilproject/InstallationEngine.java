package name.babkov.oilproject;

import name.babkov.oilproject.event.InstallationEngineEvent;
import name.babkov.oilproject.listener.InstallationEngineListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class InstallationEngine extends Thread{

    private final int interval = 500;
    
    private Installation installation;
    
    private Pipe pipe; 
    
    private List<InstallationEngineListener> listOfInstallationEngineListener;
    
    private Date failureDate;
    
    public InstallationEngine(Installation installation, Pipe pipe) {
        
        this.installation = installation;
        
        this.pipe = pipe;
        
        this.listOfInstallationEngineListener = new ArrayList<InstallationEngineListener>();
    }

    public Installation getInstallation() {
        return installation;
    }
    
    
    
    public synchronized void addInstallationEngineListener(InstallationEngineListener listener){
        
        listOfInstallationEngineListener.add(listener);
        
    }

    
    public synchronized void removeInstallationEngineListener(InstallationEngineListener listener){
        
        listOfInstallationEngineListener.remove(listener);
        
    }
    
        
    @Override
    public void run() {
        
        boolean isWorked = true;
        
        Random random  = new Random();
        
        while(isWorked && !Thread.interrupted()){
    
            double volume = installation.produceVolume();
            
            System.out.println(
                    installation.getName() + 
                    " produced " + 
                    String.format("%6.2f",installation.getFullVolume()) 
                    + " of oil");
            
            pipe.addOilToPipe(volume);
                                                                             
            try {
                
                Thread.sleep(interval);
            
            } catch (InterruptedException ex) {
            
                break;
            }
            
            if(random.nextInt(20)==10){
                
                fireFailure();
                
                isWorked = false;
                
            }
            
        }
        
    }
    
    public boolean isFailure()
    {
        
        return failureDate != null;
        
    }

    public Date getFailureDate() {
        return failureDate;
    }
    
    
    
    private void fireFailure(){
        
        failureDate = new Date();
        
        InstallationEngineEvent event = new InstallationEngineEvent(this);
        
        for(InstallationEngineListener listener: listOfInstallationEngineListener){
                            
            listener.failureOccured(event);
            
        }
        
    }
        
    
}
