package com.operatingsystem.operaingsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJF{

    public static ArrayList<Process> getSJFSecondsArray(ArrayList<Process> processes,boolean isPreemptive) {
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
        Collections.sort(temp, Comparator.comparingDouble(Process::getRemainingBurstTime));

    }
    public static void sortNonPreemptive(ArrayList<Process> temp){
       if(!temp.isEmpty()){
            Process t = temp.remove(0);
            Collections.sort(temp, Comparator.comparingDouble(Process::getRemainingBurstTime));
            temp.add(0,t);
        }
    }
    public static void main(String[] args) {
        ArrayList<Process> List =new ArrayList<>();
        List.add(new Process("P1",2,5));
        List.add(new Process("P2",3,9));
        List.add(new Process("P3",8,1));
    }


}
