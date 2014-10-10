
package mappingrule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NaeemM
 */
public class MappingRulesParser {

    public List<MarcMappingRules> marcMappingCommandParser(String requestDir) throws IOException {
        List<MarcMappingRules> mappingRulesList = new ArrayList<>();
                
        try (BufferedReader line = new BufferedReader(new FileReader(requestDir + "/mappingrules.csv"))) {
            while (line.ready()) {                
                MarcMappingRules rule = this.getMappingRule(line.readLine().split(","));
                if(rule.command != null)
                    mappingRulesList.add(rule);                                    
            }
        }

        return mappingRulesList;
    }
    
    private MarcMappingRules getMappingRule(String [] parts )  {
        MarcMappingRules rule = new MarcMappingRules();
        switch(parts[0]){
         case "COPY":
            rule.command        = parts[0];
            rule.marcElement    = parts[1];
            rule.toElement      = parts[2];             
            break;            

         case "APPEND":
            rule.command        = parts[0];
            rule.marcElement    = parts[1];
            rule.toElement      = parts[3]; 
            rule.fields.put("appendtext", parts[2]);
            break; 

         case "PREPEND":
            rule.command        = parts[0];
            rule.marcElement    = parts[1];
            rule.toElement      = parts[3];  
            rule.fields.put("prependtext", parts[2]);
            break; 

         case "SPLIT":
            rule.command        = parts[0];
            rule.marcElement    = parts[1];
            rule.toElement      = parts[3];   
            rule.fields.put("splitby", parts[2]);
            break; 

         case "COMBINE":
            rule.command        = parts[0];
            rule.marcElement    = parts[2];
            rule.toElement      = parts[4];    
            rule.fields.put("separatorstart",   parts[1]);
            rule.fields.put("separatorend",     parts[3]);
            break; 

         case "LIMIT":
            rule.command        = parts[0];
            rule.marcElement    = parts[1];
            rule.toElement      = parts[3];    
            rule.fields.put("limitto",   parts[2]);
            break; 

         case "PUT":
            rule.command        = parts[0];
            rule.marcElement    = null;
            rule.toElement      = parts[2];  
            rule.fields.put("puttext",   parts[1]);
            break; 

         case "REPLACE":
            rule.command        = parts[0];
            rule.marcElement    = parts[1];
            rule.toElement      = parts[4];   
            rule.fields.put("replace",   parts[2]);
            rule.fields.put("replaceby",   parts[3]);
            break;              
        }
        return rule;
    }
    
}
