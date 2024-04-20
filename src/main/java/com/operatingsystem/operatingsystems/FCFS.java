package com.operatingsystem.operatingsystems;

import java.util.ArrayList;

public class FCFS {

    public static ArrayList<Process> getFCFSSecondsArray(ArrayList<Process> processes){
        int maxSeconds = (int)processes.get(processes.size()-1).getActualTime() + (int)processes.get(processes.size()-1).getBurstTime();
        ArrayList<Process> secondsArray = new ArrayList<>();
        for(int i = 0;i<maxSeconds;i++){
            secondsArray.add(null);
        }
        for(Process process :processes){
            int burstTime = (int)process.getBurstTime();
            int actualTime = (int)process.getActualTime();
            for(int i = actualTime; i < actualTime + burstTime;i++){
                secondsArray.set(i,process);
                if(i == actualTime + burstTime - 1){
                    secondsArray.get(i).setFinishTime(i+1);
                }
            }
        }
        return secondsArray;
    }

    public static void main(String[] args){
        ArrayList<Process> processList =new ArrayList<>();
        processList.add(new Process("P1",0,3));
        processList.add(new Process("P2",0,4));
        processList.add(new Process("P3",1,2));
        Process.sort(processList);
        Process.calculateActualStartTimes(processList);
    }

}
