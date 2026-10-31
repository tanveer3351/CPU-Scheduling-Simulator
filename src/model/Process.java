package model;
public class Process {

    // Process information
    private final String processId;
    private final int arrivalTime;
    private final int burstTime;
    private final int priority;


    // Constructor
    public Process(String processId,
            int arrivalTime,
            int burstTime,
            int priority) {

        if (processId == null || processId.trim().isEmpty()) {
            throw new IllegalArgumentException("Process ID cannot be empty");
        }

        if (arrivalTime < 0) {
            throw new IllegalArgumentException("Arrival time cannot be negative");
        }

        if (burstTime <= 0) {
            throw new IllegalArgumentException("Burst time must be positive");
        }

        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    // Getter methods
    public String getProcessId() {
        return processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    // Display process information
    @Override
    public String toString() {
        return "Process ID: " + processId
                + ", Arrival Time: " + arrivalTime
                + ", Burst Time: " + burstTime
                + ", Priority: " + priority;
    }
}
