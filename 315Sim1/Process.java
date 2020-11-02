public class Process{
  public String procId;
  public String priority;
  public int arrivalTime;
  public int[] burstLen;
  public int[] waitTime;

  public Process(){}

  public Process(String pid, String priority, int arrival, int[] burstLen, int[] waitTime){
    procId = pid;
    this.priority = priority;
    arrivalTime = arrival;
    this.burstLen = burstLen;
    this.waitTime = waitTime;
  }
  public String getProcId(){
    return procId;
  }
  public String getPriority(){
    return priority;
  }
  public int getArrivalTime(){
    return arrivalTime;
  }
  public int[] getBurstLen(){
    return burstLen;
  }
  public int[] getWaitTime(){
    return waitTime;
  }
  public void setProcId(String pid){
    procId = pid;
  }
  public void setPriority(String priority){
    this.priority = priority;
  }
  public void setArrivalTime(int arrival){
    arrivalTime = arrival;
  }
  public void setBurstLen(int[] burstLen){
    this.burstLen = burstLen;
  }
  public void setWaitTime(int[] waitTime){
    this.waitTime = waitTime;
  }
  public String printArrays(){
    String s = "";
    for(int i = 0; i < burstLen.length; i++){
      s += "\nBurst len: " + burstLen[i];
      s += "\nwait time: " + waitTime[i];
    }
    return s;
  }
  public String toString(){
    return "\nPID: " + procId + "\n Priority: " + priority + "\n Arrival Time: " + printArrays();
  }
}
