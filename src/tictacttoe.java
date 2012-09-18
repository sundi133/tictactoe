
public class tictacttoe {

	/**
	 * @param args
	 */
	static char minmaxtable[][]= new char[3][3];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		char empty = '0';
		char you = 'U'; //You
		char machine = 'M'; //Machine
		
		for(int i=0;i<3;i++){
			for(int k=0;k<3;k++){
				minmaxtable[i][k]='0';
			}
		}
		//System.out.println("Machine always plays first");
		System.out.println("Enter Position as mentioned below : U denotes you, M denotes machine, E is empty");
		System.out.println(" 1 | 2 | 3 ");
		System.out.println(" ---------");
		System.out.println(" 4 | 5 | 6 ");
		System.out.println(" ---------");
		System.out.println(" 7 | 8 | 9 ");
		startPlay();
	}

	private static void startPlay() {
		calculateminmax();
		System.out.println("Enter number ");
		
		
	}

	private static void calculateminmax() {
		//if(full(minmaxtable)){
			return ;
		//}
		
	}

	private static boolean full() {
		// TODO Auto-generated method stub

		for(int i=0;i<3;i++){
			for(int k=0;k<3;k++){
				if(minmaxtable[i][k] != '0'){
				    //if()
					
				}else{
					
				}
				
			}
		}
		return false;
	}
	
	

}
