
import static java.util.Arrays.asList;

import java.io.*;
import java.util.*;

public class Perceptron {
	 List<String> sc1 = asList("-",",",".","@","/");
     List<String> stopwords1 = asList("a","about","above","after","aga","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by"
    		   ,"can't","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll"
    		   ,"he's","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself"
    		   ,"no","nor","not","of","off","on","once","only","or","other","ought","our","ours","ourselves","out","over","own","same","shan't","she","she'd","she'll","she's","should","shouldn't","so","some","such","than","that","that's","the","their","theirs","them"
    		   ,"themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while","who"
    		   ,"who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves");
     
     
     double lambda = 0.02;
     
     ArrayList<Double> w = new ArrayList<Double>();
     
     public int readtrainfile(ArrayList<String> data1){
    	 try{
         	  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));	
         		  String path= in.readLine();
            	  File file=new File(path);
            	  File[] tempList = file.listFiles();
            	  
            	  for(int i=0; i<tempList.length;i++){
            		  
            		 BufferedReader reader = new BufferedReader(new FileReader(tempList[i]));
               	     String line = null;
               	     while((line=reader.readLine())!=null){ 
                	  String item[] = line.split(" ");
                	   for(int j=0; j<item.length; j++){
                		   if(!sc1.contains(item[j])&&!stopwords1.contains(item[j])
)
                			 data1.add(item[j]);
                	   }  	   
                  }
            	 }
            	  
              return tempList.length;
         	 }
         	 catch(Exception e){
              	e.printStackTrace();
              	return 0;
          	}
     }
     
     public void get_att(ArrayList<String> att,ArrayList<String> data,ArrayList<String> data1){
 	    System.out.println("Please input the address of the train ham file");
 	    readtrainfile(data);
 	    System.out.println("Please input the address of the train spam file");
 	    readtrainfile(data1);
 	   
 	    count_att(att,data);
 	    count_att(att,data1);

 	    
 	    
 	    for(int i=0; i<att.size()+1; i++){
	    	w.add(0.0);
 	    }
     }
     
     public void count_att(ArrayList<String> att,ArrayList<String> data){
 	   
 	    
 	   
 	    
 		for(int k=0; k < data.size(); k++){
 				int order = -1;
 				for(int j=0; j<att.size();j++){
 					if(data.get(k).equals(att.get(j))){
 						order = j;
 					    break;
 					}	
 				}
 			
 			   if(order == -1)
 				att.add(data.get(k));
 		} 
     }
     
     public File[] getFilelist(){
	     try{
	    	 Scanner input = new Scanner(System.in);
	         BufferedReader in = new BufferedReader(new InputStreamReader(System.in));	
		     String path= in.readLine();
  	         File file=new File(path);
  	         File[] tempList = file.listFiles();
  	         return tempList;
	     }
	     catch(Exception e){
	    	 e.printStackTrace();
	    	 return null;
	     }
        
     }
     
     public  void calculate_wi(ArrayList<String> att){
 	    
 	    System.out.println("Please input the address of the train ham file");
 	    File[] tempList1 = getFilelist();
 	    System.out.println("Please input the address of the train spam file");
 	    File[] tempList2 = getFilelist();     
 	    updateW(tempList1,tempList2,att);
     }
     
     public void updateW(File[] tempList,File[] tempList1,ArrayList<String> att){	    	   
    	    int num = 100;
	        double[] y = new double[tempList.length + tempList1.length];
	    	
	    	
	    	
	    	int[][] x = new int[tempList.length + tempList1.length][att.size()+1];
	        
	    	for(int i=0; i<tempList.length;i++){
	    	   getX(tempList[i],x[i],att);
	    	   String a = tempList[i].getName();
	    		 if(a.contains("spam")) y[i] = 1;
	    		 else  y[i] = -1;
	    	}
	    	
	    	for(int i=tempList.length; i<(tempList.length + tempList1.length); i++){
	    	    getX(tempList1[i-tempList.length],x[i],att);
	    	    String a = tempList1[i-tempList.length].getName();
	    	    if(a.contains("spam"))  y[i] = 1;
	    	    else y[i] = -1;
	    	}
	    	
	    	for(int i=0; i<tempList.length + tempList1.length; i++){
	    		x[i][0] = 1;
	    	}
	    	
	    	
	    	do{
	    		
	    		double[] sum = new double[tempList.length + tempList1.length];
	    		for(int i=0; i<tempList.length + tempList1.length; i++){
	    		double temp = 0.0;
 	    		for(int n=0; n<x[i].length; n++){
 	    			temp = temp +  w.get(n)*x[i][n];

 	    		}
 	    		if(temp>0) sum[i] = 1;
 	    		else  sum[i] = -1;
 	    		  	
 	    	}
	    		
	    		 for (int l = 0; l < tempList.length + tempList1.length; l++){
	    				for(int i = 0; i < w.size(); i++){
	    					 w.set(i, (double)(w.get(i) + lambda*(y[l]-sum[l])*x[l][i]));
	    		 }
	    	 }	
	    		 
	    		 num--;
//	    		 System.out.println(w.get(0));
	    	}while(num>=1);
	  
	    	  	
     }
     
     
     public void getX(File tempList,int[] x,ArrayList<String> att){
    	 try{
    		 
    			 BufferedReader reader = new BufferedReader(new FileReader(tempList));
	    		 String line = null;
	    		 
	    		 while((line=reader.readLine())!=null){
	    			 String item[] = line.split(" ");
	    			 for(int k=0; k<item.length; k++){
	    				 for(int j=0; j<att.size(); j++){
	    						 if(att.get(j).equals(item[k]))
	    							 x[j+1] ++;
	    				 }
	    			 }
	    		 }	 		
    	 }catch(Exception e){
	    	 e.printStackTrace();
	     }
    	 
     }
     
     
     public int[] Class(ArrayList<String> att){
   	  
 	    
 	    int[] array1 = {0,0};
 	    int ham = 0;
 	    int spam = 0;
 	    double prob = 0.0;
 	    File[] templist = getFilelist();
 	    array1[0] = templist.length;
 	    for(int i=0; i<templist.length; i++){
 	    	double sum = 0.0;
 	    	int[] x= new int[att.size()+1];
 	    	getX(templist[i],x,att);
 	    	x[0] = 1;
 	    	
 	    	for(int j=0; j<x.length; j++){
 	    		sum = sum + w.get(j)*x[j];
 	    	}
 	    	
 	    	if(sum > 0){		
 	    		spam ++;
 	    	}
 	    	else {  
 	    		ham ++;
 	    	}
 	    }
 	    if(templist[0].getName().contains("spam")) {
 	    	array1[1] = spam;
 	    	return array1;
 	    }
 	    else {
 	    	array1[1] = ham;
 	    	return array1;
 	    }
 	    
  }
     
     
     
     
}



