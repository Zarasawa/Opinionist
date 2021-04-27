import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;

class Server
{
  public static void main (String[] args)
  {
    System.out.println("Server is listening...");
    try{
      //connect to client
      ServerSocket ss=new ServerSocket(1812);
      Socket s=ss.accept();
      System.out.println("Connection received");

      //process input from client
      Scanner sc=new Scanner(s.getInputStream());
      while (sc.hasNextLine()){
        String temp=sc.nextLine();
        System.out.println("Received " + temp);
      }


      //process output to client
      PrintWriter pw;
      pw = new PrintWriter(s.getOutputStream());
      System.out.println("Sending requested file to client");
      pw.println("Here's the topic you requested");
      pw.flush();
      s.close();

    } catch (Exception e){
      System.out.println("Error: " + e);
      return;
    }

  }
}

