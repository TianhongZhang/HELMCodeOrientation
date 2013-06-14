package org.helm.orientation.notation;

import java.util.List;
import org.helm.notation.MonomerFactory;
import org.helm.notation.model.MoleculeInfo;
import org.helm.notation.model.Monomer;
import org.helm.notation.model.PolymerNode;
import org.helm.notation.tools.ComplexNotationParser;
import org.helm.notation.tools.SimpleNotationParser;

/**
 *
 * @author ZHANGTIANHONG
 */
public class ComplexNotationParserSample {

    public static void main(String[] args) {
        try {
            //initialize default monomer database
            MonomerFactory.getInstance();
            String notation;

            //cyclic peptide
            notation = "PEPTIDE1{A.A.C.G.[dK].E.C.H.A}$PEPTIDE1,PEPTIDE1,3:R3-7:R3$$$";
            validate(notation);
            getMonomerCount(notation);
            getCanonicalNotation(notation);
            getCanonicalSmiles(notation);
            getMoleculeInfo(notation);
            getAminoAcidSequence(notation);

            //peptide-chem conjugate
            notation = "PEPTIDE1{A.G.G.G.[seC].C.K.K.K.K}|CHEM1{MCC}$PEPTIDE1,CHEM1,9:R3-1:R1$$$";
            validate(notation);
            getMonomerCount(notation);
            getCanonicalNotation(notation);
            getCanonicalSmiles(notation);
            getMoleculeInfo(notation);
            getAminoAcidSequence(notation);

            //oligo-Chem Conjugate with dynamic chem modifier
            notation = "RNA1{R(A)P.[mR](A)P}|CHEM1{[*]OCCOCCOCCO[*] |$_R1;;;;;;;;;;;_R3$|}$RNA1,CHEM1,6:R2-1:R1$$$";
            validate(notation);
            getMonomerCount(notation);
            getCanonicalNotation(notation);
            getCanonicalSmiles(notation);
            getMoleculeInfo(notation);           
            getNucleotideSequence(notation);

            //Invalid Notation with unknown Monomer xyz
            notation = "PEPTIDE1{A.A.C.G.[dK].[xyz].E.C.H.A}$$$$";
            validate(notation);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void validate(String notation) {
        System.out.println("/*********************");
        try {
            System.out.println("Testing Notation Validity for: " + notation);
            System.out.println("Notation Format Valid? " + ComplexNotationParser.validateNotationFormat(notation));
            System.out.println("Node String: " + ComplexNotationParser.getAllNodeString(notation));
            System.out.println("Edge String: " + ComplexNotationParser.getAllEdgeString(notation));
            System.out.println("Base Pair String: " + ComplexNotationParser.getAllBasePairString(notation));
            System.out.println("Node Label String: " + ComplexNotationParser.getAllNodeLabelString(notation));
            System.out.println("Others String: " + ComplexNotationParser.getOtherString(notation));
            System.out.println("Notation Valid? " + ComplexNotationParser.validateComplexNotation(notation));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("*********************/");
    }

    private static void getCanonicalSmiles(String notation) {
        System.out.println("/*********************");
        try {
            System.out.println("Testing getCanonicalSmiles for: " + notation);
            System.out.println("Canonical SMILES: " + ComplexNotationParser.getComplexPolymerCanonicalSmiles(notation));
            System.out.println("*********************");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("*********************/");
    }
    
    private static void getCanonicalNotation(String notation) {
        System.out.println("/*********************");
        try {
            System.out.println("Testing getCanonicalNotation for: " + notation);
            System.out.println("Canonical Notation: " + ComplexNotationParser.getCanonicalNotation(notation));
            System.out.println("*********************");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("*********************/");
    }

    private static void getMonomerCount(String notation) {
        System.out.println("/*********************");
        try {
            System.out.println("Testing getMonomerCount for: " + notation);
            System.out.println("Monomer Count: " + ComplexNotationParser.getTotalMonomerCount(notation));
            System.out.println("*********************");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("*********************/");
    }

    private static void getMoleculeInfo(String notation) {
        System.out.println("/*********************");
        try {
            System.out.println("Testing testGetMoleculeInfo for: " + notation);
            MoleculeInfo mi = ComplexNotationParser.getMoleculeInfo(notation);
            System.out.println("MW = " + mi.getMolecularWeight());
            System.out.println("MF = " + mi.getMolecularFormula());
            System.out.println("Mass = " + mi.getExactMass());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("*********************/");
    }
    
    private static void getNucleotideSequence(String notation) {
        System.out.println("/*********************");
        try {
            System.out.println("Testing getNucleotideSequence for: " + notation);
            List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
            for (PolymerNode polymer : polymers) {
                if (polymer.getType().endsWith(Monomer.NUCLIEC_ACID_POLYMER_TYPE)) {
                    String simpleNotation = polymer.getLabel();
                    String seq = SimpleNotationParser.getNucleotideSequence(simpleNotation);
                    System.out.println("Nucleotide Sequence: " + seq);
                }
            }          
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("*********************/");
    }
    
    private static void getAminoAcidSequence(String notation) {
        System.out.println("/*********************");
        try {
            System.out.println("Testing getAminoAcidSequence for: " + notation);
            List<PolymerNode> polymers = ComplexNotationParser.getPolymerNodeList(notation);
            for (PolymerNode polymer : polymers) {
                if (polymer.getType().endsWith(Monomer.PEPTIDE_POLYMER_TYPE)) {
                    String simpleNotation = polymer.getLabel();
                    String seq = SimpleNotationParser.getPeptideSequence(simpleNotation);
                    System.out.println("AA Sequence: " + seq);
                }
            }          
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("*********************/");
    }
}
