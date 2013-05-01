package ablebaker;
import java.util.*;
/**
 *
 * @author vinator
 */
public class AbleBaker {
    public static Server Able = new Server('a');
    public static Server Baker = new Server('b');
    public static Customer[] Customers = new Customer[10];
    public static int cumulativeInterarrival = 0;
    
    public static Queue<Customer> customerQueue = new LinkedList<Customer>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Customers[0] = new Customer();
        Customers[0].arrivalTime = 0;
        Customers[0].serviceStartTime = 0;
        Customers[0].serviceTime = Able.serviceTime('a');
       // Able.busy = true;
        Able.whenAvailable = Customers[0].serviceTime;
        
        System.out.println("Cust    Inter   Cummu   Able    Baker   Serve   Start");
        for(int i = 1; i < 10; i++){
            Customers[i]= new Customer();
                    
            cumulativeInterarrival += Customers[i].arrivalTime;
            update();
            
            //If Able frees up 
            if(Able.whenAvailable <= Baker.whenAvailable 
                   && Able.whenAvailable <= cumulativeInterarrival){
                
                Able.whenAvailable = Able.whenAvailable + Customers[i].serviceTime;
                Customers[i].serviceStartTime = cumulativeInterarrival;
                Customers[i].serviceTime = Able.serviceTime('a');
                Able.whenAvailable = cumulativeInterarrival 
                        + Customers[i].serviceTime;
            
            //If Baker frees up
            } else if (Baker.whenAvailable <= cumulativeInterarrival){
                Baker.whenAvailable = Baker.whenAvailable + Customers[i].serviceTime;
                
                Customers[i].serviceStartTime = cumulativeInterarrival;
                Customers[i].serviceTime = Baker.serviceTime('b');
                Baker.whenAvailable = cumulativeInterarrival 
                        + Customers[i].serviceTime;
                
            //If neither free up, add them to the Queue
            } else {
                customerQueue.add(Customers[i]);
            }
            
            System.out.println(i + "        " +Customers[i].arrivalTime+"       "+
                    cumulativeInterarrival+"        "+Able.whenAvailable+"      "+
                    Baker.whenAvailable+"       "+Customers[i].serviceTime+"        "+
                    Customers[i].serviceStartTime);
        //testing
        }
//        for(int i = 0; i < 10; i++){
//            System.out.print(Customers[i].serviceTime+" ");
//            System.out.println(Customers[i].arrivalTime);
//        }
    }
//-----------------------------------------------------------------------------
//update()
    public static void update(){
        while(!customerQueue.isEmpty()){
            if(Able.whenAvailable <= Baker.whenAvailable 
                   && Able.whenAvailable <= cumulativeInterarrival){
                Customer newCustomer = customerQueue.remove();
                newCustomer.serviceTime = Able.serviceTime('a');
                Able.whenAvailable = Able.whenAvailable + newCustomer.serviceTime;
            } else if (Baker.whenAvailable <= cumulativeInterarrival){
                Customer newCustomer = customerQueue.remove();
                newCustomer.serviceTime = Baker.serviceTime('b');
                Baker.whenAvailable = Baker.whenAvailable + newCustomer.serviceTime;
            } else {
                break;
            }
        }
    }
}


