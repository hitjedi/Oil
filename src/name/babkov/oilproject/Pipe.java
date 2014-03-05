package name.babkov.oilproject;

import java.util.ArrayList;
import java.util.List;
import name.babkov.oilproject.event.PipeEvent;
import name.babkov.oilproject.listener.PipeListener;

public class Pipe {

    private final double thresh;
    
    private double volume;
    
    private List<PipeListener> listOfPipeListener;

    public Pipe(double thresh) {
        
        this.volume = 0;
        
        this.thresh = thresh;
        
        this.listOfPipeListener = new ArrayList<PipeListener>();
        
    }       
    
    public double getVolume() {
        return volume;
    }
    
    
    
    public synchronized void addPipeListener(PipeListener listener){
        
        listOfPipeListener.add(listener);
        
    }
    
    public synchronized void removePipeListener(PipeListener listener){
        
        listOfPipeListener.remove(listener);
        
    }
        
    
    
    public synchronized void addOilToPipe(double volume){

        if(this.volume != thresh){
            
            if(this.volume+volume>thresh){
            
                this.volume = thresh;                           
                
                firePipeEvent();
            
            }    
            else{
            
                this.volume+= volume;
                
                System.out.println("Volume in pipe = " + String.format("%6.2f",this.volume));
            }
            
        }                                        
    }
    
    private void firePipeEvent(){
        
        PipeEvent event = new PipeEvent(this);
        
        for(PipeListener listener:listOfPipeListener){
            
            listener.pipeFulled(event);
            
        }
        
    }
}
