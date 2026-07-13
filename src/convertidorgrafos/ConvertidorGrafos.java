package convertidorgrafos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.border.*;

// ==================== CLASE ARISTA ====================
class Arista {
    private int origen;
    private int destino;
    
    public Arista(int origen, int destino) {
        this.origen = origen;
        this.destino = destino;
    }
    
    public int getOrigen() { 
        return origen; 
    }
    
    public int getDestino() { 
        return destino; 
    }
    
    @Override
    public String toString() {
        return "(" + origen + ", " + destino + ")";
    }
}

// ==================== CLASE GRAFO ====================
class Grafo {
    private int[][] matrizAdyacencia;
    private int numVertices;
    private int numAristas;
    private java.util.List<Arista> aristasCache;
    private java.util.List<java.util.List<Integer>> listaAdyacenciaCache;
    private int[][] matrizIncidenciaCache;
    
    public Grafo(int[][] matrizAdyacencia) {
        this.matrizAdyacencia = matrizAdyacencia;
        this.numVertices = matrizAdyacencia.length;
        this.numAristas = contarAristas();
        this.aristasCache = null;
        this.listaAdyacenciaCache = null;
        this.matrizIncidenciaCache = null;
    }
    
    private int contarAristas() {
        int count = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public int[][] getMatrizAdyacencia() {
        return matrizAdyacencia;
    }
    
    public int getNumVertices() {
        return numVertices;
    }
    
    public int getNumAristas() {
        return numAristas;
    }
    
    public java.util.List<java.util.List<Integer>> getListaAdyacencia() {
        if (listaAdyacenciaCache != null) {
            return listaAdyacenciaCache;
        }
        
        java.util.List<java.util.List<Integer>> lista = new java.util.ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            java.util.List<Integer> vecinos = new java.util.ArrayList<>();
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] == 1) {
                    vecinos.add(j);
                }
            }
            lista.add(vecinos);
        }
        listaAdyacenciaCache = lista;
        return lista;
    }
    
    public int[][] getMatrizIncidencia() {
        if (matrizIncidenciaCache != null) {
            return matrizIncidenciaCache;
        }
        
        java.util.List<Arista> aristas = getListaAristas();
        int[][] incidencia = new int[numVertices][numAristas];
        
        for (int a = 0; a < numAristas; a++) {
            Arista arista = aristas.get(a);
            incidencia[arista.getOrigen()][a] = 1;
            incidencia[arista.getDestino()][a] = 1;
        }
        
        matrizIncidenciaCache = incidencia;
        return incidencia;
    }
    
    public java.util.List<Arista> getListaAristas() {
        if (aristasCache != null) {
            return aristasCache;
        }
        
        java.util.List<Arista> aristas = new java.util.ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] == 1) {
                    aristas.add(new Arista(i, j));
                }
            }
        }
        aristasCache = aristas;
        return aristas;
    }
    
    public long getMemoriaMatrizAdyacencia() {
        return (long) numVertices * numVertices * 4;
    }
    
    public long getMemoriaListaAdyacencia() {
        long memoria = 0;
        for (int i = 0; i < numVertices; i++) {
            java.util.List<Integer> lista = getListaAdyacencia().get(i);
            memoria += 24;
            memoria += lista.size() * 4;
            for (Integer val : lista) {
                memoria += 16;
            }
        }
        return memoria;
    }
    
    public long getMemoriaMatrizIncidencia() {
        return (long) numVertices * numAristas * 4;
    }
    
    public long getMemoriaListaAristas() {
        long memoria = 0;
        memoria += 24;
        memoria += numAristas * 4;
        for (Arista a : getListaAristas()) {
            memoria += 24;
        }
        return memoria;
    }
    
    public boolean esDirigido() {
        return false;
    }
}

// ==================== CLASE CONVERSORGRAFO ====================
class ConversorGrafo {
    private Grafo grafo;
    
    public ConversorGrafo(Grafo grafo) {
        this.grafo = grafo;
    }
    
    public java.util.List<java.util.List<Integer>> convertirMatrizALista() {
        return grafo.getListaAdyacencia();
    }
    
    public int[][] convertirMatrizAIncidencia() {
        return grafo.getMatrizIncidencia();
    }
    
    public java.util.List<Arista> convertirMatrizAListaAristas() {
        return grafo.getListaAristas();
    }
    
    public java.util.Map<String, Long> calcularMemoriaTotal() {
        java.util.Map<String, Long> memoria = new java.util.LinkedHashMap<>();
        memoria.put("Matriz de Adyacencia", grafo.getMemoriaMatrizAdyacencia());
        memoria.put("Lista de Adyacencia", grafo.getMemoriaListaAdyacencia());
        memoria.put("Matriz de Incidencia", grafo.getMemoriaMatrizIncidencia());
        memoria.put("Lista de Aristas", grafo.getMemoriaListaAristas());
        return memoria;
    }
    
    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("═══════════════════════════════════════════════════════════════\n");
        reporte.append("           CONVERSIÓN UNIVERSAL DE GRAFOS\n");
        reporte.append("═══════════════════════════════════════════════════════════════\n\n");
        
