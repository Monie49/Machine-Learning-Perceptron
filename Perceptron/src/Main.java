

import java.util.ArrayList;

public class Main {
             public static void main(String[] args){
            	    Perceptron p1 = new Perceptron();
            	    ArrayList<String> att = new ArrayList<String>();
                	ArrayList<String> data1 = new ArrayList<String>();
                	ArrayList<String> data2 = new ArrayList<String>();
                	p1.get_att(att, data1, data2); 
                	p1.calculate_wi(att);
                	
                	System.out.println("please input the address of test ham file");
                 	int a[] = p1.Class(att);
                 	System.out.println("please input the address of test spam file");
                 	int b[] = p1.Class(att);
        //         	System.out.println(a[1] + " " + b[1] + " " + b[0] + " " + a[0]);
                 	System.out.println((b[1] + a[1])*1.0/(b[0] + a[0]));
             }
}

