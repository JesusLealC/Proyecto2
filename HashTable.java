import java.util.*;

public class HashTable {
    private static final int PRIME = 31;
    private List<HashEntry>[] table;
    private int size;
    private Map<String, List<String>> collisionMap;

    public HashTable(int initialCapacity) {
        table = new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            table[i] = new LinkedList<>();
        }
        collisionMap = new HashMap<>();
    }

    private int hash(String pattern) {
        int hash = 0;
        for (int i = 0; i < pattern.length(); i++) {
            hash = PRIME * hash + pattern.charAt(i);
        }
        return Math.abs(hash % table.length);
    }

    public void insert(String pattern, int position) {
        int hash = hash(pattern);
        boolean found = false;
        
        for (HashEntry entry : table[hash]) {
            if (entry.pattern.equals(pattern)) {
                entry.positions.add(position);
                found = true;
                break;
            }
        }
        
        if (!found) {
            HashEntry newEntry = new HashEntry(pattern);
            newEntry.positions.add(position);
            table[hash].add(newEntry);
            
            // Registrar colisión si hay más de un patrón en este hash
            if (table[hash].size() > 1) {
                String hashKey = Integer.toString(hash);
                List<String> patternsInCollision = collisionMap.computeIfAbsent(hashKey, k -> new ArrayList<>());
                
                // Agregar todos los patrones en este hash
                for (HashEntry entry : table[hash]) {
                    if (!patternsInCollision.contains(entry.pattern)) {
                        patternsInCollision.add(entry.pattern);
                    }
                }
            }
        }
    }

    public int getCount(String pattern) {
        int hash = hash(pattern);
        for (HashEntry entry : table[hash]) {
            if (entry.pattern.equals(pattern)) {
                return entry.positions.size();
            }
        }
        return 0;
    }

    public Set<String> getAllPatterns() {
        Set<String> patterns = new HashSet<>();
        for (List<HashEntry> bucket : table) {
            for (HashEntry entry : bucket) {
                patterns.add(entry.pattern);
            }
        }
        return patterns;
    }

    public Map<String, Integer> getAllPatternsWithCounts() {
        Map<String, Integer> counts = new HashMap<>();
        for (List<HashEntry> bucket : table) {
            for (HashEntry entry : bucket) {
                counts.put(entry.pattern, entry.positions.size());
            }
        }
        return counts;
    }

    public Map<String, List<Integer>> getAllPatternsWithLocations() {
        Map<String, List<Integer>> locations = new HashMap<>();
        for (List<HashEntry> bucket : table) {
            for (HashEntry entry : bucket) {
                locations.put(entry.pattern, new ArrayList<>(entry.positions));
            }
        }
        return locations;
    }

    public Map<String, List<String>> getCollisions() {
        return collisionMap;
    }

    private static class HashEntry {
        String pattern;
        List<Integer> positions;

        public HashEntry(String pattern) {
            this.pattern = pattern;
            this.positions = new ArrayList<>();
        }
    }
}
