package model;

public class SchedulingResult {

    private final String processId;

    private final int arrivalTime;
    private final int burstTime;

    private final int startTime;
    private final int completionTime;

    private final int turnaroundTime;
    private final int waitingTime;
    private final int responseTime;

    public SchedulingResult(
            String processId,
            int arrivalTime,
            int burstTime,
            int startTime,
            int completionTime) {

        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.startTime = startTime;
        this.completionTime = completionTime;

        this.turnaroundTime = completionTime - arrivalTime;
        this.waitingTime = turnaroundTime - burstTime;
        this.responseTime = startTime - arrivalTime;
    }

    public String getProcessId() {
        return processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getResponseTime() {
        return responseTime;
    }
}
