import java.util.*;
import java.util.stream.Collectors;

public class DNASequenceAnalyzer {
    private static final int PATTERN_LENGTH = 3;
    private HashTable patternTable;
    private BinarySearchTree frequencyTree;
    private AminoAcidMapper aminoAcidMapper;
    private boolean initialized = false;

    public DNASequenceAnalyzer() {
        aminoAcidMapper = new AminoAcidMapper();
    }

    public void processDNA(String dnaSequence) {
        if (dnaSequence == null || dnaSequence.length() < PATTERN_LENGTH) {
            throw new IllegalArgumentException("La secuencia de ADN es demasiado corta");
        }

        patternTable = new HashTable(dnaSequence.length() / 2); // Tamaño inicial
        frequencyTree = new BinarySearchTree();

        for (int i = 0; i <= dnaSequence.length() - PATTERN_LENGTH; i++) {
            String pattern = dnaSequence.substring(i, i + PATTERN_LENGTH);
            
            // Verificar que el patrón solo contenga A, T, C, G
            if (!pattern.matches("[ATCG]+")) {
                continue;
            }
            
            patternTable.insert(pattern, i);
            
            // Actualizar árbol de frecuencias
            int currentCount = patternTable.getCount(pattern);
            frequencyTree.insertOrUpdate(pattern, currentCount);
            
            // Mapear a aminoácidos
            aminoAcidMapper.processTriplet(pattern);
        }
        
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public Map<String, Integer> getPatternFrequency() {
        return patternTable != null ? patternTable.getAllPatternsWithCounts() : Collections.emptyMap();
    }

    public Map<String, List<Integer>> getPatternLocations() {
        return patternTable != null ? patternTable.getAllPatternsWithLocations() : Collections.emptyMap();
    }

    public List<String> getPatternsByFrequency() {
        if (frequencyTree == null) return Collections.emptyList();
        return frequencyTree.getPatternsInOrder();
    }

    public List<String> getAllPatternsSorted() {
        if (patternTable == null) return Collections.emptyList();
        return patternTable.getAllPatterns().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public String getMostFrequentPattern() {
        return frequencyTree != null ? frequencyTree.getMaxPattern() : "";
    }

    public String getLeastFrequentPattern() {
        return frequencyTree != null ? frequencyTree.getMinPattern() : "";
    }

    public Map<String, List<String>> getCollisionReport() {
        return patternTable != null ? patternTable.getCollisions() : Collections.emptyMap();
    }

    public Map<String, Map<String, Integer>> getAminoAcidReport() {
        return aminoAcidMapper != null ? aminoAcidMapper.getAminoAcidReport() : Collections.emptyMap();
    }
}
