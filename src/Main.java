
import algorithms.FCFS;
import java.util.ArrayList;
import java.util.List;
import model.Process;
import model.SchedulingResult;

public class Main {

    public static void main(String[] args) {

        // Create process list
        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 3, 1));
        processes.add(new Process("P2", 5, 2, 2));
        processes.add(new Process("P3", 6, 4, 3));

        // Create FCFS scheduler
        FCFS fcfs = new FCFS();

        // Execute scheduling
        List<SchedulingResult> results
                = fcfs.schedule(processes);

        // Display results
        System.out.println("\nFCFS CPU Scheduling");
        System.out.println("----------------------------------------------");

        System.out.printf(
                "%-10s %-5s %-5s %-5s %-5s %-5s %-5s%n",
                "Process", "AT", "BT", "ST", "CT", "TAT", "WT"
        );

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (SchedulingResult result : results) {

            System.out.printf(
                    "%-10s %-5d %-5d %-5d %-5d %-5d %-5d%n",
                    result.getProcessId(),
                    result.getArrivalTime(),
                    result.getBurstTime(),
                    result.getStartTime(),
                    result.getCompletionTime(),
                    result.getTurnaroundTime(),
                    result.getWaitingTime()
            );

            totalWaitingTime += result.getWaitingTime();
            totalTurnaroundTime += result.getTurnaroundTime();
        }

        // Calculate averages
        int numberOfProcesses = results.size();

        if (numberOfProcesses > 0) {

            System.out.println("----------------------------------------------");

            System.out.printf(
                    "Average Waiting Time: %.2f%n",
                    totalWaitingTime / numberOfProcesses
            );

            System.out.printf(
                    "Average Turnaround Time: %.2f%n",
                    totalTurnaroundTime / numberOfProcesses
            );
        }
    }
}
