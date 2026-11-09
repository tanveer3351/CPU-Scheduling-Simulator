
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import model.ExecutionSegment;

public class GanttChartPanel extends JPanel {

    private List<ExecutionSegment> executionSegments;

    public GanttChartPanel() {

        executionSegments = new ArrayList<>();

        setBorder(BorderFactory.createTitledBorder("Gantt Chart"));
        setPreferredSize(new Dimension(900, 180));
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

        if (executionSegments.isEmpty()) {

            graphics.setColor(Color.GRAY);
            graphics.drawString(
                "Run a scheduling algorithm to display the Gantt chart.",
                20,
                90
            );

            return;
        }

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        int chartX = 30;
        int chartY = 55;
        int blockHeight = 45;
        int availableWidth = getWidth() - 60;

        int totalTime = executionSegments
            .get(executionSegments.size() - 1)
            .getEndTime();

        if (totalTime <= 0) {
            return;
        }

        double scale = (double) availableWidth / totalTime;

        for (ExecutionSegment segment : executionSegments) {

            int startTime = segment.getStartTime();
            int endTime = segment.getEndTime();

            int blockX = chartX + (int) (startTime * scale);
            int blockWidth = (int) ((endTime - startTime) * scale);

            blockWidth = Math.max(blockWidth, 1);

            Color blockColor;

            if (segment.getProcessId().equals("IDLE")) {
                blockColor = Color.LIGHT_GRAY;
            } else {
                blockColor = getProcessColor(segment.getProcessId());
            }

            graphics2D.setColor(blockColor);

            graphics2D.fillRect(
                blockX,
                chartY,
                blockWidth,
                blockHeight
            );

            graphics2D.setColor(Color.BLACK);

            graphics2D.drawRect(
                blockX,
                chartY,
                blockWidth,
                blockHeight
            );

            FontMetrics fontMetrics = graphics2D.getFontMetrics();

            String processId = segment.getProcessId();

            int textX = blockX +
                (blockWidth - fontMetrics.stringWidth(processId)) / 2;

            int textY = chartY +
                (blockHeight + fontMetrics.getAscent()) / 2 - 2;

            graphics2D.drawString(processId, textX, textY);

            graphics2D.drawString(
                String.valueOf(startTime),
                blockX - 3,
                chartY + blockHeight + 20
            );

            graphics2D.drawString(
                String.valueOf(endTime),
                blockX + blockWidth - 3,
                chartY + blockHeight + 20
            );
        }
    }

    private Color getProcessColor(String processId) {

        int colorIndex = Math.abs(processId.hashCode());

        Color[] colors = {
            new Color(173, 216, 230),
            new Color(144, 238, 144),
            new Color(255, 218, 185),
            new Color(221, 160, 221),
            new Color(255, 255, 153),
            new Color(176, 196, 222)
        };

        return colors[colorIndex % colors.length];
    }
}
