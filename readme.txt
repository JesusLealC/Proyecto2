+-------------------+       +-----------------------+
|    MainFrame      |       | DNASequenceAnalyzer   |
+-------------------+       +-----------------------+
| - analyzer        |<>---->| - patternTable: HashTable |
| - outputArea      |       | - frequencyTree: BST  |
| - patternComboBox |       | - aminoAcidMapper     |
+-------------------+       +-----------------------+
        |                           |
        |                           |
        v                           v
+-------------------+       +-----------------------+
|    HashTable      |       | BinarySearchTree      |
+-------------------+       +-----------------------+
| - table: List[]   |       | - root: Node          |
| - size: int       |       +-----------------------+
| - collisionMap    |                   |
+-------------------+                   |
        |                               v
        |                       +---------------+
        |                       |     Node      |
        |                       +---------------+
        |                       | - pattern     |
        |                       | - count      |
        |                       | - left, right|
        |                       +---------------+
        v
+-------------------+
|  AminoAcidMapper  |
+-------------------+
| - aminoAcidReport |
+-------------------+
