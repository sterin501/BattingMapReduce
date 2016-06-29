import java.io.*;
import java.util.*;


public class CsvCreator {


public static void main (String[] args) {


try {

 File Outputfile = new File(args[1]);
 FileWriter fw = new FileWriter(Outputfile.getAbsoluteFile());
 BufferedWriter bw = new BufferedWriter(fw);   

int endofLoop = Integer.parseInt(args[0]);

for (int i=0;i<endofLoop;i++)

{

//aaronha01,1954,1,ML1,NL,122,122,468,58,131,27,6,13,69,2,2,28,39,,3,6,4,13,122
//1        ,...2,3,..4,.5,..6,..7,..8,.9,.10,11,2,13,14,5,6,17,18,9,20,21,22,23
 String player = getRandomWord(8,26,97);

 String club  = getRandomWord(3,26,65);

String state  = getRandomWord(2,26,65);

 int year=randInt(1867,2005);

 int careerYear=randInt(1,10);


  for(int c=0;c<careerYear;c++)

    {

 

  int maxScoreinYear=randInt(38,110);

  int ranRun1=randInt(0,10);

   int ranRun2=randInt(0,10);

   int ranRun3=randInt(0,10);

  int ranRun4=randInt(0,10);

 maxScoreinYear=maxScoreinYear+ranRun1+ranRun2+ranRun3+ranRun4;
   

  int run1=randInt(0,125);

  int run2=randInt(0,120);
  int run3=randInt(0,150);

  int run4=randInt(0,maxScoreinYear);

 

  String cc1 = getRandomWord(3,26,97);

  String cc2 = getRandomWord(3,26,97);

  String cc3 = getRandomWord(3,26,65);
 


  int run5=randInt(0,125);

  int run6=randInt(0,120);
  int run7=randInt(0,150);

  int run8=randInt(0,60);

  int run9=randInt(0,500);


  int run10=randInt(0,12599);

  int run11=randInt(0,89766);
  int run12=randInt(0,9878);

  int run13=randInt(0,60);

  int run14=randInt(0,500);

String cc4 = getRandomWord(3,26,97);

  String cc5 = getRandomWord(3,26,97);

  String cc6 = getRandomWord(3,26,65);
 




 String toPrint=player+","+(year+c)+","+"1"+","+club+","+state+","+run1+","+run2+","+run3+","+run4+","+cc1+","+cc2+","+cc3+","+run5+","+run6+","+run7+","+run8+","+run9+","+run10+","+run11+","+run12+","+run13+","+run14+","+cc4+","+cc5+","+cc6;

 bw.write(toPrint+"\n");

   }


}


 bw.close();



     }catch (IOException e) {
      e.printStackTrace();
    }


} //end of main


public static int randInt(int min, int max) {


    Random rand = new Random(); ;


    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
}

/*
for small letters 26 to 97
For Capital  

http://www.asciitable.com/

*/

public static String getRandomWord(int length,int StartAsci,int EndAsci) {
    String r = "";
    for(int i = 0; i < length; i++) {
        r += (char)(Math.random() * StartAsci + EndAsci);
    }
    return r;
}






} // end of class
