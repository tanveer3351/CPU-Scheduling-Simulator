package model;

public class ExecutionSegment {

    private final String processId;
    private final int startTime;
    private final int endTime;

    // Constructor
    public ExecutionSegment(
            String processId,
            int startTime,
            int endTime) {

        if (processId == null || processId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Process ID cannot be empty"
            );
        }

        if (startTime < 0) {
            throw new IllegalArgumentException(
                    "Start time cannot be negative"
            );
        }

        if (endTime <= startTime) {
            throw new IllegalArgumentException(
                    "End time must be greater than start time"
            );
        }

        this.processId = processId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter methods
    public String getProcessId() {
        return processId;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    // Calculate execution duration
    public int getDuration() {
        return endTime - startTime;
    }

    // Display execution segment
    @Override
    public String toString() {
        return processId + " [" + startTime + " - " + endTime + "]";
    }
}