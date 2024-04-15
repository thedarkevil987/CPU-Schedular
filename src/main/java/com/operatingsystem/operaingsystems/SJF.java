package com.operatingsystem.operaingsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJF{
    public static ArrayList<Process> runRoundRobin(ArrayList<Process> processes) {
        ArrayList<Process> result = new ArrayList<>();
        int i = 0;
        ArrayList<Process> temp = new ArrayList<>(processes);
        ArrayList<Process> currentlyProcessing = new ArrayList<>();
        while(!temp.isEmpty()){
            addProcesses(i,temp,currentlyProcessing);
            if(currentlyProcessing.isEmpty()){
                result.add(null);
            }else{
                result.add(currentlyProcessing.get(0));
                currentlyProcessing.get(0).setRemainingBurstTime(currentlyProcessing.get(0).getRemainingBurstTime() - 1);
                if(currentlyProcessing.get(0).getRemainingBurstTime() == 0){
                    currentlyProcessing.get(0).setRemainingBurstTime(currentlyProcessing.get(0).getBurstTime());
                    temp.remove(currentlyProcessing.get(0));
                    currentlyProcessing.remove(0);
                }
            }
            i++;
        }
        return result;
    }
    public static void addProcesses(int i,ArrayList<Process> processes,ArrayList<Process> currentlyProcessing){
        for(Process p:processes){
            if((int)p.getArrivalTime() == i){
                currentlyProcessing.add(p);
            }
        }
        sortByBT(currentlyProcessing);
    }
    public static void sortByBT(ArrayList<Process> temp){
        Collections.sort(temp, Comparator.comparingDouble(Process::getRemainingBurstTime));
        /*
        * getRemainingBurstTime For Nonpremetive
        * getBurstTime For Premetive
        * */
    }
    public static void main(String[] args) {
        ArrayList<Process> List =new ArrayList<>();
        List.add(new Process("P1",2,4));
        List.add(new Process("P2",2,5));
        List.add(new Process("P3",3,3));
        List.add(new Process("P4",4,2));
        List.add(new Process("P5",99,2));
        System.out.println(runRoundRobin(List));
    }
}
