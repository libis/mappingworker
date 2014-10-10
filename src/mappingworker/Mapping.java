
package mappingworker;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import mappingrule.MappingRulesParser;
import mappingrule.MarcMappingRules;
import utils.LibisUtil;

/**
 *
 * @author NaeemM
 */
public class Mapping {
    private final MappingRulesParser mappingParser;
    
    public Mapping() {
        this.mappingParser = new MappingRulesParser();        
    }
 
    public boolean mappRecords(String requestDir, String sourceFormat, String targetFormat) throws IOException{                
        
        List<MarcMappingRules> mappingRulesList = this.mappingParser.marcMappingCommandParser(requestDir);       
        Gson gson = new Gson();
        
        if(sourceFormat.equals("MARC") && targetFormat.equals("EDM")){                                
            LibisUtil util = new LibisUtil();
            for(Object item : mappingRulesList){
                 MarcMappingRules rule = (MarcMappingRules) item;
                 String json = gson.toJson(item);
                 System.out.println(util.prettyPrintJson(json));            
            }              
        }

        if(sourceFormat.equals("MARC") && targetFormat.equals("EDM")){             
        }

        if(sourceFormat.equals("LIDO") && targetFormat.equals("EDM")){             
        }

        if(sourceFormat.equals("CAJSON") && targetFormat.equals("OMJSON")){             
        }        

        return true;
    }
}
