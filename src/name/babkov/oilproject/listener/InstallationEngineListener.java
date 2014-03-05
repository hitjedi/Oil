package name.babkov.oilproject.listener;

import java.util.EventObject;

public interface InstallationEngineListener {

    void failureOccured(EventObject event);
    
}
