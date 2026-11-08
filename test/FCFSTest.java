import java.util.ArrayList;
import java.util.List;

import algorithms.FCFS;
import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class FCFSTest {

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 2, 3, 1));
        processes.add(new Process("P2", 8, 2, 2));

        FCFS fcfs = new FCFS();

        List<SchedulingResult> results = fcfs.schedule(processes);

        System.out.println("FCFS Scheduling Results");
        System.out.println("-----------------------");

        for (SchedulingResult result : results) {
            System.out.println(
                result.getProcessId()
                + " | CT: " + result.getCompletionTime()
                + " | WT: " + result.getWaitingTime()
                + " | TAT: " + result.getTurnaroundTime()
            );
        }

        System.out.println("\nExecution Segments");
        System.out.println("------------------");

        for (ExecutionSegment segment : fcfs.getExecutionSegments()) {
            System.out.println(segment);
        }
    }
}
