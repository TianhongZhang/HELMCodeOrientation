package org.helm.orientation.notation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import org.helm.notation.MonomerFactory;
import org.helm.notation.model.Monomer;
import org.helm.notation.model.MonomerCache;

/**
 *
 * @author ZHANGTIANHONG
 */
public class MonomerFactorySample {

    public static void main(String[] args) {
        try {
            initMonomerDB();
            
            showMonomerCounts();
            
            saveMonomerDBToFile();
            
            initMonomerDBFromFile("C:\\GitHub\\tmp\\MonomerDBGZEncoded.xml");
            
            showMonomerCounts();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //use default monomer database
    private static void initMonomerDB() throws Exception {
        MonomerFactory.getInstance();
    }
    
    //use provided monomer database file
    private static void initMonomerDBFromFile(String monomerDBFile) throws Exception {
        File file = new File(monomerDBFile);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (null != line) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        fr.close();
        String monomerDBXML = sb.toString();
        
        initMonomerDBFromXML(monomerDBXML);
    }
    
    //use provided monomer database xml document
    private static void initMonomerDBFromXML(String monomerDBXML) throws Exception {
        MonomerCache cache = MonomerFactory.getInstance().buildMonomerCacheFromXML(monomerDBXML);
        MonomerFactory.getInstance().setMonomerCache(cache);
    }
    
    //save monomer database locally
    private static void saveMonomerDBToFile() throws Exception {
        MonomerFactory.getInstance().saveMonomerCache();
    }
    
    private static void showMonomerCounts() throws Exception {
        Map<String, Map<String, Monomer>> monomerDB = MonomerFactory.getInstance().getMonomerDB();
        System.out.println("Total number of PEPTIDE monomers: "+monomerDB.get(Monomer.PEPTIDE_POLYMER_TYPE).size());     
        System.out.println("Total number of RNA monomers: "+monomerDB.get(Monomer.NUCLIEC_ACID_POLYMER_TYPE).size());     
        System.out.println("Total number of CHEM monomers: "+monomerDB.get(Monomer.CHEMICAL_POLYMER_TYPE).size());      
    }
}
