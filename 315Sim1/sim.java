import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class sim{
  //Declarations
  public static int lq1quant;
  public static int lq2quant;
  public static LinkedList<Process> hq = new LinkedList<Process>();
  public static LinkedList<Process> lq1 = new LinkedList<Process>();
  public static LinkedList<Process> lq2 = new LinkedList<Process>();
  public static LinkedList<Process> hq_b = new LinkedList<Process>();
  public static LinkedList<Process> lq1_b = new LinkedList<Process>();
  public static LinkedList<Process> lq2_b = new LinkedList<Process>();
  public static LinkedList<Process> lq1_q = new LinkedList<Process>();
  public static LinkedList<Process> lq2_q = new LinkedList<Process>();
  public static String[] printable = new String[100];
	public static LinkedList<Process> out = new LinkedList<Process>();
	public static LinkedList<Process> blocked = new LinkedList<Process>();


  public static void main(String args[]){

    //new process list
    LinkedList<Process> procList = new LinkedList<Process>();
	
    //Read from file
      File f = new File(args[0]);

      procList = reader(f);
      for(int i = 0; i < procList.size(); i++){
        if(procList.get(i) != null){
          System.out.println(procList.get(i).toString());
        }
        System.out.println();
      }

    //Start simulation
      int time = 0;
			Process onCpu = null; //current cpu process
			boolean cpuFlag = false;
      int cpu_active = 0;
      int cpu_idle = 0;
      do{
        
        //check if new jobs enter the System
				check_new_jobs(procList, time, hq, lq1, hq_b, lq1_b, lq2_b);
        //if time = 0 and there is a process, put it onCpu
        if (time == 0){
          onCpu = pickproc(time);
        } 
        //check if job is returning fom blocked
				if(blocked.isEmpty() == false){
					checkblocked(time);
				}

        //System.out.println(hq_b);
         //CPU Check (pick_proc)
        if((hq.isEmpty() == false || lq1.isEmpty() == false || lq2.isEmpty() == false) && onCpu == null){
          onCpu = pickproc(time);
        }

        //for Gantt Chart
        if(onCpu != null){
				  printable[time] = onCpu.getProcId();
          cpu_active++;
        }
				else {
				  printable[time] = "*";
          cpu_idle++;
        }

        time++;

        //cpu_done_check ( + process run)
        if (onCpu != null){
        //run process 
          onCpu = runProc(onCpu,time);
        }
        //quantum check 
        if (onCpu != null){
        onCpu = quantumCheck(onCpu);
				}

        //ex pick proc


        //if elements exist in the job queues, increment their wait time
        int k = 0;
      
        if (hq.isEmpty() != true){
        for (k = 0; k < hq.size(); k++)
          hq.get(k).incrementWaitTime();
      }
        if (lq1.isEmpty() != true){
        for (k = 0; k < lq1.size(); k++)
          lq1.get(k).incrementWaitTime();
        }

        if (lq2.isEmpty() != true){
        for (k = 0; k < lq2.size(); k++)
          lq2.get(k).incrementWaitTime();
        }

      }while(procList.isEmpty() == false || (hq.isEmpty() == false || lq1.isEmpty() == false || lq2.isEmpty() == false || blocked.isEmpty() == false || hq_b.isEmpty() == false || lq1_b.isEmpty() == false || lq2_b.isEmpty() == false || lq1_q.isEmpty() == false || lq2_q.isEmpty() == false) || onCpu != null);

      //Output
      System.out.println("__________________");
      System.out.println("\nGantt Chart: ");
			printer(time);
			int size = out.size();
      ArrayList<Integer> turnaroundTimes = new ArrayList<Integer>();
      ArrayList<Integer> waitTimes = new ArrayList<Integer>();
      System.out.println("\n");
			while(out.isEmpty() == false){
				Process p = out.poll();
        System.out.println();
        System.out.println("Process " + p.getProcId());
				int turnaround = p.getOutTime() - p.getArrivalTime();
        turnaroundTimes.add(turnaround);
				System.out.println("Turnaround time: "+ turnaround);
				System.out.println("Waiting time in Ready Q: " + p.getWaitqTime());
        waitTimes.add(p.getWaitqTime());
			}

      //average turnaround & wait time
      int averageTurnaround = 0;
      int averageWait = 0;
      for (int i = 0; i < turnaroundTimes.size(); i++)
        averageTurnaround += turnaroundTimes.get(i);
      for (int i = 0; i < waitTimes.size(); i++)
        averageWait += waitTimes.get(i);
      float avgTurn = (float) averageTurnaround/turnaroundTimes.size();
      float avgWait = (float) averageWait/waitTimes.size();
      System.out.println();
      System.out.println("Average Turnaround Time: " + avgTurn);
      System.out.println("Average Wait time on ready Q: " + avgWait);
      System.out.println();
      double cpu_util = 100.00*cpu_active / (cpu_active + cpu_idle);
      System.out.println("CPU Utilization rate: " + cpu_active +"/" + (cpu_active + cpu_idle) );
			System.out.println("Throughput: " + size + "/" + time);
  }

  //checks if new jobs are entering the system
  public static void check_new_jobs(List<Process> procList, int time, Queue<Process> hq, Queue<Process> lq1, LinkedList<Process> hq_b, LinkedList<Process> lq1_b, LinkedList<Process> lq2_b){
    Process p;
    int j = 0;
    //check process list
		if(procList.isEmpty() == false){
			for(j = 0; j < procList.size(); j++){
				if (procList.get(j).getArrivalTime() == time){
          p = procList.get(j);
				//place entering job in correct queue
					if (p.getPriority().equals("HP")){
						hq.add(p);
					}
					else{
						lq1.add(p);
					}    
				}
			}
    
      for(j = 0; j < procList.size(); j++){
				if (procList.get(j).getArrivalTime() <= time){
          procList.remove(j);
        }
      }
    }

    //return the blocked members
    Process temp;
    if (hq_b.isEmpty() == false){
      for (int i = 0; i < hq_b.size(); i++){
        temp = hq_b.peek();
        hq.add(temp);
      }
    }

    while (hq_b.isEmpty() == false)
      hq_b.remove();

    if (lq1_b.isEmpty() == false){
      for (int i = 0; i < lq1_b.size(); i++){
        temp = lq1_b.peek();
        lq1.add(temp);
      }
    }

    while (lq1_b.isEmpty() == false)
      lq1_b.remove();


    if (lq2_b.isEmpty() == false){
      for (int i = 0; i < lq2_b.size(); i++){
        temp = lq2_b.peek();
        lq2.add(temp);
      }
    }

    while (lq2_b.isEmpty() == false)
      lq2_b.remove();

    if (lq1_q.isEmpty() == false){
      for (int i = 0; i < lq1_q.size(); i++){
        temp = lq1_q.peek();
        lq1.add(temp);
      }
    }

    while (lq1_q.isEmpty() == false)
      lq1_q.remove();



  }

  //Read in from the file and add to a list so we can work with the processes
  public static LinkedList<Process> reader(File f){

    LinkedList<Process> procList = new LinkedList<Process>();

    try{
      Scanner fileReader = new Scanner(f);
      lq1quant = fileReader.nextInt();
      lq2quant = fileReader.nextInt();

      Process p;
      int j = 0;
      System.out.println("Processes read: \n");
      while(fileReader.hasNext()){
        p = new Process();
        p.setProcId(fileReader.next());
        p.setPriority(fileReader.next());
        p.setArrivalTime(fileReader.nextInt());

        while (fileReader.hasNextInt() && fileReader.hasNext()){
          p.addBurstTime(fileReader.nextInt());
          int temp = fileReader.nextInt();
          if (temp == -1)
            break;
          p.addWaitTime(temp);
        }
        procList.add(p);
        p.toString();
        j++;
      }
      fileReader.close();
    }catch(FileNotFoundException e){
      System.out.println("File Not Found");
      e.printStackTrace();
    }
    return procList;
  }


  //outputs Gantt Chart
  public static void printer(int time){
    for(int i = 0; i < time; i++){
      if(printable[i] == null){
        return;
      }
			if(i % 5 == 0 && i != 0)
			System.out.print("|");
      if (i == 40)
        System.out.println();
      System.out.print(printable[i]);
    }
  }

  //returns the next process to run
	public static Process pickproc(int time){
		Process p = new Process();
		if(hq.isEmpty() == false){
					p = hq.peek();
					hq.remove();
    }
    else if(lq1.isEmpty() == false){
			p = lq1.peek();
			lq1.remove();
      p.setQuantumSize(lq1quant);
		}
		else if(lq2.isEmpty() == false){
			p = lq2.poll();
      p.setQuantumSize(lq2quant);
     }
		else{
			p = null;
		}
		return p;
	}

  //runs the current process, checks if job is done
  public static Process runProc(Process p, int time){
    int burst = p.decrementBurst();
    if (p.getPriority().equals("LP"))
      p.decrementQuantumSize();
    if (burst == 0){
      if (p.getWaitTime().isEmpty() != true){
        blocked.add(p);
			}
			else{
				p.setOutTime(time);
				out.add(p);
			}
      return null;

    }
    else
      return p;
  }

  //checks if job has reached its quantum
  public static Process quantumCheck(Process p){
    if (p.getPriority().equals("LP")){
      if (p.getQuantumSize() == 0){ //block
					p.isRun();
          if (p.getRunCount() == 0){
            p.setQuantumSize(lq2quant);
            lq2_b.add(p);
            p.setRunCount();
          }
          else{
            p.setQuantumSize(lq1quant);
            lq1_q.add(p);
          }
        return null;
      }
      else
        return p;
    }
    return p;
  }
  //checks if jobs are returning from blocked Q
  public static void checkblocked(int time){
    Process p = null;
    //for each process in the blocked Q
    for (int i = 0; i < blocked.size(); i++){
       p = blocked.get(i);
     //decrement blocked time
      if (p.decrementWait() == 0){
        blocked.remove(i);
         //if no more blocked, remove from blocked Q
        //assign to proper priority list
        if (p.getPriority().equals("HP")){
					hq_b.add(p);
				}
        else{
          if (p.getRunCount() == 0){
            p.setQuantumSize(lq2quant);
						
            lq2.add(p);
            p.setRunCount();
          }
          else{
            p.setQuantumSize(lq1quant);
						p.isRun();
            lq1_b.add(p);
						
          }
        }
    }
  }

}

}


