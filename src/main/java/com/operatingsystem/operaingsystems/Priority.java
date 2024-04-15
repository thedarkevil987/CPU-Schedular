package com.operatingsystem.operaingsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Priority {
    public static void sort (ArrayList<Process> processList){
        Collections.sort(processList, Comparator.comparingDouble(Process::getPriority).thenComparing(Process::getArrivalTime));
    }
}
