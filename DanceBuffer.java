import java.util.*; 


public class DanceBuffer{

	/**
	 * String representation of each dance, allows for printing. 
	 */
	public String[] stringDance = {"Waltz", "Tango", "Foxtrot", "Quickstep", "Rumba", "Samba", "Cha Cha", "Jive"}; 


	/**
	 * Waltz, Tango, Foxtrot, Quickslap, Rumba, Samba, Cha Cha, Jive
	 * True if dance is available, false otherwise.
	*/ 
	private boolean[] dances; //{true, true, ...} 

	/**
	 * Declare a Dance buffer and intialize each of the possible dances. 
	 */
	public DanceBuffer(){
		dances = new boolean[8];
		for(int i = 0; i<dances.length; i++){
			dances[i] = true; 
		}
	} 


	/**
	 * Return if a specific dance is available.
	 */
	public boolean isAvailable(int index){
		return dances[index]; 
	}

	/**
	 * A thread will call this to reserve a dance with leader and follower. 
	 */
	public void put(int index, Leader x, Follower y){
		// If the spot is reserved, wait in line. 
		// Otherwise reserve the spot, suspend the Leader process (prevents ending messages). 
		// Signal the dance to the leader and reset its iteration. 
		// Calls gets to free the dance space.  
		while( !isAvailable(index)){
			try {
				this.wait();
				x.wait();
			}
			catch (Exception e){
				// System.out.println ("put wait interuption\n");
			} 
		}
		//System.out.println("Before wait"); 
		try{
			//x.wait(); 
			//y.wait();
		} catch (Exception e) {
			//System.out.println("Wait interuption.\n"); 
		}
		dances[index] = false; 
		//Debug print
		x.placeDance(y.ID, index);
		x.resetIteration(); 
		//x.printString(); 
		x.resetIteration(); 
		get(index, x, y); 

	}

	/**
	 * Frees up a dance and signals all threads in the dance queue. 
	 */
	public synchronized void get(int index, Leader x, Follower y){
		dances[index] = true;
		// System.out.println("Entered Get\n"); 
		this.notifyAll(); 
	}	


	/**
	 * A method that prints out a leader thread once it terminates. 
	 */
	public synchronized void printString(int ID, int[] danceCard){
		System.out.printf("Leader %d:\n", ID);


		for(int i = 0; i< dances.length; i++){
			System.out.printf("%-15s", stringDance[i]); 
			if(danceCard[i] == 0){ //Didn't find a partner
				System.out.printf("------\n");
			}else {
				System.out.printf("with %d\n", danceCard[i]);
			}
		}
		System.out.println(); 
	}	

}