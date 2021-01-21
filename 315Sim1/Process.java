  import java.util.*;
  
  public class Process{

  public String procId;
  public String priority;
  public int arrivalTime;
  public LinkedList<Integer> burstTime = new LinkedList<Integer>();
  public LinkedList<Integer> waitTime = new LinkedList<Integer>();
  public int runCount = 3; //for demoting to LQ2
  public int quantumSize = 0;
	public int outTime; //time done
	public int waitqTime = 0; //time spent in ready Q


  public Process(){}

  public Process(String pid, String priority, int arrival, LinkedList<Integer> burstTime, LinkedList<Integer> waitTime, int quantumSize){
    procId = pid;
    this.priority = priority;
    arrivalTime = arrival;
    this.burstTime = burstTime;
    this.waitTime = waitTime;
    this.quantumSize = quantumSize;
  }

  //returns the process ID
  public String getProcId(){
    return procId;
  }
  //returns the process Priority
  public String getPriority(){
    return priority;
  }
  //returns the process Arrival Time
  public int getArrivalTime(){
    return arrivalTime;
  }
  //returns the burst(s) in CPU
	public LinkedList<Integer> getBurstTime(){
		return burstTime;
	}
  //returns the amount of time(s) blocked
	public LinkedList<Integer> getWaitTime(){
		return waitTime;
	}

  //sets the Process ID
  public void setProcId(String pid){
    procId = pid;
  }
  //sets the process priority
  public void setPriority(String priority){
    this.priority = priority;
  }
  //sets the process Arrival Time
  public void setArrivalTime(int arrival){
    arrivalTime = arrival;
  }
  //adds burst time
  public void addBurstTime(int burst){
    burstTime.add(burst);
  }
  //adds time blocked
  public void addWaitTime(int wait){
    waitTime.add(wait);
  } 
  //decrements Burst time (for when on CPU)
  public int decrementBurst(){
    int burst = burstTime.poll() - 1;
    if (burst > 0)
      burstTime.addFirst(burst);
    else{
      runCount = 3;
      return 0;
    }
    return burstTime.getFirst();
  }
  //decrements wait time (for when a tick passes and process is blocked)
  public int decrementWait(){
    int wait = waitTime.poll() - 1;
    if (wait > 0)
     waitTime.addFirst(wait);
    else
      return 0;
    return 1;
  }
 //returns burst and blocked times to a string
 public String ListsToString() {
  String s = "\nBurst: ";
  s += burstTime.toString();
  s += "\nWait: ";
  s += waitTime.toString();
  return s;
}
  //prints the process
  public String toString(){
    return "\nPID: " + procId + "\nPriority: " + priority + "\nArrival Time: "+ arrivalTime + ListsToString();
  }
  //returns the Run Count to determine for demotion to LQ2
  public int getRunCount(){
    return runCount;
  }
  //sets Run Count
  public void setRunCount(){
    runCount = 3;
  }
  //decrements Run Count
  public void isRun(){
    runCount -= 1;
  }
  //sets process quantum size
  public void setQuantumSize(int num){
    quantumSize = num;
  }
  //decrements process quantum size
  public void decrementQuantumSize(){
    quantumSize -= 1;
  }
  //returns process quantum size
  public int getQuantumSize(){
    return quantumSize;
  }
  //reutrns time process was done
	public int getOutTime(){
		return outTime;
	}
  //sets time process was done
	public void setOutTime(int out){
		outTime = out;
	}
  //returns time spent on ready Q
	public int getWaitqTime(){
		return waitqTime;
	}
  //increments time spent on ready Q
	public void incrementWaitTime(){
    waitqTime += 1;
  }
}
