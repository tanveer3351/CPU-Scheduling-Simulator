package gui;

import engine.SchedulingEngine;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.ExecutionSegment;
import model.Process;
import model.SchedulingResult;

public class MainFrame extends JFrame {

    // Modern color palette
    private static final Color BACKGROUND_COLOR = new Color(241, 245, 249);
    private static final Color NAVY_COLOR = new Color(30, 41, 59);
    private static final Color ACCENT_COLOR = new Color(37, 99, 235);
    private static final Color WHITE_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(51, 65, 85);
    private static final Color BORDER_COLOR = new Color(203, 213, 225);

    private InputPanel inputPanel;
    private ResultsPanel resultsPanel;
    private GanttChartPanel ganttChartPanel;

    private JComboBox<String> algorithmSelector;
    private JTextField timeQuantumField;

    private SchedulingEngine schedulingEngine;

    public MainFrame() {

        // Initialize scheduling engine
        schedulingEngine = new SchedulingEngine();

        // Configure main window
        setTitle("CPU Scheduling Simulator");
        setSize(1100, 850);
        setMinimumSize(new Dimension(950, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Initialize GUI panels
        inputPanel = new InputPanel();
        resultsPanel = new ResultsPanel();
        ganttChartPanel = new GanttChartPanel();

        // Connect Clear All Processes button with output panels
        inputPanel.setClearAllAction(() -> {
            resultsPanel.clearResults();
            ganttChartPanel.clearChart();
        });

        // Apply modern styling
        inputPanel.setBackground(WHITE_COLOR);
        resultsPanel.setBackground(WHITE_COLOR);
        ganttChartPanel.setBackground(WHITE_COLOR);

        styleComponents(inputPanel);
        styleComponents(resultsPanel);

        inputPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER_COLOR, 1, true),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );

        resultsPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER_COLOR, 1, true),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );

        ganttChartPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER_COLOR, 1, true),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );

        // Create algorithm selection controls
        JPanel controlPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 12, 12)
        );

        controlPanel.setBackground(NAVY_COLOR);
        controlPanel.setBorder(new EmptyBorder(5, 12, 5, 12));

        algorithmSelector = new JComboBox<>(new String[]{
            "FCFS",
            "SJF",
            "SRTF",
            "PRIORITY",
            "ROUND ROBIN"
        });

        algorithmSelector.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        algorithmSelector.setPreferredSize(new Dimension(150, 34));

        timeQuantumField = new JTextField("2", 5);
        timeQuantumField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        timeQuantumField.setPreferredSize(new Dimension(75, 34));

        JButton runButton = new JButton("Run Scheduling");
        runButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        runButton.setBackground(ACCENT_COLOR);
        runButton.setForeground(WHITE_COLOR);
        runButton.setFocusPainted(false);
        runButton.setBorder(new EmptyBorder(9, 18, 9, 18));
        runButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel algorithmLabel = new JLabel("Algorithm:");
        algorithmLabel.setForeground(WHITE_COLOR);
        algorithmLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel quantumLabel = new JLabel("Time Quantum:");
        quantumLabel.setForeground(WHITE_COLOR);
        quantumLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        controlPanel.add(algorithmLabel);
        controlPanel.add(algorithmSelector);
        controlPanel.add(quantumLabel);
        controlPanel.add(timeQuantumField);
        controlPanel.add(runButton);

        // Enable quantum field only for Round Robin
        updateQuantumField();

        algorithmSelector.addActionListener(event -> updateQuantumField());

        // Organize results and Gantt chart
        JPanel outputPanel = new JPanel(new BorderLayout(10, 10));
        outputPanel.setBackground(BACKGROUND_COLOR);

        outputPanel.add(resultsPanel, BorderLayout.CENTER);
        outputPanel.add(ganttChartPanel, BorderLayout.SOUTH);

        // Combine input panel and controls
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(BACKGROUND_COLOR);

        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.SOUTH);

        // Add components to main window
        add(topPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

        // Add spacing around the main content
        ((JPanel) getContentPane()).setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        // Run scheduling when button is clicked
        runButton.addActionListener(event -> runScheduling());

        // Center window
        setLocationRelativeTo(null);
    }

    private void updateQuantumField() {

        String selectedAlgorithm
                = algorithmSelector.getSelectedItem().toString();

        boolean isRoundRobin
                = selectedAlgorithm.equals("ROUND ROBIN");

        timeQuantumField.setEnabled(isRoundRobin);

        if (isRoundRobin) {
            timeQuantumField.setBackground(WHITE_COLOR);
        } else {
            timeQuantumField.setBackground(
                    new Color(226, 232, 240)
            );
        }
    }

    private void runScheduling() {

        try {

            // Get the table model once
            DefaultTableModel tableModel = inputPanel.getTableModel();

            // Read processes from input table
            List<Process> processes = new ArrayList<>();

            for (int row = 0; row < tableModel.getRowCount(); row++) {

                String processId = tableModel.getValueAt(row, 0).toString();

                int arrivalTime = Integer.parseInt(
                        tableModel.getValueAt(row, 1).toString()
                );

                int burstTime = Integer.parseInt(
                        tableModel.getValueAt(row, 2).toString()
                );

                int priority = Integer.parseInt(
                        tableModel.getValueAt(row, 3).toString()
                );

                processes.add(new Process(
                        processId,
                        arrivalTime,
                        burstTime,
                        priority
                ));
            }

            // Get selected algorithm
            String selectedAlgorithm
                    = algorithmSelector.getSelectedItem().toString();

            // Read quantum only for Round Robin
            int timeQuantum = 1;

            if (selectedAlgorithm.equals("ROUND ROBIN")) {

                timeQuantum = Integer.parseInt(
                        timeQuantumField.getText().trim()
                );

                if (timeQuantum <= 0) {
                    throw new IllegalArgumentException(
                            "Time Quantum must be greater than zero."
                    );
                }
            }

            // Execute scheduling
            List<SchedulingResult> results
                    = schedulingEngine.schedule(
                            selectedAlgorithm,
                            processes,
                            timeQuantum
                    );

            // Get execution segments
            List<ExecutionSegment> segments
                    = schedulingEngine.getExecutionSegments();

            // Display updated results
            resultsPanel.displayResults(results);

            // Display updated Gantt chart
            ganttChartPanel.displayGanttChart(segments);

        } catch (NumberFormatException exception) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid integer values.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException exception) {

            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "Scheduling Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Apply consistent styling to child components
    private void styleComponents(Container container) {

        for (Component component : container.getComponents()) {

            component.setFont(
                    new Font("Segoe UI", Font.PLAIN, 13)
            );

            if (component instanceof JTable table) {

                table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                table.setRowHeight(30);
                table.setGridColor(BORDER_COLOR);
                table.setSelectionBackground(
                        new Color(219, 234, 254)
                );
                table.setSelectionForeground(TEXT_COLOR);
                table.setBackground(WHITE_COLOR);
                table.setForeground(TEXT_COLOR);

                JTableHeader header = table.getTableHeader();

                if (header != null) {
                    header.setFont(
                            new Font("Segoe UI", Font.BOLD, 13)
                    );
                    header.setBackground(NAVY_COLOR);
                    header.setForeground(WHITE_COLOR);
                    header.setPreferredSize(
                            new Dimension(0, 35)
                    );
                }

            } else if (component instanceof JButton button) {

                button.setFont(
                        new Font("Segoe UI", Font.BOLD, 12)
                );
                button.setBackground(ACCENT_COLOR);
                button.setForeground(WHITE_COLOR);
                button.setFocusPainted(false);
                button.setCursor(
                        new Cursor(Cursor.HAND_CURSOR)
                );

            } else if (component instanceof JTextField field) {

                field.setFont(
                        new Font("Segoe UI", Font.PLAIN, 13)
                );
                field.setPreferredSize(
                        new Dimension(100, 30)
                );
                field.setBorder(
                        BorderFactory.createCompoundBorder(
                                new LineBorder(BORDER_COLOR, 1, true),
                                new EmptyBorder(4, 7, 4, 7)
                        )
                );

            } else if (component instanceof JLabel label) {

                label.setForeground(TEXT_COLOR);
                label.setFont(
                        new Font("Segoe UI", Font.PLAIN, 13)
                );
            }

            if (component instanceof Container child) {
                styleComponents(child);
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
