package com.operatingsystem.operatingsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Process {
    /*
     *  FCFS: Burst time, Order of arrival, time of arrival
     *  SJF(Non-preemptive): Burst time, time of arrival
     *  SRTF (Preemptive): Burst time, order of arrival, time of arrival
     *  Round robin: Burst time, order of arrival
     *  Priority: Burst time, priority
     * */
    private String name;
    private double arrivalTime;
    private double burstTime;
    private double priority;
    private String color;
    private double actualTime;
    private double RemainingBurstTime;
    public double getRemainingBurstTimeCalc() {
        return remainingBurstTimeCalc;
    }
    public void setRemainingBurstTimeCalc(double remainingBurstTimecalc) {
        this.remainingBurstTimeCalc = remainingBurstTimecalc;
    }
    private double remainingBurstTimeCalc;
    private double finishTime;
    private double turnAroundTime;
    private double waitingTime;
    public double getTurnAroundTime() {
        return this.turnAroundTime;
    }
    public double getWaitingTime() {
        return this.waitingTime;
    }
    public double getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
        this.turnAroundTime = this.finishTime - this.arrivalTime;
        this.waitingTime = this.turnAroundTime - this.burstTime;
    }

    public Process(String name, double arrivalTime, double burstTime, String processColor){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.RemainingBurstTime = burstTime;
        this.color = processColor;
        this.remainingBurstTimeCalc = burstTime;
    }
    public Process(String name, double arrivalTime, double burstTime, String processColor,double priority){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.RemainingBurstTime = burstTime;
        this.color = processColor;
        this.priority = priority;
        this.remainingBurstTimeCalc = burstTime;
    }
    public Process(String name, double arrivalTime, double burstTime){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.RemainingBurstTime = burstTime;
    }
    public Process(String name, double arrivalTime, double burstTime, double priority){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.RemainingBurstTime = burstTime;
        this.priority = priority;
    }
    public double getRemainingBurstTime() {
        return RemainingBurstTime;
    }

    public void setRemainingBurstTime(double remainingBurstTime) {
        RemainingBurstTime = remainingBurstTime;
    }

    public double getActualTime() {
        return actualTime;
    }

    public void setActualTime(double actualTime) {
        this.actualTime = actualTime;
    }
    public static void sort(ArrayList<Process> processList){
        Collections.sort(processList, Comparator.comparingDouble(Process::getArrivalTime).thenComparing(Process::getBurstTime));
    }
    public static void calculateActualStartTimes(ArrayList<Process> processList) {
        int currentTime = 0;
        for (Process process : processList) {
            if ((int) process.getArrivalTime() > currentTime) {
                currentTime = (int) process.getArrivalTime();
            }
            process.setActualTime(currentTime);
            currentTime += (int) process.getBurstTime();
        }
    }
    public String getProcessColor() {
        return color;
    }

    public void setProcessColor(String processColor) {
        this.color = processColor;
    }

    public String getName(){
        return this.name;
    }
    public double getArrivalTime(){
        return this.arrivalTime;
    }
    public double getBurstTime(){
        return this.burstTime;
    }
    public double getPriority(){
        return this.priority;
    }
    @Override
    public String toString(){
        return "Name: "+ this.name +"\n"+ "Burst time:"+this.getBurstTime() + "\n"
                + "Arrival time: "+ this.getArrivalTime()+"\n"+ "Remaining time: "+ this.getRemainingBurstTime()+"\n"+"Finish time: " + this.getFinishTime() + "\n"+"Actual time: " + this.getActualTime() + "\n";
    }
}

