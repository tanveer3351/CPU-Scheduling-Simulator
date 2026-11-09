
package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class InputPanel extends JPanel {

    private JTextField processIdField;
    private JTextField arrivalTimeField;
    private JTextField burstTimeField;
    private JTextField priorityField;

    private DefaultTableModel tableModel;
    private JTable processTable;

    public InputPanel() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Process Input"));
        setPreferredSize(new Dimension(1000, 260));

        // Create input form
        JPanel inputForm = new JPanel(new GridLayout(2, 4, 10, 5));

        processIdField = new JTextField();
        arrivalTimeField = new JTextField();
        burstTimeField = new JTextField();
        priorityField = new JTextField();

        inputForm.add(new JLabel("Process ID:"));
        inputForm.add(new JLabel("Arrival Time:"));
        inputForm.add(new JLabel("Burst Time:"));
        inputForm.add(new JLabel("Priority:"));

        inputForm.add(processIdField);
        inputForm.add(arrivalTimeField);
        inputForm.add(burstTimeField);
        inputForm.add(priorityField);

        // Create process table
        String[] columnNames = {
            "Process ID", "Arrival Time", "Burst Time", "Priority", "Action"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        processTable = new JTable(tableModel);
        processTable.setRowHeight(30);

        // Add Edit and Delete buttons
        processTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        processTable.getColumn("Action").setCellEditor(new ButtonEditor());
        processTable.getColumn("Action").setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(processTable);

        // Create buttons
        JButton addButton = new JButton("Add Process");
        JButton clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(event -> addProcess());
        clearButton.addActionListener(event -> clearInputs());

        // Organize panel
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        topPanel.add(inputForm, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addProcess() {

        try {

            String processId = processIdField.getText().trim();

            int arrivalTime = Integer.parseInt(
                arrivalTimeField.getText().trim()
            );

            int burstTime = Integer.parseInt(
                burstTimeField.getText().trim()
            );

            int priority = Integer.parseInt(
                priorityField.getText().trim()
            );

            validateProcess(processId, arrivalTime, burstTime);

            // Check for duplicate process IDs
            for (int row = 0; row < tableModel.getRowCount(); row++) {

                if (processId.equalsIgnoreCase(
                    tableModel.getValueAt(row, 0).toString()
                )) {
                    throw new IllegalArgumentException(
                        "Process ID already exists."
                    );
                }
            }

            tableModel.addRow(new Object[] {
                processId, arrivalTime, burstTime, priority, "Actions"
            });

            clearInputs();

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
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void validateProcess(
            String processId,
            int arrivalTime,
            int burstTime) {

        if (processId.isEmpty()) {
            throw new IllegalArgumentException(
                "Process ID cannot be empty."
            );
        }

        if (arrivalTime < 0 || burstTime <= 0) {
            throw new IllegalArgumentException(
                "Arrival time must be non-negative and burst time must be positive."
            );
        }
    }

    private void editProcess(int row) {

        String oldProcessId = tableModel.getValueAt(row, 0).toString();

        JTextField editProcessId = new JTextField(oldProcessId);
        JTextField editArrivalTime = new JTextField(
            tableModel.getValueAt(row, 1).toString()
        );
        JTextField editBurstTime = new JTextField(
            tableModel.getValueAt(row, 2).toString()
        );
        JTextField editPriority = new JTextField(
            tableModel.getValueAt(row, 3).toString()
        );

        JPanel editPanel = new JPanel(new GridLayout(4, 2, 8, 8));

        editPanel.add(new JLabel("Process ID:"));
        editPanel.add(editProcessId);

        editPanel.add(new JLabel("Arrival Time:"));
        editPanel.add(editArrivalTime);

        editPanel.add(new JLabel("Burst Time:"));
        editPanel.add(editBurstTime);

        editPanel.add(new JLabel("Priority:"));
        editPanel.add(editPriority);

        int option = JOptionPane.showConfirmDialog(
            this,
            editPanel,
            "Edit Process",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        try {

            String processId = editProcessId.getText().trim();

            int arrivalTime = Integer.parseInt(
                editArrivalTime.getText().trim()
            );

            int burstTime = Integer.parseInt(
                editBurstTime.getText().trim()
            );

            int priority = Integer.parseInt(
                editPriority.getText().trim()
            );

            validateProcess(processId, arrivalTime, burstTime);

            // Check duplicate IDs, excluding the current row
            for (int currentRow = 0;
                 currentRow < tableModel.getRowCount();
                 currentRow++) {

                if (currentRow != row &&
                    processId.equalsIgnoreCase(
                        tableModel.getValueAt(currentRow, 0).toString()
                    )) {

                    throw new IllegalArgumentException(
                        "Another process already uses this ID."
                    );
                }
            }

            // Update the existing row
            tableModel.setValueAt(processId, row, 0);
            tableModel.setValueAt(arrivalTime, row, 1);
            tableModel.setValueAt(burstTime, row, 2);
            tableModel.setValueAt(priority, row, 3);

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
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearInputs() {

        processIdField.setText("");
        arrivalTimeField.setText("");
        burstTimeField.setText("");
        priorityField.setText("");

        processIdField.requestFocus();
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    // Render both buttons inside the table
    private class ButtonRenderer extends JPanel
            implements TableCellRenderer {

        private JButton editButton;
        private JButton deleteButton;

        public ButtonRenderer() {

            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            return this;
        }
    }

    // Handle Edit and Delete actions
    private class ButtonEditor extends AbstractCellEditor
            implements TableCellEditor {

        private JPanel buttonPanel;
        private JButton editButton;
        private JButton deleteButton;

        private int selectedRow;

        public ButtonEditor() {

            buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 5, 0)
            );

            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);

            editButton.addActionListener(event -> {

                fireEditingStopped();
                editProcess(selectedRow);
            });

            deleteButton.addActionListener(event -> {

                fireEditingStopped();

                if (selectedRow >= 0 &&
                    selectedRow < tableModel.getRowCount()) {

                    tableModel.removeRow(selectedRow);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column) {

            selectedRow = table.convertRowIndexToModel(row);

            return buttonPanel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Actions";
        }
    }
}
