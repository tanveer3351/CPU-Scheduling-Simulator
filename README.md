# CPU Scheduling Simulator

A Java-based CPU Scheduling Simulator designed to demonstrate and compare different CPU scheduling algorithms. The project aims to provide an interactive graphical interface, process execution visualization, Gantt charts, and scheduling performance metrics.

## Project Overview

CPU scheduling is an important function of an operating system. It determines which process gets access to the CPU and in what order.

This project simulates different CPU scheduling algorithms to help students understand how processes are scheduled and how scheduling decisions affect waiting time, turnaround time, and CPU performance.

## Objectives

- Understand and implement different CPU scheduling algorithms.
- Simulate process execution using arrival and burst times.
- Calculate important CPU scheduling metrics.
- Visualize process execution through Gantt charts.
- Compare the performance of different scheduling algorithms.
- Develop a user-friendly desktop application using Java Swing.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| Java Swing | Graphical user interface |
| VS Code | Development environment |
| Git | Version control |
| GitHub | Source code management and collaboration |

## Scheduling Algorithms

The project aims to implement the following five CPU scheduling algorithms:

| Algorithm | Type | Status |
|---|---|---|
| First Come First Served (FCFS) | Non-Preemptive | Completed and tested |
| Shortest Job First (SJF) | Non-Preemptive | In Progress |
| Shortest Remaining Time First (SRTF) | Preemptive | Planned |
| Priority Scheduling | Non-Preemptive | Planned |
| Round Robin (RR) | Preemptive | Planned |

*Implementation statuses will be updated as development progresses.*

## Planned Features

- Add and manage processes.
- Enter process ID, arrival time, burst time, and priority.
- Select a CPU scheduling algorithm.
- Simulate process execution.
- Display a Gantt chart.
- Calculate Completion Time (CT).
- Calculate Turnaround Time (TAT).
- Calculate Waiting Time (WT).
- Calculate Response Time (RT).
- Display average waiting and turnaround times.
- Compare scheduling algorithm performance.
- Provide a graphical interface using Java Swing.

## Project Structure

```text
CPU-Scheduling-Simulator/
├── src/
│   ├── algorithms/
│   │   ├── FCFS.java
│   │   ├── SJF.java
│   │   ├── SRTF.java
│   │   ├── Priority.java
│   │   └── RoundRobin.java
│   ├── engine/
│   │   └── SchedulingEngine.java
│   ├── gui/
│   │   ├── MainFrame.java
│   │   ├── InputPanel.java
│   │   ├── ResultsPanel.java
│   │   └── GanttChartPanel.java
│   ├── model/
│   │   ├── Process.java
│   │   ├── SchedulingResult.java
│   │   └── ExecutionSegment.java
│   └── Main.java
├── test/
├── .gitignore
└── README.md
```

*Note: Some files and directories represent the planned project structure and will be added during development.*

## Getting Started

### Prerequisites

- Java Development Kit (JDK 17 or later recommended).
- Visual Studio Code or another Java IDE.
- Git (optional, for cloning the repository).

### Clone the Repository

```bash
git clone https://github.com/tanveer3351/CPU-Scheduling-Simulator.git
cd CPU-Scheduling-Simulator
```

### Compile and Run

The current FCFS implementation can be compiled using:

```powershell
javac -d out src/model/Process.java src/model/SchedulingResult.java src/algorithms/FCFS.java src/Main.java
```

Run the application:

```powershell
java -cp out Main
```

*These commands run the current console-based FCFS implementation. GUI execution instructions will be added when the Swing interface is completed.*

## Scheduling Metrics

| Metric | Formula | Description |
|---|---|---|
| Completion Time (CT) | End time of execution | Time when a process finishes |
| Turnaround Time (TAT) | CT - AT | Total time from arrival to completion |
| Waiting Time (WT) | TAT - BT | Total time spent waiting |
| Response Time (RT) | First ST - AT | Time until the process first gets CPU access |

Where AT = Arrival Time, BT = Burst Time, and ST = Start Time.

## Sample Input

| Process | Arrival Time | Burst Time | Priority |
|---|---:|---:|---:|
| P1 | 0 | 5 | 1 |
| P2 | 1 | 3 | 2 |
| P3 | 2 | 8 | 3 |

## Expected FCFS Output

| Process | ST | CT | TAT | WT |
|---|---:|---:|---:|---:|
| P1 | 0 | 5 | 5 | 0 |
| P2 | 5 | 8 | 7 | 4 |
| P3 | 8 | 16 | 14 | 6 |

**Average Waiting Time:** 3.33

**Average Turnaround Time:** 8.67

## Team Contributions

| Team Member | Responsibility |
|---|---|
| Tanveer Ali | Project coordination, FCFS, integration and overall development |
| Team Member 2 | SJF implementation |
| Team Member 3 | SRTF implementation |

*Update team member names and responsibilities as tasks are finalized.*

## Future Improvements

- Interactive Gantt chart visualization.
- Step-by-step process execution.
- Algorithm performance comparison charts.
- CPU utilization and throughput calculations.
- Improved graphical interface.
- Additional test cases and validation.

## Learning Outcomes

- Operating System process management.
- CPU scheduling techniques.
- Preemptive and non-preemptive scheduling.
- Java object-oriented programming.
- Data structures and algorithms.
- GUI development using Java Swing.
- Git and GitHub collaboration.

## License

This project is developed for educational purposes as part of an Operating Systems academic project.
