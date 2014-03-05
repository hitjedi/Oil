package name.babkov.oilproject.listener;

import java.util.EventObject;

public interface PipeListener {

    void pipeFulled(EventObject event);
    
}
