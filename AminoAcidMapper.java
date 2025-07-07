import java.util.*;

public class AminoAcidMapper {
    private static final Map<String, String> DNA_TO_RNA = Map.of(
        "A", "A",
        "T", "U",
        "C", "C",
        "G", "G"
    );
    
    private static final Map<String, String> RNA_TO_AMINO_ACID = createAminoAcidMap();
    private Map<String, Map<String, Integer>> aminoAcidReport;
    
    public AminoAcidMapper() {
        aminoAcidReport = new HashMap<>();
    }
    
    private static Map<String, String> createAminoAcidMap() {
        Map<String, String> map = new HashMap<>();
        // Tabla de codones a aminoácidos (simplificada)
        map.put("UUU", "Fenilalanina (Phe/F)");
        map.put("UUC", "Fenilalanina (Phe/F)");
        map.put("UUA", "Leucina (Leu/L)");
        map.put("UUG", "Leucina (Leu/L)");
        map.put("CUU", "Leucina (Leu/L)");
        map.put("CUC", "Leucina (Leu/L)");
        map.put("CUA", "Leucina (Leu/L)");
        map.put("CUG", "Leucina (Leu/L)");
        map.put("AUU", "Isoleucina (Ile/I)");
        map.put("AUC", "Isoleucina (Ile/I)");
        map.put("AUA", "Isoleucina (Ile/I)");
        map.put("AUG", "Metionina (Inicio) (Met/M)");
        map.put("GUU", "Valina (Val/V)");
        map.put("GUC", "Valina (Val/V)");
        map.put("GUA", "Valina (Val/V)");
        map.put("GUG", "Valina (Val/V)");
        map.put("UCU", "Serina (Ser/S)");
        map.put("UCC", "Serina (Ser/S)");
        map.put("UCA", "Serina (Ser/S)");
        map.put("UCG", "Serina (Ser/S)");
        map.put("CCU", "Prolina (Pro/P)");
        map.put("CCC", "Prolina (Pro/P)");
        map.put("CCA", "Prolina (Pro/P)");
        map.put("CCG", "Prolina (Pro/P)");
        map.put("ACU", "Treonina (Thr/T)");
        map.put("ACC", "Treonina (Thr/T)");
        map.put("ACA", "Treonina (Thr/T)");
        map.put("ACG", "Treonina (Thr/T)");
        map.put("GCU", "Alanina (Ala/A)");
        map.put("GCC", "Alanina (Ala/A)");
        map.put("GCA", "Alanina (Ala/A)");
        map.put("GCG", "Alanina (Ala/A)");
        map.put("UAU", "Tirosina (Tyr/Y)");
        map.put("UAC", "Tirosina (Tyr/Y)");
        map.put("UAA", "STOP (Ocre)");
        map.put("UAG", "STOP (Ámbar)");
        map.put("CAU", "Histidina (His/H)");
        map.put("CAC", "Histidina (His/H)");
        map.put("CAA", "Glutamina (Gln/Q)");
        map.put("CAG", "Glutamina (Gln/Q)");
        map.put("AAU", "Asparagina (Asn/N)");
        map.put("AAC", "Asparagina (Asn/N)");
        map.put("AAA", "Lisina (Lys/K)");
        map.put("AAG", "Lisina (Lys/K)");
        map.put("GAU", "Ácido Aspártico (Asp/D)");
        map.put("GAC", "Ácido Aspártico (Asp/D)");
        map.put("GAA", "Ácido Glutámico (Glu/E)");
        map.put("GAG", "Ácido Glutámico (Glu/E)");
        map.put("UGU", "Cisteína (Cys/C)");
        map.put("UGC", "Cisteína (Cys/C)");
        map.put("UGA", "STOP (Ópalo)");
        map.put("UGG", "Triptófano (Trp/W)");
        map.put("CGU", "Arginina (Arg/R)");
        map.put("CGC", "Arginina (Arg/R)");
        map.put("CGA", "Arginina (Arg/R)");
        map.put("CGG", "Arginina (Arg/R)");
        map.put("AGU", "Serina (Ser/S)");
        map.put("AGC", "Serina (Ser/S)");
        map.put("AGA", "Arginina (Arg/R)");
        map.put("AGG", "Arginina (Arg/R)");
        map.put("GGU", "Glicina (Gly/G)");
        map.put("GGC", "Glicina (Gly/G)");
        map.put("GGA", "Glicina (Gly/G)");
        map.put("GGG", "Glicina (Gly/G)");
        
        return map;
    }
    
    public void processTriplet(String dnaTriplet) {
        if (dnaTriplet.length() != 3) return;
        
        // Convertir ADN a ARN
        StringBuilder rnaTriplet = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            String nucleotide = String.valueOf(dnaTriplet.charAt(i));
            rnaTriplet.append(DNA_TO_RNA.get(nucleotide));
        }
        
        String aminoAcid = RNA_TO_AMINO_ACID.getOrDefault(rnaTriplet.toString(), "Triplete no válido");
        
        // Actualizar reporte
        Map<String, Integer> tripletsForAminoAcid = aminoAcidReport.computeIfAbsent(
            aminoAcid, k -> new HashMap<>());
        
        tripletsForAminoAcid.merge(dnaTriplet, 1, Integer::sum);
    }
    
    public Map<String, Map<String, Integer>> getAminoAcidReport() {
        return aminoAcidReport;
    }
}
