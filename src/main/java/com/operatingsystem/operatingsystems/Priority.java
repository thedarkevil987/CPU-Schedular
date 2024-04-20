package com.operatingsystem.operatingsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Priority {
    public static ArrayList<Process> getPrioritySecondsArray(ArrayList<Process> processes,boolean isPreemptive) {
        ArrayList<Process> result = new ArrayList<>();
        int i = 0;
        ArrayList<Process> temp = new ArrayList<>(processes);
        ArrayList<Process> currentlyProcessing = new ArrayList<>();
        while(!temp.isEmpty()){
            addProcesses(i,temp,currentlyProcessing,isPreemptive);
            if(currentlyProcessing.isEmpty()){
                result.add(null);
            }else{
                result.add(currentlyProcessing.get(0));
                currentlyProcessing.get(0).setRemainingBurstTimeCalc(currentlyProcessing.get(0).getRemainingBurstTimeCalc() - 1);
                if(currentlyProcessing.get(0).getRemainingBurstTimeCalc() == 0){
                    currentlyProcessing.get(0).setRemainingBurstTimeCalc(currentlyProcessing.get(0).getBurstTime());
                    currentlyProcessing.get(i%currentlyProcessing.size()).setFinishTime(i + 1);
                    temp.remove(currentlyProcessing.get(0));
                    currentlyProcessing.remove(0);
                }
            }
            i++;
        }
        return result;
    }
    public static void addProcesses(int i,ArrayList<Process> processes,ArrayList<Process> currentlyProcessing,boolean isPreemptive){
        for(Process p:processes){
            if((int)p.getArrivalTime() == i){
                currentlyProcessing.add(p);
            }
        }
        if(isPreemptive){
            sortPreemptive(currentlyProcessing);
        }else{
            sortNonPreemptive(currentlyProcessing);
        }
    }
    public static void sortPreemptive(ArrayList<Process> temp){
        Collections.sort(temp, Comparator.comparingDouble(Process::getPriority));

    }
    public static void sortNonPreemptive(ArrayList<Process> temp){
        if(!temp.isEmpty()){
            Process t = temp.remove(0);
            Collections.sort(temp, Comparator.comparingDouble(Process::getPriority));
            temp.add(0,t);
        }
    }


    public static void main (String[] args){

        ArrayList<Process> processes = new ArrayList<>();

        processes.add(new Process("P1",2,3,2));
        processes.add(new Process("P2",3,4,1));
        //processes.add(new Process("P3",5,2,2));
        //processes.add(new Process("P4",2,1,3));
        //processes.add(new Process("P5",2,4,4));
        //processes.add(new Process("P6",2,2,5));

    }
}