        reporte.append("DATOS DEL GRAFO\n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        reporte.append("Vértices: ").append(grafo.getNumVertices()).append("\n");
        reporte.append("Aristas: ").append(grafo.getNumAristas()).append("\n");
        reporte.append("Tipo: No dirigido\n\n");
        
        reporte.append("MATRIZ DE ADYACENCIA\n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        int[][] matriz = grafo.getMatrizAdyacencia();
        for (int i = 0; i < matriz.length; i++) {
            reporte.append("  ");
            for (int j = 0; j < matriz[i].length; j++) {
                reporte.append(matriz[i][j]).append(" ");
            }
            reporte.append("\n");
        }
        reporte.append("Memoria: ").append(grafo.getMemoriaMatrizAdyacencia()).append(" bytes\n\n");
        
        reporte.append("LISTA DE ADYACENCIA\n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        java.util.List<java.util.List<Integer>> lista = grafo.getListaAdyacencia();
        for (int i = 0; i < lista.size(); i++) {
            reporte.append("  Vértice ").append(i).append(" -> ");
            java.util.List<Integer> vecinos = lista.get(i);
            if (vecinos.isEmpty()) {
                reporte.append("∅");
            } else {
                reporte.append(vecinos);
            }
            reporte.append("\n");
        }
        reporte.append("Memoria: ").append(grafo.getMemoriaListaAdyacencia()).append(" bytes\n\n");
        
        reporte.append("MATRIZ DE INCIDENCIA\n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        int[][] incidencia = grafo.getMatrizIncidencia();
        if (incidencia.length > 0 && incidencia[0].length > 0) {
            for (int i = 0; i < incidencia.length; i++) {
                reporte.append("  ");
                for (int j = 0; j < incidencia[i].length; j++) {
                    reporte.append(incidencia[i][j]).append(" ");
                }
                reporte.append("\n");
            }
        } else {
            reporte.append("  (Sin aristas)\n");
        }
        reporte.append("Memoria: ").append(grafo.getMemoriaMatrizIncidencia()).append(" bytes\n\n");
        
        reporte.append("LISTA DE ARISTAS\n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        java.util.List<Arista> aristas = grafo.getListaAristas();
        if (aristas.isEmpty()) {
            reporte.append("  (Sin aristas)\n");
        } else {
            for (Arista a : aristas) {
                reporte.append("  ").append(a).append("\n");
            }
        }
        reporte.append("Memoria: ").append(grafo.getMemoriaListaAristas()).append(" bytes\n\n");
        
        reporte.append("═══════════════════════════════════════════════════════════════\n");
        reporte.append("           RESUMEN DE CONSUMO DE MEMORIA\n");
        reporte.append("═══════════════════════════════════════════════════════════════\n");
        java.util.Map<String, Long> memoria = calcularMemoriaTotal();
        for (java.util.Map.Entry<String, Long> entry : memoria.entrySet()) {
            reporte.append(String.format("  %-25s: %8d bytes\n", entry.getKey(), entry.getValue()));
        }
        
        reporte.append("\nRECOMENDACIÓN DE ALMACENAMIENTO\n");
        reporte.append("─────────────────────────────────────────────────────────────\n");
        String mejor = "";
        long menor = Long.MAX_VALUE;
        for (java.util.Map.Entry<String, Long> entry : memoria.entrySet()) {
            if (entry.getValue() < menor) {
                menor = entry.getValue();
                mejor = entry.getKey();
            }
        }
        reporte.append("  La representación más eficiente en memoria es: " + mejor);
        reporte.append(" (" + menor + " bytes)\n");
        
        reporte.append("\n─────────────────────────────────────────────────────────────\n");
        reporte.append("  Convertidor Universal de Grafos v1.0\n");
        reporte.append("  Teoría de Grafos - Implementación en Java\n");
        reporte.append("═══════════════════════════════════════════════════════════════\n");
        
        return reporte.toString();
    }
}

// ==================== CLASE CONTROLADORGRAFO ====================
class ControladorGrafo {
    private VistaGrafo vista;
    private Grafo modelo;
    private ConversorGrafo conversor;
    private static ControladorGrafo instancia;
    
    private ControladorGrafo() {}
    
    public static ControladorGrafo getInstance() {
        if (instancia == null) {
            instancia = new ControladorGrafo();
        }
        return instancia;
    }
    
    public void setVista(VistaGrafo vista) {
        this.vista = vista;
    }
    
    public void procesarMatriz(int[][] matriz) {
        try {
            if (matriz == null || matriz.length == 0) {
                throw new IllegalArgumentException("La matriz no puede estar vacía");
            }
            
            int n = matriz.length;
            for (int i = 0; i < n; i++) {
                if (matriz[i].length != n) {
                    throw new IllegalArgumentException("La matriz debe ser cuadrada");
                }
                for (int j = 0; j < n; j++) {
                    if (matriz[i][j] != 0 && matriz[i][j] != 1) {
                        throw new IllegalArgumentException("La matriz solo debe contener 0 o 1");
                    }
                }
            }
            
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matriz[i][j] != matriz[j][i]) {
                        throw new IllegalArgumentException("La matriz no es simétrica (grafo dirigido no soportado)");
                    }
                }
            }
            
            this.modelo = new Grafo(matriz);
            this.conversor = new ConversorGrafo(modelo);
            
            vista.mostrarResultados(conversor.generarReporte());
            
        } catch (Exception e) {
            vista.mostrarError("Error al procesar la matriz: " + e.getMessage());
        }
    }
    
    public void exportarDatos() {
        if (modelo == null) {
            vista.mostrarError("Primero debe convertir un grafo");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte de grafo");
        fileChooser.setSelectedFile(new File("grafo_reporte.txt"));
        
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                writer.write(conversor.generarReporte());
                vista.mostrarEstado("Datos exportados exitosamente");
            } catch (IOException e) {
                vista.mostrarError("Error al exportar: " + e.getMessage());
            }
        }
    }
}

