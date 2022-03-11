import java.util.*; 

public class Follower extends Thread{
	private boolean[] danceCard = {false, false, false, false, false, false, false, false}; //Dance card of follower
	public String[] dances = {"Waltz", "Tango", "Foxtrot", "Quickstep", "Rumba", "Samba", "Cha Cha", "Jive"}; // Used for Follower Debug Print Statement. 
	private int[] leaderCounter;
	private boolean acceptingInvites = false; // If dance card is filled
	private Queue<Pair<Leader, Integer>> inbox = new LinkedList(); 
	private DanceBuffer buff; 
	public int ID; 

	public Follower (int ID, int leaderCount, DanceBuffer buff){
		this.ID = ID;
		this.leaderCounter = new int[leaderCount];
		for(int i = 0; i< leaderCount; i++){ //Intialize the amount of time the Follower danced with this leader. 
			leaderCounter[i] = 0; 
		}
		this.buff = buff; 
	}

	/**
	 * Checks if all of the dance card is being checked. 
	 */ 
	public boolean checkDanceCard(){
		for(boolean x : danceCard){
			if(x == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Called when a leader sends a message to THIS follower. 
	 * Process the message and run the thread. 
	 */
	public synchronized void sendMessage(Pair<Leader,Integer> x){
		inbox.add(x); 
		if(acceptingInvites == false){ // If empty was empty, re-run the thread. 
			acceptingInvites = true; 
			this.run();
		} 
	}

	/**
	 * Accepts or deny whether a Follower will dance with the leader. 
	 * Assumption: Should be ran one at time based on the run function impelmentation. 
	 */
	public void acceptInvitation(Pair<Leader, Integer> leader){ 

		// Extract Leader ID. 
		int tempLeaderID = leader.first.ID;
		tempLeaderID --; //Leader 1-index translated to 0-index
		//System.out.printf("Leader %d attempting to dance %s with Follower %d \n", leader.first.ID, dances[leader.second], ID );

		// Checkers whether a Follower will dance with the leader.
		// If conditions are true, decline the invitation. 
		if(danceCard[leader.second] == true){ // Check if follower has danced on the dance card.
			return;
		}
		else if(leaderCounter[tempLeaderID] == 2){ // Check if Leader has danced with this follower twice
			return; 
		} 

		// Queues up for the Dance Room. 
		buff.put(leader.second, leader.first, this);

		// Track the dance. 
		leaderCounter[tempLeaderID] ++; 
		danceCard[leader.second] = true; 
		//printDebugString(); 
	}

	/**
	 * Follower Thread Run Function. 
	 */ 
	public void run(){ 

		while(acceptingInvites){	
			checkInbox(); 
		}
			
	}

	/**
	 * Print the relevant variable of Follower instance. 
	 */ 
	public void printDebugString(){
		
		System.out.printf("Follower %d:\n", this.ID);


		for(int i = 0; i< danceCard.length; i++){
			System.out.printf("%-15s", dances[i]); 
			if(danceCard[i] == false){ //Didn't find a partner
				System.out.printf("------\n");
			}else {
				//System.out.printf("with %d\n", danceCard[i]);
			}
		}
		System.out.println(); 
	}	

	/**
	 * Checks if there's a message waiting to be opened. 
	 */ 
	public void checkInbox(){
		Pair<Leader, Integer> message = inbox.poll();

		if( message != null){ // Inbox has message. 
			acceptInvitation(message);
		} else{ // Empty queue. 
			// System.out.println("Inbox empty"); 
			acceptingInvites = false; 
		}
	}


}