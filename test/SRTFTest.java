
import java.util.ArrayList;
import java.util.List;

import algorithms.SRTF;
import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class SRTFTest {

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 8, 1));
        processes.add(new Process("P2", 1, 4, 2));
        processes.add(new Process("P3", 2, 2, 3));
        processes.add(new Process("P4", 3, 1, 4));

        SRTF srtf = new SRTF();

        List<SchedulingResult> results = srtf.schedule(processes);

        // Verify number of results
        assert results.size() == 4 : "Incorrect number of results";

        // Verify P1
        SchedulingResult p1 = results.get(0);

        assert p1.getProcessId().equals("P1");
        assert p1.getCompletionTime() == 15 : "Incorrect P1 CT";
        assert p1.getTurnaroundTime() == 15 : "Incorrect P1 TAT";
        assert p1.getWaitingTime() == 7 : "Incorrect P1 WT";
        assert p1.getResponseTime() == 0 : "Incorrect P1 RT";

        // Verify P2
        SchedulingResult p2 = results.get(1);

        assert p2.getProcessId().equals("P2");
        assert p2.getCompletionTime() == 8 : "Incorrect P2 CT";
        assert p2.getTurnaroundTime() == 7 : "Incorrect P2 TAT";
        assert p2.getWaitingTime() == 3 : "Incorrect P2 WT";
        assert p2.getResponseTime() == 0 : "Incorrect P2 RT";

        // Verify P3
        SchedulingResult p3 = results.get(2);

        assert p3.getProcessId().equals("P3");
        assert p3.getCompletionTime() == 4 : "Incorrect P3 CT";
        assert p3.getTurnaroundTime() == 2 : "Incorrect P3 TAT";
        assert p3.getWaitingTime() == 0 : "Incorrect P3 WT";
        assert p3.getResponseTime() == 0 : "Incorrect P3 RT";

        // Verify P4
        SchedulingResult p4 = results.get(3);

        assert p4.getProcessId().equals("P4");
        assert p4.getCompletionTime() == 5 : "Incorrect P4 CT";
        assert p4.getTurnaroundTime() == 2 : "Incorrect P4 TAT";
        assert p4.getWaitingTime() == 1 : "Incorrect P4 WT";
        assert p4.getResponseTime() == 1 : "Incorrect P4 RT";

        // Verify execution segments
        List<ExecutionSegment> segments = srtf.getExecutionSegments();

        assert segments.size() == 6 : "Incorrect number of execution segments";

        assert segments.get(0).getProcessId().equals("P1");
        assert segments.get(1).getProcessId().equals("P2");
        assert segments.get(2).getProcessId().equals("P3");
        assert segments.get(3).getProcessId().equals("P4");
        assert segments.get(4).getProcessId().equals("P2");
        assert segments.get(5).getProcessId().equals("P1");

        System.out.println("SRTF Test Passed!");
    }
}
