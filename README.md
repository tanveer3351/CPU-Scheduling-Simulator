# CPU Scheduling Simulator

A Java-based CPU Scheduling Simulator with a graphical user interface (GUI). This project demonstrates how different CPU scheduling algorithms manage process execution and calculate scheduling performance metrics.

## Features

- Add, edit, and delete processes.
- Clear individual input fields or remove all processes.
- Support for five CPU scheduling algorithms.
- Configurable time quantum for Round Robin scheduling.
- Display scheduling results in a table.
- Calculate average Waiting Time, Turnaround Time, and Response Time.
- Visualize process execution through a Gantt Chart.
- Display CPU idle periods in the Gantt Chart.
- Input validation and duplicate process ID prevention.
- Reserve `IDLE` as a special identifier for CPU idle periods.
- Automatically clear previous results when the process list changes.
- Deterministic tie-breaking rules for consistent scheduling results.
- Optimized SRTF handling of CPU idle periods.

## Scheduling Algorithms

| Algorithm | Type | Description |
|---|---|---|
| FCFS | Non-Preemptive | Executes processes in order of arrival time. |
| SJF | Non-Preemptive | Selects the arrived process with the shortest burst time. |
| SRTF | Preemptive | Executes the process with the shortest remaining time. |
| Priority | Non-Preemptive | Selects the arrived process with the highest priority. |
| Round Robin | Preemptive | Allocates CPU time using a configurable time quantum. |

**Priority convention:** A smaller priority number represents a higher priority. For example, priority 1 is higher than priority 3.

### Tie-Breaking Rules

The simulator uses deterministic tie-breaking rules:

| Algorithm | Scheduling Criteria |
|---|---|
| FCFS | Arrival Time → Process ID |
| SJF | Burst Time → Arrival Time → Process ID |
| SRTF | Remaining Time → Arrival Time → Process ID |
| Priority | Priority Number → Arrival Time → Process ID |
| Round Robin | Arrival Time → Process ID when ordering arrivals |

These rules ensure consistent scheduling results when multiple processes have equal scheduling values.

## Technologies Used

- Java
- Java Swing
- Java AWT
- Object-Oriented Programming (OOP)
- Git and GitHub

## Project Structure

```text
CPU-Scheduling-Simulator/
│
├── .github/
│   └── ISSUE_TEMPLATE/
│
├── src/
│   ├── Main.java
│   │
│   ├── algorithms/
│   │   ├── FCFS.java
│   │   ├── SJF.java
│   │   ├── SRTF.java
│   │   ├── Priority.java
│   │   └── RoundRobin.java
│   │
│   ├── engine/
│   │   └── SchedulingEngine.java
│   │
│   ├── gui/
│   │   ├── MainFrame.java
│   │   ├── InputPanel.java
│   │   ├── ResultsPanel.java
│   │   └── GanttChartPanel.java
│   │
│   └── model/
│       ├── Process.java
│       ├── SchedulingResult.java
│       └── ExecutionSegment.java
│
├── test/
│   ├── ExecutionSegmentTest.java
│   ├── FCFSTest.java
│   ├── SJFTest.java
│   ├── SRTFTest.java
│   ├── PriorityTest.java
│   ├── RoundRobinTest.java
│   └── SchedulingEngineTest.java
│
├── .gitignore
└── README.md
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or later.
- Visual Studio Code or another Java-compatible IDE.
- Git (optional, for cloning the repository).

### Clone the Repository

```bash
git clone https://github.com/tanveer3351/CPU-Scheduling-Simulator.git
```

Navigate to the project directory:

```bash
cd CPU-Scheduling-Simulator
```

### Compile the Project

Run the following command from the project root directory:

```bash
javac -d out src/model/*.java src/algorithms/*.java src/engine/*.java src/gui/*.java
```

### Run the Application

Launch the graphical interface:

```bash
java -cp out gui.MainFrame
```

## How to Use

1. Launch the application.
2. Enter the Process ID, Arrival Time, Burst Time, and Priority.
3. Click **Add Process**.
4. Add more processes as needed.
5. Select a scheduling algorithm.
6. For Round Robin, enter the desired Time Quantum.
7. Click **Run Scheduling**.
8. View the scheduling results, average performance metrics, and Gantt Chart.
9. Use the Edit and Delete buttons to modify individual processes.

## Scheduling Metrics

| Metric | Formula |
|---|---|
| Completion Time (CT) | Time at which a process finishes execution |
| Turnaround Time (TAT) | Completion Time − Arrival Time |
| Waiting Time (WT) | Turnaround Time − Burst Time |
| Response Time (RT) | First Start Time − Arrival Time |

Average metrics are calculated by dividing the sum of the corresponding process values by the total number of processes.

## Testing

The project includes individual test files for all five scheduling algorithms.

The algorithm tests use Java assertions to verify scheduling results, including:

- Completion Time (CT).
- Turnaround Time (TAT).
- Waiting Time (WT).
- Response Time (RT), where applicable.
- Execution order and Gantt chart segments.

### Compile All Source and Test Files

```bash
javac -d out src/model/*.java src/algorithms/*.java src/engine/*.java src/gui/*.java test/*.java
```

### Run the Algorithm Tests

Run each test with assertions enabled:

```bash
java -ea -cp out FCFSTest
java -ea -cp out SJFTest
java -ea -cp out SRTFTest
java -ea -cp out PriorityTest
java -ea -cp out RoundRobinTest
```

**Note:** The `-ea` option enables Java assertions. Without it, assertion checks are disabled.

## Learning Objectives

This project provides practical experience with:

- CPU scheduling concepts in Operating Systems.
- Preemptive and non-preemptive scheduling.
- Java Object-Oriented Programming.
- Collections and data structures.
- Queue-based process scheduling.
- Java Swing GUI development.
- Modular software design.
- Software testing and validation.
- Git and GitHub version control.

## Future Enhancements

- Add more CPU scheduling algorithms.
- Improve visualization and reporting.
- Add process execution statistics and comparisons.
- Export scheduling results to a file.

## Team

**Tanveer Ali (Lead)**

**Muhammad Waleed**

**Faraz Hussain**

GitHub: [Tanverr Ali](https://github.com/tanveer3351)

## Project Purpose

This project is developed for educational purposes as part of Operating Systems coursework.
