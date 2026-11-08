
import java.util.ArrayList;
import java.util.List;

import algorithms.SJF;
import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class SJFTest {

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 2, 3, 1));
        processes.add(new Process("P2", 8, 2, 2));

        SJF sjf = new SJF();

        List<SchedulingResult> results = sjf.schedule(processes);

        System.out.println("SJF Scheduling Results");
        System.out.println("--------------------------------");

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (SchedulingResult result : results) {

            System.out.println(
                    result.getProcessId()
                    + " | AT: " + result.getArrivalTime()
                    + " | BT: " + result.getBurstTime()
                    + " | CT: " + result.getCompletionTime()
                    + " | WT: " + result.getWaitingTime()
                    + " | TAT: " + result.getTurnaroundTime()
            );

            totalWaitingTime += result.getWaitingTime();
            totalTurnaroundTime += result.getTurnaroundTime();
        }

        System.out.println("\nExecution Segments:");

        for (ExecutionSegment segment : sjf.getExecutionSegments()) {
            System.out.println(segment);
        }

        System.out.println("\nAverage Waiting Time: "
                + totalWaitingTime / results.size());

        System.out.println("Average Turnaround Time: "
                + totalTurnaroundTime / results.size());
    }
}
