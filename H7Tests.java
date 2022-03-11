import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

public class H7Tests {
  public static void main(String args[]){
    org.junit.runner.JUnitCore.main("H7Tests");
  }
  
  public String readFile(String filename) {
    try {
      Scanner sc = new Scanner(new File(filename));
      StringBuilder s = new StringBuilder();
      while (sc.hasNextLine()){
        s.append(sc.nextLine()+"\n");
      }
      return s.toString();
    }
    catch (FileNotFoundException e){
     throw new AssertionError("can't read the missing file '"+filename+"'"); 
    }
  }
  

/*
  public static void checkOutputConsistency(String filename, int N, int M) throws IOException{
    DanceCardChecker.main(new String[]{"tests/"+filename,N+"",M+""}); 
  }
*/
  
  @Test public void test_A1 () throws IOException {checkTestConsistency("A1.txt",  4,  4); }
  @Test public void test_A2 () throws IOException {checkTestConsistency("A2.txt",  4,  6); }
  @Test public void test_A3 () throws IOException {checkTestConsistency("A3.txt",  6,  4); }
  @Test public void test_A4 () throws IOException {checkTestConsistency("A4.txt", 10, 10); }
  @Test public void test_A5 () throws IOException {checkTestConsistency("A5.txt",  2,  2); }
  @Test public void test_A6 () throws IOException {checkTestConsistency("A6.txt",  2,  8); }
  @Test public void test_A7 () throws IOException {checkTestConsistency("A7.txt",  8,  2); }
  @Test public void test_A8 () throws IOException {checkTestConsistency("A8.txt", 30, 30); }
  @Test public void test_A9 () throws IOException {checkTestConsistency("A9.txt", 30,  4); }
  @Test public void test_A10() throws IOException {checkTestConsistency("A10.txt", 4, 30); }
  
  public static void checkTestConsistency(String filename, int N, int M) throws IOException{
    DanceCardChecker.main(new String[]{"out/"+filename,N+"",M+""}); 
  }
}
