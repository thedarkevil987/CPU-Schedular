package com.operatingsystem.operaingsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS {

    public static ArrayList<Process> getFCFSSecondsArray(ArrayList<Process> processes,int maxSeconds){
        ArrayList<Process> secondsArray = new ArrayList<>();
        for(int i = 0;i<maxSeconds;i++){
            secondsArray.add(null);
        }
        for(Process process :processes){
            int burstTime = (int)process.getBurstTime();
            int actualTime = (int)process.getActualTime();
            for(int i = actualTime; i < actualTime + burstTime;i++){
                secondsArray.set(i,process);
            }
        }
        return secondsArray;
    }

    public static void main(String[] args){
        ArrayList<Process> processList =new ArrayList<>();
        processList.add(new Process("P1",0,3,"red"));
        processList.add(new Process("P2",0,4,"blue"));
        processList.add(new Process("P3",1,2));
        processList.add(new Process("P4",0,1));
        processList.add(new Process("P5",0,10));
        processList.add(new Process("P6",2,2));
        Process.sort(processList);
        System.out.println(processList);
    }

}