// ==================== CLASE VISTAGRAFO ====================
class VistaGrafo extends JFrame {
    private JTextField txtVertices;
    private JTextArea txtMatriz;
    private JTextArea areaResultados;
    private JButton btnConvertir;
    private JButton btnExportar;
    private JButton btnLimpiar;
    private JButton btnEjemplo;
    private JLabel lblEstado;
    private ControladorGrafo controlador;
    
    public VistaGrafo() {
        controlador = ControladorGrafo.getInstance();
        controlador.setVista(this);
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Convertidor Universal de Grafos - Teoría de Grafos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel panelEntrada = new JPanel(new BorderLayout(10, 10));
        panelEntrada.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 200), 2), 
            "DATOS DE ENTRADA", 
            TitledBorder.LEFT, 
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        JPanel panelConfig = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelConfig.add(new JLabel("Número de Vértices:"));
        txtVertices = new JTextField(5);
        txtVertices.setFont(new Font("Arial", Font.PLAIN, 14));
        panelConfig.add(txtVertices);
        
        JButton btnGenerarMatriz = new JButton("Generar Matriz");
        btnGenerarMatriz.setFont(new Font("Arial", Font.BOLD, 12));
        btnGenerarMatriz.setBackground(new Color(200, 230, 255));
        btnGenerarMatriz.addActionListener(e -> generarMatriz());
        panelConfig.add(btnGenerarMatriz);
        
        btnEjemplo = new JButton("Cargar Ejemplo");
        btnEjemplo.setFont(new Font("Arial", Font.BOLD, 12));
        btnEjemplo.setBackground(new Color(255, 230, 200));
        btnEjemplo.addActionListener(e -> cargarEjemplo());
        panelConfig.add(btnEjemplo);
        
        panelEntrada.add(panelConfig, BorderLayout.NORTH);
        
        JPanel panelMatriz = new JPanel(new BorderLayout());
        panelMatriz.setBorder(BorderFactory.createTitledBorder("Matriz de Adyacencia"));
        txtMatriz = new JTextArea(8, 30);
        txtMatriz.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtMatriz.setLineWrap(false);
        txtMatriz.setEditable(false);
        txtMatriz.setBackground(new Color(250, 250, 255));
        JScrollPane scrollMatriz = new JScrollPane(txtMatriz);
        scrollMatriz.setPreferredSize(new Dimension(400, 150));
        panelMatriz.add(scrollMatriz, BorderLayout.CENTER);
        
        panelEntrada.add(panelMatriz, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnConvertir = new JButton("CONVERTIR");
        btnConvertir.setFont(new Font("Arial", Font.BOLD, 14));
        btnConvertir.setBackground(new Color(50, 200, 50));
        btnConvertir.setForeground(Color.WHITE);
        btnConvertir.setPreferredSize(new Dimension(150, 40));
        btnConvertir.addActionListener(e -> convertirGrafo());
        
        btnExportar = new JButton("EXPORTAR");
        btnExportar.setFont(new Font("Arial", Font.BOLD, 14));
        btnExportar.setBackground(new Color(50, 150, 255));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setPreferredSize(new Dimension(150, 40));
        btnExportar.addActionListener(e -> controlador.exportarDatos());
        btnExportar.setEnabled(false);
        
        btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpiar.setBackground(new Color(255, 100, 100));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setPreferredSize(new Dimension(150, 40));
        btnLimpiar.addActionListener(e -> limpiarTodo());
        
        panelBotones.add(btnConvertir);
        panelBotones.add(btnExportar);
        panelBotones.add(btnLimpiar);
        
        panelEntrada.add(panelBotones, BorderLayout.SOUTH);
        
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 180, 0), 2), 
            "RESULTADOS DE CONVERSIÓN", 
            TitledBorder.LEFT, 
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        
        areaResultados = new JTextArea();
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaResultados.setEditable(false);
        areaResultados.setBackground(new Color(240, 255, 240));
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setPreferredSize(new Dimension(600, 450));
        panelResultados.add(scrollResultados, BorderLayout.CENTER);
        
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblEstado = new JLabel("Listo. Ingrese los datos y presione CONVERTIR.");
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 12));
        lblEstado.setForeground(new Color(0, 100, 200));
        panelEstado.add(lblEstado);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelEntrada, panelResultados);
        splitPane.setDividerLocation(450);
        
        add(splitPane, BorderLayout.CENTER);
        add(panelEstado, BorderLayout.SOUTH);
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInfo.add(new JLabel("Teoría de Grafos - Convertidor Universal v1.0"));
        add(panelInfo, BorderLayout.NORTH);
        
        setVisible(true);
    }
    
    private void generarMatriz() {
        try {
            int n = Integer.parseInt(txtVertices.getText().trim());
            if (n <= 0 || n > 50) {
                mostrarError("El número de vértices debe estar entre 1 y 50");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    sb.append("0 ");
                }
                sb.append("\n");
            }
            txtMatriz.setText(sb.toString());
            txtMatriz.setEditable(true);
            txtMatriz.setBackground(new Color(255, 255, 230));
            btnExportar.setEnabled(false);
            areaResultados.setText("");
            lblEstado.setText("Matriz generada. Complete con 0 o 1 y presione CONVERTIR.");
            lblEstado.setForeground(new Color(0, 100, 200));
        } catch (NumberFormatException e) {
            mostrarError("Ingrese un número válido para los vértices");
        }
    }
    
    private void cargarEjemplo() {
        txtVertices.setText("4");
        generarMatriz();
        txtMatriz.setText(
            "0 1 1 0\n" +
            "1 0 0 1\n" +
            "1 0 0 0\n" +
            "0 1 0 0\n"
        );
        lblEstado.setText("Ejemplo cargado. Presione CONVERTIR para ver los resultados.");
        lblEstado.setForeground(new Color(0, 150, 0));
    }
    
    private void convertirGrafo() {
        try {
            String texto = txtMatriz.getText().trim();
            if (texto.isEmpty()) {
                mostrarError("Primero debe generar e ingresar la matriz");
                return;
            }
            
            String[] lineas = texto.split("\n");
            int n = lineas.length;
            int[][] matriz = new int[n][n];
            
            for (int i = 0; i < n; i++) {
                String[] valores = lineas[i].trim().split("\\s+");
                if (valores.length != n) {
                    mostrarError("La fila " + (i+1) + " debe tener " + n + " valores");
                    return;
                }
                for (int j = 0; j < n; j++) {
                    try {
                        matriz[i][j] = Integer.parseInt(valores[j]);
                    } catch (NumberFormatException e) {
                        mostrarError("Error en fila " + (i+1) + ", columna " + (j+1) + ": debe ser número");
                        return;
                    }
                }
            }
            
            controlador.procesarMatriz(matriz);
            btnExportar.setEnabled(true);
            
        } catch (Exception e) {
            mostrarError("Error al convertir: " + e.getMessage());
        }
    }
    
    private void limpiarTodo() {
        txtVertices.setText("");
        txtMatriz.setText("");
        txtMatriz.setEditable(false);
        txtMatriz.setBackground(new Color(250, 250, 255));
        areaResultados.setText("");
        btnExportar.setEnabled(false);
        lblEstado.setText("Limpieza completa. Ingrese nuevos datos.");
        lblEstado.setForeground(new Color(0, 100, 200));
    }
    
    public void mostrarResultados(String reporte) {
        areaResultados.setText(reporte);
        lblEstado.setText("Conversión exitosa. Datos listos para exportar.");
        lblEstado.setForeground(new Color(0, 150, 0));
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        lblEstado.setText("ERROR: " + mensaje);
        lblEstado.setForeground(Color.RED);
    }
    
    public void mostrarEstado(String mensaje) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(new Color(0, 100, 200));
    }
}

// ==================== CLASE PRINCIPAL ====================
public class ConvertidorGrafos {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Usar look and feel por defecto
            }
            new VistaGrafo();
        });
    }
}