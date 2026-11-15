
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

        // Verify number of results
        assert results.size() == 2 : "Incorrect number of results";

        // Verify P1
        SchedulingResult p1 = results.get(0);

        assert p1.getProcessId().equals("P1") : "Incorrect P1 ID";
        assert p1.getCompletionTime() == 5 : "Incorrect P1 completion time";
        assert p1.getTurnaroundTime() == 3 : "Incorrect P1 turnaround time";
        assert p1.getWaitingTime() == 0 : "Incorrect P1 waiting time";

        // Verify P2
        SchedulingResult p2 = results.get(1);

        assert p2.getProcessId().equals("P2") : "Incorrect P2 ID";
        assert p2.getCompletionTime() == 10 : "Incorrect P2 completion time";
        assert p2.getTurnaroundTime() == 2 : "Incorrect P2 turnaround time";
        assert p2.getWaitingTime() == 0 : "Incorrect P2 waiting time";

        // Verify execution segments
        List<ExecutionSegment> segments = sjf.getExecutionSegments();

        assert segments.size() == 4 : "Incorrect number of execution segments";

        assert segments.get(0).getProcessId().equals("IDLE");
        assert segments.get(1).getProcessId().equals("P1");
        assert segments.get(2).getProcessId().equals("IDLE");
        assert segments.get(3).getProcessId().equals("P2");

        System.out.println("SJF Test Passed!");
    }
}
