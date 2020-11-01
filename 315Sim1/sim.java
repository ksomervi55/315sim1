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


    Process[] procList;


    Scanner s = new Scanner(System.in);
    System.out.print("Enter File name");
    String fileName = s.nextLine();
      File f = new File(fileName);
      reader(f);


  }
  public static Process[] reader(File f){
    int burst[] = {0};
    int wait[] = {0};
    Process[] procList = new Process[80];

    try{

      Scanner fileReader = new Scanner(f);
      lq1quant = fileReader.nextInt();
      lq2quant = fileReader.nextInt();

      Process p = new Process();
      int j = 0;

      while(fileReader.hasNextLine()){
        p.setProcId(fileReader.nextLine());
        p.setPriority(fileReader.nextLine());
        p.setArrivalTime(fileReader.nextInt());
        int i = 0;

        while(fileReader.hasNextInt()){
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
        procList[j] = p;
      }
      fileReader.close();
    }catch(FileNotFoundException e){
      System.out.println("File Not Found");
      e.printStackTrace();
    }
    return procList;
  }
}
