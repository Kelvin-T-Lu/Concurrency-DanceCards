import java.util.*; 

public class Dance {
	
	public static void main(String[] args) {
        // Intialize the number of leaders and followers. 
        // Intialize the Leader and Follower Thread, and store it inside a list. 
        //     Spawn the Leader Threads. 
		
        // Numbers Leaders and Follwers 
		int numLeaders=0; 
		int numFollowers=0; 

		List<Leader> leaders = new ArrayList(); 
		List<Follower> followers = new ArrayList(); 

		//Read Command Line Arguments
		if (args.length == 2) { // Intialize the count. 
            try {
                 numLeaders= Integer.parseInt(args[0]);
                 numFollowers = Integer.parseInt(args[1]);
                 if (numFollowers <= 0 || numLeaders <=0 ) {
                     throw new Exception();
                 }
            } 
            catch (Exception e) {
                System.err.println("Arguments 1 and 2 must be an integer.");
                System.exit(1);
            } 
        }
        else { // Error of arguments.
            System.out.println("Argument error.");
            System.exit(1);
        }
		
		DanceBuffer buffer = new DanceBuffer(); 


        //Spawn threads

        // Creates a list of Follower Thread References. Adds it to followers list. 
      	  for(int i = 0; i < numFollowers; i++){
        	Follower temp = new Follower(i+1, numLeaders, buffer);
        	followers.add(temp); 
        	//temp.start(); //Run Thread
        }

        // Creates a list of Leader Thread References. Adds it to Leaders list. 
        for(int i = 0; i < numLeaders; i++){
        	Leader temp = new Leader(i+1, followers,buffer); 
        	leaders.add(temp); 
        	temp.start(); 
        }


	}

}