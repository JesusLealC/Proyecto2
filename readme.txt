
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

Profe, este proyecto implementa un analizador de secuencias de ADN que identifica patrones de tres nucleótidos, calcula su frecuencia y ubicación, y los relaciona con los aminoácidos correspondientes. La solución utiliza una tabla hash para almacenar los patrones y un árbol binario de búsqueda para ordenarlos por frecuencia, cumpliendo con los requisitos de eficiencia especificados.

Integrantes del Equipo Jesus Leal y Oscar Ezeiza Prof C. Guillen Ejecutar el programa desde NetBeans o el archivo JAR generado

Utilizar el botón "Cargar Archivo" para seleccionar un archivo .txt con la secuencia de ADN Explorar las diferentes funcionalidades: Listar patrones ordenados por frecuencia Buscar un patrón específico Identificar patrones más/menos frecuentes Generar reporte de colisiones Ver relación entre tripletas y aminoácidos
