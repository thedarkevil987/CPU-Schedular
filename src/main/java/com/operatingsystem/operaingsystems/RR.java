package com.operatingsystem.operaingsystems;


import java.util.ArrayList;

public class RR {
    public static ArrayList<Process> getRRSecondsArray(ArrayList<Process> processes) {
        ArrayList<Process> result = new ArrayList<>();
        int i = 0;
        ArrayList<Process> temp = new ArrayList<>(processes);
        ArrayList<Process> currentlyProcessing = new ArrayList<>();
        while(!temp.isEmpty()){
            addProcesses(i,temp,currentlyProcessing);
            if(currentlyProcessing.isEmpty()){
                result.add(null);
            }else{
                result.add(currentlyProcessing.get(i%currentlyProcessing.size()));
                currentlyProcessing.get(i%currentlyProcessing.size()).setRemainingBurstTime(currentlyProcessing.get(i%currentlyProcessing.size()).getRemainingBurstTime() - 1);
                if(currentlyProcessing.get(i%currentlyProcessing.size()).getRemainingBurstTime() == 0){
                    currentlyProcessing.get(i%currentlyProcessing.size()).setRemainingBurstTime(currentlyProcessing.get(i%currentlyProcessing.size()).getBurstTime());
                    currentlyProcessing.get(i%currentlyProcessing.size()).setFinishTime(i + 1);
                    temp.remove(currentlyProcessing.get(i%currentlyProcessing.size()));
                    currentlyProcessing.remove(i%currentlyProcessing.size());
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
    }
    public static void main(String[] args) {
        ArrayList<Process> List =new ArrayList<>();
        List.add(new Process("P1",3,3));
        //List.add(new Process("P3",20,2));
        System.out.println(getRRSecondsArray(List));
        List.add(new Process("P2",3,3));
        System.out.println(getRRSecondsArray(List));
    }
}
