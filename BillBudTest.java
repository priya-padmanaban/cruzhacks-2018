import java.util.*;
import java.text.*;

class BillBudTest {

    public static void main(String[] args) {

    	Scanner in = new Scanner(System.in);

    	System.out.println("How many people in your party?");
    	int usercount = in.nextInt();

    	ArrayList<String> users = new ArrayList<String>();
    	for(int i = 0; i < usercount; i++){
    		System.out.println("Enter name of person: ");
    		users.add(in.next());
    	}

    	ArrayList<String> items = new ArrayList<String>();
    	ArrayList<Double> prices = new ArrayList<Double>();

    	while (true) {
            System.out.println("Enter name of item: ");
            items.add(in.next());
            System.out.println("Enter price of item (no $): ");
            prices.add(in.nextDouble());
            System.out.println("Done adding? y/n");
            if(in.next().equals("y"))
            	break;
        }

        double totalprice = 0;
        for(int i = 0; i < prices.size(); i++){
        	totalprice += prices.get(i);
        }

        System.out.println("Enter the tax as a dollar amount (no $): ");
        double tax = in.nextDouble();

        // this 2d array stores only 1s and 0s, 1 if the user is paying for the item, 0 if not
        int[][] shared = new int[usercount][items.size()];

        for(int i = 0; i < usercount; i++){
        	for(int j = 0; j < items.size(); j++){
        		System.out.println("Is " + users.get(i) + " paying for " + items.get(j) + "? y/n");
        		if(in.next().equals("y"))
        			shared[i][j] = 1;
        	}
        }

        // this array stores the column totals
        int[] totalshared = columnSum(shared);

        // divide items by number in totalshared
        double[] divamounts = new double[items.size()];
        for(int i = 0; i < items.size(); i++){
        	divamounts[i] = (double)(prices.get(i)) / (double)(totalshared[i]);
        }

        System.out.println("Tip percentage? (no %)");
        double tip = (double)(in.nextInt())/100;


        // figure out everyone's amount
        double[] amounts = new double[usercount];
        for(int i = 0; i < usercount; i++){
        	double total = 0;
        	for(int j = 0; j < items.size(); j++){
        		if(shared[i][j] == 1){
        			total += divamounts[j];
        		}
        	}
        	amounts[i] = total;
        }

        // add tip to bills
        for(int i = 0; i < usercount; i++){
        	amounts[i] += amounts[i]*tip;
        }

        // add tax to bills
        double taxpercent = (double)(tax/totalprice);
        for(int i = 0; i < usercount; i++){
        	amounts[i] += amounts[i]*taxpercent;
        }


        System.out.println();
        System.out.println("THE SPLIT BILL: ");

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        for(int i = 0; i < usercount; i++){
        	String moneyString = formatter.format(amounts[i]);
        	System.out.println(users.get(i) + ": " + moneyString);
        }
    }

    public static int[] columnSum(int [][] array){

    	int size = array[0].length; 
    	int temp[] = new int[size];

    	for (int i = 0; i < array.length; i++){
        	for (int j = 0; j < array[i].length; j++){
            	temp[j] += array[i][j];  
        	}
    	}
    	
    	return temp;  
	}
}