import java.util.*;

public class Disk {
    int size;
    int headInit;

    public Disk(int size, int headInit) {
        this.size = size;
        this.headInit = headInit;
    }

    public int FCFS(ArrayList<Process> listOfProcesses) {
        listOfProcesses.sort(Comparator.comparingInt(process -> process.arrivalTime));
        int seekCount = 0;
        int currentHeadPosition = headInit;

        for (Process process : listOfProcesses) {
            int targetTrack = process.track;
            int movement = Math.abs(currentHeadPosition - targetTrack);
            seekCount += movement;
            currentHeadPosition = targetTrack;
        }
        System.out.println("FCFS Head moves count: " + seekCount);
        return seekCount;
    }

    public int SSTF(ArrayList<Process> listOfProcesses) {
        int currentHeadPosition = headInit;
        int seekCount = 0;

        while (!listOfProcesses.isEmpty()) {
            Process nextProcess = null;
            int minDistance = Integer.MAX_VALUE;

            for (Process process : listOfProcesses) {
                int distance = Math.abs(currentHeadPosition - process.track);
                if (distance < minDistance) {
                    minDistance = distance;
                    nextProcess = process;
                }
            }

            if (nextProcess != null) {
                seekCount += minDistance;
                currentHeadPosition = nextProcess.track;
                listOfProcesses.remove(nextProcess);
            }
        }

        System.out.println("SSTF Head moves count: " + seekCount);
        return seekCount;
    }

    public int SCAN(ArrayList<Process> listOfProcesses, int mode) {
        ArrayList<Process> leftRequests = new ArrayList<>();
        ArrayList<Process> rightRequests = new ArrayList<>();

        for (Process p : listOfProcesses) {
            if (p.track < headInit) {
                leftRequests.add(p);
            } else {
                rightRequests.add(p);
            }
        }

        leftRequests.sort(Comparator.comparingInt(process -> process.track));
        Collections.reverse(leftRequests);

        rightRequests.sort(Comparator.comparingInt(process -> process.track));

        int seekCount = 0;
        int currentPosition = headInit;

        // Mode == 0 -> scanning to the left
        if (mode == 0) {
            for (Process p : leftRequests) {
                seekCount += Math.abs(currentPosition - p.track);
                currentPosition = p.track;
            }
            for (Process p : rightRequests) {
                seekCount += Math.abs(currentPosition - p.track);
                currentPosition = p.track;
            }
            // Mode == 1 -> scanning to the right
        } else if (mode == 1) {
            for (Process p : rightRequests) {
                seekCount += Math.abs(currentPosition - p.track);
                currentPosition = p.track;
            }
            for (Process p : leftRequests) {
                seekCount += Math.abs(currentPosition - p.track);
                currentPosition = p.track;
            }
        }

        String text = (mode == 0) ? "left" : "right";
        System.out.println("SCAN (" + text + ") Head moves count: " + seekCount);
        return seekCount;
    }

    public int cSCAN(ArrayList<Process> listOfProcesses, int mode) {
        listOfProcesses.sort(Comparator.comparingInt(process -> process.track));
        ArrayList<Process> helpList = new ArrayList<>();
        int seekCount = 0;
        int tempHeadInit = headInit;

        for (int i = 0; i < listOfProcesses.size(); i++) {
            if (listOfProcesses.get(i).track <= headInit) {
                helpList.add(listOfProcesses.get(i));
            }
        }
        Collections.reverse(helpList);

        for (int i = 0; i < listOfProcesses.size(); i++) {
            if (listOfProcesses.get(i).track > headInit) {
                helpList.add(listOfProcesses.get(i));
            }
        }

        for (int i = 0; i < listOfProcesses.size(); i++) {
            if (listOfProcesses.get(i).track <= headInit) {
                helpList.add(listOfProcesses.get(i));
            }
        }

        for (Process process : helpList) {
            seekCount += Math.abs(tempHeadInit - process.track);
            tempHeadInit = process.track;
        }

        System.out.println("C-SCAN Head moves count: " + seekCount);
        return seekCount;
    }

    //Dynamic implementation
    public int dynamicEDF(int simulationTime) {
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.deadline));
        int currentTime = 0;
        int currentHeadPosition = headInit;
        int seekCount = 0;
        int abandoned = 0;
        Random random = new Random();

        while (currentTime < simulationTime) {
            if (random.nextDouble() < 0.2) {
                int randomTrack = random.nextInt(size);
                int randomDeadlineOffset = random.nextInt(81) + 20;
                int deadline = currentTime + randomDeadlineOffset;
                Process newProcess = new Process(currentTime, randomTrack, deadline);
                readyQueue.add(newProcess);
                System.out.println("Dynamic EDF: New process added at time " + currentTime
                        + " | track: " + randomTrack + " | deadline: " + deadline);
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.peek();
                int movement = Math.abs(currentHeadPosition - currentProcess.track);

                if (currentTime + movement <= currentProcess.deadline) {
                    currentProcess = readyQueue.poll();
                    currentTime += movement;
                    seekCount += movement;
                    currentHeadPosition = currentProcess.track;
                    System.out.println("Dynamic EDF processed: arrival " + currentProcess.arrivalTime
                            + " | track: " + currentProcess.track + " | deadline: " + currentProcess.deadline
                            + " | currentTime: " + currentTime);
                } else {
                    readyQueue.poll();
                    abandoned++;
                    System.out.println("Dynamic EDF abandoned process at time " + currentTime);
                }
            } else {
                currentTime++;
            }
        }
        System.out.println("\n==== Summarising ====");
        System.out.println("Dynamic EDF simulation finished. Total head moves count: " + seekCount
                + ", abandoned requests: " + abandoned);
        return seekCount;
    }
}
