import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class sim{
  public static int lq1quant;
  public static int lq2quant;
  public Queue<Process> hq = new LinkedList<Process>();
  public Queue<Process> lq1 = new LinkedList<Process>();
  public Queue<Process> lq2 = new LinkedList<Process>();
  public static void main(String args[]){


    LinkedList<Process> procList = new LinkedList<Process>();


    Scanner s = new Scanner(System.in);
    System.out.print("Enter File name");
    String fileName = s.nextLine();
      File f = new File(fileName);
      procList = reader(f);
      for(int i = 0; i < procList.size(); i++){
        if(procList.get(i) != null){
          System.out.println(procList.get(i).toString());
        }
      }


  }
  public static LinkedList<Process> reader(File f){

    LinkedList<Process> procList = new LinkedList<Process>();

    try{
      int burst[] = new int[10];
      int wait[] = new int[10];
      Scanner fileReader = new Scanner(f);
      lq1quant = fileReader.nextInt();
      lq2quant = fileReader.nextInt();
      System.out.println(lq1quant);
      System.out.println(lq2quant);
      Process p;
      int j = 0;

      while(fileReader.hasNext()){
        p = new Process();
        p.setProcId(fileReader.next());
        System.out.println("PID: " + p.getProcId());
        p.setPriority(fileReader.next());
        System.out.println("priority: " + p.getPriority());
        p.setArrivalTime(fileReader.nextInt());
        System.out.println("Arrival: "+ p.getArrivalTime());
        int i = 0;

        while(fileReader.hasNextInt() && fileReader.hasNext()){
          burst[i] = fileReader.nextInt();
          int temp = fileReader.nextInt();
          if(temp == -1){
            wait[i] = 0;
            break;
          }
          else{
            wait[i] = temp;
          }
          i++;
        }
        p.setBurstLen(burst);
        p.setWaitTime(wait);
        procList.add(p);
        //System.out.println(procList[j].toString());
        j++;
      }
      fileReader.close();
    }catch(FileNotFoundException e){
      System.out.println("File Not Found");
      e.printStackTrace();
    }
    return procList;
  }
}
