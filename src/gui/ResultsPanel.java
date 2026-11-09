
package gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.SchedulingResult;

public class ResultsPanel extends JPanel {

    private DefaultTableModel tableModel;

    private JLabel averageWaitingTimeLabel;
    private JLabel averageTurnaroundTimeLabel;
    private JLabel averageResponseTimeLabel;

    public ResultsPanel() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Scheduling Results"));
        setPreferredSize(new Dimension(1000, 300));

        // Create results table
        String[] columnNames = {
            "Process ID",
            "Arrival Time",
            "Burst Time",
            "Start Time",
            "Completion Time",
            "Waiting Time",
            "Turnaround Time",
            "Response Time"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);

        // Create average metrics labels
        averageWaitingTimeLabel = new JLabel("Average Waiting Time: 0.00");
        averageTurnaroundTimeLabel = new JLabel("Average Turnaround Time: 0.00");
        averageResponseTimeLabel = new JLabel("Average Response Time: 0.00");

        JPanel metricsPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        metricsPanel.add(averageWaitingTimeLabel);
        metricsPanel.add(averageTurnaroundTimeLabel);
        metricsPanel.add(averageResponseTimeLabel);

        // Add components
        add(scrollPane, BorderLayout.CENTER);
        add(metricsPanel, BorderLayout.SOUTH);
    }

    public void displayResults(List<SchedulingResult> results) {

        tableModel.setRowCount(0);

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;

        for (SchedulingResult result : results) {

            tableModel.addRow(new Object[] {
                result.getProcessId(),
                result.getArrivalTime(),
                result.getBurstTime(),
                result.getStartTime(),
                result.getCompletionTime(),
                result.getWaitingTime(),
                result.getTurnaroundTime(),
                result.getResponseTime()
            });

            totalWaitingTime += result.getWaitingTime();
            totalTurnaroundTime += result.getTurnaroundTime();
            totalResponseTime += result.getResponseTime();
        }

        int processCount = results.size();

        if (processCount > 0) {

            averageWaitingTimeLabel.setText(
                String.format("Average Waiting Time: %.2f",
                    totalWaitingTime / processCount)
            );

            averageTurnaroundTimeLabel.setText(
                String.format("Average Turnaround Time: %.2f",
                    totalTurnaroundTime / processCount)
            );

            averageResponseTimeLabel.setText(
                String.format("Average Response Time: %.2f",
                    totalResponseTime / processCount)
            );

        } else {
            clearResults();
        }
    }

    public void clearResults() {

        tableModel.setRowCount(0);

        averageWaitingTimeLabel.setText("Average Waiting Time: 0.00");
        averageTurnaroundTimeLabel.setText("Average Turnaround Time: 0.00");
        averageResponseTimeLabel.setText("Average Response Time: 0.00");
    }
}
