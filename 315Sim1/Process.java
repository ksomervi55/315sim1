public class Process{
  public String procId;
  public String priority;
  public int arrivalTime;
  public int[] burstLen;
  public int[] waitTime;

  public Process(){}

  public Process(String pid, String priority, int arrival, int[] burstLen, int[] waitTime){
    procId = pid;
    priority = this.priority;
    arrivalTime = arrival;
    burstLen = this.burstLen;
    waitTime = this.waitTime;
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
    priority = this.priority;
  }
  public void setArrivalTime(int arrival){
    arrivalTime = arrival;
  }
  public void setBurstLen(int[] burstLen){
    burstLen = this.burstLen;
  }
  public void setWaitTime(int[] waitTime){
    waitTime = this.waitTime;
  }

}
