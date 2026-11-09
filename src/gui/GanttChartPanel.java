
package gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.ExecutionSegment;

public class GanttChartPanel extends JPanel {

    private List<ExecutionSegment> executionSegments;

    // Chart colors
    private static final Color TEXT_COLOR = new Color(51, 65, 85);
    private static final Color BORDER_COLOR = new Color(203, 213, 225);
    private static final Color IDLE_COLOR = new Color(226, 232, 240);

    // Process color palette
    private static final Color[] PROCESS_COLORS = {
        new Color(147, 197, 253),
        new Color(134, 239, 172),
        new Color(253, 186, 116),
        new Color(216, 180, 254),
        new Color(253, 224, 71),
        new Color(103, 232, 249),
        new Color(249, 168, 212)
    };

    public GanttChartPanel() {

        executionSegments = new ArrayList<>();

        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR),
                    "Gantt Chart"
                ),
                new EmptyBorder(10, 10, 10, 10)
            )
        );

        setPreferredSize(new Dimension(900, 190));
        setMinimumSize(new Dimension(500, 190));
        setBackground(Color.WHITE);
    }

    public void displayGanttChart(List<ExecutionSegment> segments) {

        executionSegments = new ArrayList<>(segments);

        repaint();
    }

    public void clearChart() {

        executionSegments.clear();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics.create();

        graphics2D.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (executionSegments.isEmpty()) {

            graphics2D.setColor(Color.GRAY);
            graphics2D.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            String message =
                "Run a scheduling algorithm to display the Gantt chart.";

            FontMetrics metrics = graphics2D.getFontMetrics();

            int messageX =
                (getWidth() - metrics.stringWidth(message)) / 2;

            graphics2D.drawString(
                message,
                Math.max(messageX, 15),
                105
            );

            graphics2D.dispose();
            return;
        }

        drawGanttChart(graphics2D);

        graphics2D.dispose();
    }

    private void drawGanttChart(Graphics2D graphics2D) {

        int chartX = 30;
        int chartY = 45;
        int blockHeight = 45;

        int availableWidth = getWidth() - 60;

        int totalTime = executionSegments
            .get(executionSegments.size() - 1)
            .getEndTime();

        if (totalTime <= 0 || availableWidth <= 0) {
            return;
        }

        double scale = (double) availableWidth / totalTime;

        graphics2D.setFont(
            new Font("Segoe UI", Font.BOLD, 12)
        );

        FontMetrics fontMetrics = graphics2D.getFontMetrics();

        // Draw execution blocks
        for (ExecutionSegment segment : executionSegments) {

            int startTime = segment.getStartTime();
            int endTime = segment.getEndTime();

            int blockX = chartX + (int) Math.round(startTime * scale);

            int blockWidth = (int) Math.round(
                (endTime - startTime) * scale
            );

            blockWidth = Math.max(blockWidth, 1);

            String processId = segment.getProcessId();

            Color blockColor;

            if ("IDLE".equals(processId)) {
                blockColor = IDLE_COLOR;
            } else {
                blockColor = getProcessColor(processId);
            }

            // Draw rounded process block
            graphics2D.setColor(blockColor);

            graphics2D.fillRoundRect(
                blockX,
                chartY,
                blockWidth,
                blockHeight,
                8,
                8
            );

            graphics2D.setColor(BORDER_COLOR);

            graphics2D.drawRoundRect(
                blockX,
                chartY,
                blockWidth,
                blockHeight,
                8,
                8
            );

            // Draw process label if enough space is available
            int labelWidth = fontMetrics.stringWidth(processId);

            if (blockWidth >= labelWidth + 12) {

                graphics2D.setColor(TEXT_COLOR);

                int textX = blockX +
                    (blockWidth - labelWidth) / 2;

                int textY = chartY +
                    (blockHeight + fontMetrics.getAscent()) / 2 - 2;

                graphics2D.drawString(
                    processId,
                    textX,
                    textY
                );
            }
        }

        // Draw timeline markers
        graphics2D.setFont(
            new Font("Segoe UI", Font.PLAIN, 12)
        );

        FontMetrics timeMetrics = graphics2D.getFontMetrics();

        graphics2D.setColor(TEXT_COLOR);

        for (int i = 0; i <= executionSegments.size(); i++) {

            int time;

            if (i == executionSegments.size()) {
                time = executionSegments
                    .get(i - 1)
                    .getEndTime();
            } else {
                time = executionSegments
                    .get(i)
                    .getStartTime();
            }

            int markerX = chartX + (int) Math.round(time * scale);

            // Draw vertical tick
            graphics2D.setColor(TEXT_COLOR);

            graphics2D.drawLine(
                markerX,
                chartY + blockHeight,
                markerX,
                chartY + blockHeight + 7
            );

            // Draw time label
            String timeLabel = String.valueOf(time);

            int labelX = markerX -
                timeMetrics.stringWidth(timeLabel) / 2;

            graphics2D.drawString(
                timeLabel,
                labelX,
                chartY + blockHeight + 25
            );
        }

        // Draw timeline baseline
        graphics2D.setColor(BORDER_COLOR);

        graphics2D.drawLine(
            chartX,
            chartY + blockHeight,
            chartX + availableWidth,
            chartY + blockHeight
        );
    }

    private Color getProcessColor(String processId) {

        int colorIndex = Math.floorMod(
            processId.hashCode(),
            PROCESS_COLORS.length
        );

        return PROCESS_COLORS[colorIndex];
    }
}
