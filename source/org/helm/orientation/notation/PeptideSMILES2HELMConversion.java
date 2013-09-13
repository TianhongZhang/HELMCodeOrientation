/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.helm.orientation.notation;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.helm.notation.MonomerException;
import org.helm.notation.MonomerFactory;
import org.helm.notation.StructureException;
import org.helm.notation.peptide.PeptideStructureParser;
import org.jdom.JDOMException;

/**
 *
 * @author ZHANGTIANHONG
 */
public class PeptideSMILES2HELMConversion {

    public static void main(String[] args) throws StructureException {
        try {
            String inFilePath = "C:/Downloads/ChEMBL_peptides.smiles";
            String outFilePath = "C:/Downloads/ChEMBL_peptides.helm";
            if (args.length == 2) {
                inFilePath = args[0];
                outFilePath = args[1];
            }

            MonomerFactory.getInstance().getMonomerDB();
            PeptideStructureParser.getInstance().initAminoAcidLists();


            System.out.println("Reading compounds from file ...");
            List<Compound> cmpdList = read(inFilePath);
            System.out.println("Reading compounds from file ... done, total count:" + cmpdList.size());

            int totalCount = 0;
            int successCount = 0;
            System.out.println("Starting conversion ...");
            for (Compound cmpd : cmpdList) {
                process(cmpd);        
                totalCount++;
                if (null != cmpd.getHelm()) {
                    successCount++;
                }
                System.out.println("\tNumber of compounds processed: " + totalCount);
                System.out.println("\tNumber of compounds converted: " + successCount);
            }
            System.out.println("Conversion ...done");

            System.out.println("Writing result to file ...");
            write(outFilePath, cmpdList);
            System.out.println("Writing result to file ... done, total count:" + cmpdList.size());

        } catch (MonomerException ex) {
            Logger.getLogger(PeptideSMILES2HELMConversion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(PeptideSMILES2HELMConversion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PeptideSMILES2HELMConversion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PeptideSMILES2HELMConversion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static List<Compound> read(String fileName) throws FileNotFoundException, IOException {
        List<Compound> l = new ArrayList<Compound>();
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        int i = 0;
        while (null != line) {
            if (line.trim().length() > 0) {
                i++;
                Compound c = new Compound();
                c.setId(String.valueOf(i));
                c.setSmiles(line.trim());
                l.add(c);
            }
            line = br.readLine();
        }
        br.close();
        fr.close();

        return l;
    }

    private static void write(String fileName, List<Compound> compoundList) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        fw.write("ID\t");
        fw.write("SMILES\t");
        fw.write("HELM\t");
        fw.write("Status\t");
        fw.write("Message\n");

        for (Compound compound : compoundList) {
            fw.write(compound.getId());
            fw.write("\t");

            if (null != compound.getSmiles()) {
                fw.write(compound.getSmiles());
            }
            fw.write("\t");

            if (null != compound.getHelm()) {
                fw.write(compound.getHelm());
            }
            fw.write("\t");


            if (null != compound.getStatus()) {
                fw.write(compound.getStatus());
            }
            fw.write("\t");

            if (null != compound.getMessage()) {
                fw.write(compound.getMessage());
            }

            fw.write("\n");
        }

        fw.close();
    }

    private static void process(Compound compound) {
        String smiles = compound.getSmiles();
        try {
            String helm = PeptideStructureParser.getInstance().smiles2notation(smiles);
            compound.setHelm(helm);
        } catch (Exception ex) {
            compound.setStatus("smi2notation Conversion Failure");
            compound.setMessage(ex.getMessage());
        }
    }

    static class Compound {

        private String id;
        private String smiles;
        private String helm;
        private String status;
        private String message;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHelm() {
            return helm;
        }

        public void setHelm(String helm) {
            this.helm = helm;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSmiles() {
            return smiles;
        }

        public void setSmiles(String smiles) {
            this.smiles = smiles;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
