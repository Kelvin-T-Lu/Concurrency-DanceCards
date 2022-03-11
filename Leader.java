import java.util.*; 

public class Leader extends Thread{
	
	private boolean isSearch = true; // True if this leader is saerching. Variable that runs the thread. 
	private int[] danceCard= {0, 0, 0, 0, 0, 0, 0, 0}; // A Leader's dance card w/ ID of follower
	private List<Follower> followers; // List of follower thread to choose from. 
	private DanceBuffer buff; // Primary buffer for Leader and Followers.  
	private int numIteration = 0; // Signals thread for termination when reached a certain amount of iterations. 
	public int ID; // Thread ID. 


	public Leader(int ID, List<Follower> followers, DanceBuffer buff){
		this.ID = ID; 
		this.followers = followers; 
		this.buff = buff; 
	}


	/**
	 * Checks the danceCard if the Leader danced all of the dances. 
	 */
	public void checkDanceCard(){
		for(int x : danceCard){
			if(x == 0) {
				isSearch = true;
				return; 
			}
		}

		isSearch = false; 

	}

	/**
	 * Resets the iteration count of thread. 
	 * Usually called when a dance was accepted. 
	 */
	public void resetIteration(){
		numIteration = 0; 
	}

	/**
	 * Sends an invitation to a random follower with a random dance. 
	 */
	public void sendInvitation(){

		// Randomization declaration
		Random rand = new Random(); 

		int followerIndex = Math.abs(rand.nextInt() % followers.size()); 
		int danceIndex = Math.abs(rand.nextInt() % danceCard.length); 

		// If leader has already dance the specific dance, find another dance. 
		while(danceCard[danceIndex] != 0){
			danceIndex = Math.abs(rand.nextInt() % danceCard.length); 			
		}

		// System.out.printf("Leader sending message with dance: %d\n", danceIndex); 

		//Send the message
		Pair<Leader, Integer> temp = new Pair(this, danceIndex);
		followers.get(followerIndex).sendMessage(temp);

	}
	
	/**
	 * Leader's thread run function. 
	 */
	public void run(){
		// Keep sending messages until 
		//    1. The Dance Card is all filled out
		//    2. The Leader hasn't danced for a specific number of iterations. 
		while(isSearch){
			//System.out.printf("Leader searching, Iteration: %d\n", numIteration); 
			sendInvitation(); 
			checkDanceCard(); // Leader danced all of the dances. 
			if(numIteration > 100){
				isSearch = false; 
			}
			numIteration++; 
		}

		// Thread exiting...
		// Print the Leader
		this.printString(); 
	}

	/**
	 * Called when a leader danced with a follower. 
	 */
	public void placeDance(int followerID, int danceIndex){
		danceCard[danceIndex] = followerID; 
	}

	/**
	 * Print the information of the Leader 	thread. 
	 */
	public synchronized void printString(){
		buff.printString(ID, danceCard);		
	}	

}