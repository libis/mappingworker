
package mappingrule;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NaeemM
 */
public class MappingRules {
    public String command;
    public String toElement;           //****start with marc to edm, used edmElement in php version
    public Map<String, String> fields;

    public MappingRules() {
        this.fields = new HashMap<>();
    }
    
    
}
