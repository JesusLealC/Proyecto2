import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    private DNASequenceAnalyzer analyzer;
    private JTextArea outputArea;
    private JComboBox<String> patternComboBox;

    public MainFrame() {
        analyzer = new DNASequenceAnalyzer();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Analizador de Secuencias de ADN");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton loadButton = new JButton("Cargar Archivo");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });
        
        JButton listButton = new JButton("Listar Patrones");
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPatterns();
            }
        });
        
        JButton searchButton = new JButton("Buscar Patrón");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPattern();
            }
        });
        
        JButton freqButton = new JButton("Patrón Más Frecuente");
        freqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMostFrequentPattern();
            }
        });
        
        JButton collisionButton = new JButton("Reporte de Colisiones");
        collisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCollisionReport();
            }
        });
        
        JButton aminoButton = new JButton("Reporte de Aminoácidos");
        aminoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAminoAcidReport();
            }
        });

        buttonPanel.add(loadButton);
        buttonPanel.add(listButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(freqButton);
        buttonPanel.add(collisionButton);
        buttonPanel.add(aminoButton);

        // ComboBox para búsqueda
        patternComboBox = new JComboBox<>();
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Seleccione un patrón:"), BorderLayout.WEST);
        searchPanel.add(patternComboBox, BorderLayout.CENTER);

        // Área de salida
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Agregar componentes al frame
        add(buttonPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                StringBuilder dnaSequence = new StringBuilder();
                
                while ((line = reader.readLine()) != null) {
                    dnaSequence.append(line.trim().toUpperCase());
                }
                
                analyzer.processDNA(dnaSequence.toString());
                updatePatternComboBox();
                outputArea.setText("Archivo cargado exitosamente.\nLongitud de la secuencia: " + dnaSequence.length());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updatePatternComboBox() {
        patternComboBox.removeAllItems();
        List<String> patterns = analyzer.getAllPatternsSorted();
        for (String pattern : patterns) {
            patternComboBox.addItem(pattern);
        }
    }

    private void listPatterns() {
        if (!analyzer.isInitialized()) {
            outputArea.setText("Primero cargue un archivo de ADN.");
            return;
        }
        
        Map<String, Integer> frequencyMap = analyzer.getPatternFrequency();
        Map<String, List<Integer>> locationMap = analyzer.getPatternLocations();
        
        StringBuilder sb = new StringBuilder();
        sb.append("Patrones encontrados (ordenados por frecuencia):\n");
        
        analyzer.getPatternsByFrequency().forEach(pattern -> {
            sb.append("Patrón: ").append(pattern)
              .append(", Frecuencia: ").append(frequencyMap.get(pattern))
              .append(", Ubicaciones: ").append(locationMap.get(pattern))
              .append("\n");
        });
        
        outputArea.setText(sb.toString());
    }

    private void searchPattern() {
        if (!analyzer.isInitialized()) {
            outputArea.setText("Primero cargue un archivo de ADN.");
            return;
        }
        
        String selectedPattern = (String) patternComboBox.getSelectedItem();
        if (selectedPattern == null) {
            outputArea.setText("No hay patrones para buscar.");
            return;
        }
        
        int frequency = analyzer.getPatternFrequency().get(selectedPattern);
        List<Integer> locations = analyzer.getPatternLocations().get(selectedPattern);
        
        outputArea.setText("Patrón: " + selectedPattern + 
                          "\nFrecuencia: " + frequency + 
                          "\nUbicaciones: " + locations);
    }

    private void showMostFrequentPattern() {
        if (!analyzer.isInitialized()) {
            outputArea.setText("Primero cargue un archivo de ADN.");
            return;
        }
        
        String mostFrequent = analyzer.getMostFrequentPattern();
        String leastFrequent = analyzer.getLeastFrequentPattern();
        
        outputArea.setText("Patrón más frecuente: " + mostFrequent + 
                          " (Frecuencia: " + analyzer.getPatternFrequency().get(mostFrequent) + ")\n" +
                          "Patrón menos frecuente: " + leastFrequent + 
                          " (Frecuencia: " + analyzer.getPatternFrequency().get(leastFrequent) + ")");
    }

    private void showCollisionReport() {
        if (!analyzer.isInitialized()) {
            outputArea.setText("Primero cargue un archivo de ADN.");
            return;
        }
        
        Map<String, List<String>> collisions = analyzer.getCollisionReport();
        if (collisions.isEmpty()) {
            outputArea.setText("No se encontraron colisiones en la tabla hash.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Reporte de Colisiones:\n");
        collisions.forEach((key, patterns) -> {
            sb.append("Hash: ").append(key).append(" -> Patrones: ").append(patterns).append("\n");
        });
        
        outputArea.setText(sb.toString());
    }

    private void showAminoAcidReport() {
        if (!analyzer.isInitialized()) {
            outputArea.setText("Primero cargue un archivo de ADN.");
            return;
        }
        
        Map<String, Map<String, Integer>> aminoAcidReport = analyzer.getAminoAcidReport();
        StringBuilder sb = new StringBuilder();
        sb.append("Reporte de Aminoácidos:\n");
        
        aminoAcidReport.forEach((aminoAcid, triplets) -> {
            sb.append("\nAminoácido: ").append(aminoAcid).append("\n");
            triplets.forEach((triplet, count) -> {
                sb.append("  Triplete: ").append(triplet).append(", Frecuencia: ").append(count).append("\n");
            });
        });
        
        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
