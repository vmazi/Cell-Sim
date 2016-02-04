import java.awt.Color;
public class CellSim{


	public static void main(String[] args){
		System.out.print("enter n: ");
		int n = IO.readInt();
		char[][] tissue = new char[n][n];
		

		System.out.print("Enter threshold: ");
		int threshold = IO.readInt();
		
		System.out.print("Enter max rounds: ");
		int maxRounds = IO.readInt();
		
		System.out.print("Enter frequency: ");
		int frequency = IO.readInt();
		
		

		int percentX;
		while (true){
			System.out.print("Enter percent X: ");
			percentX = IO.readInt();
			if (percentX <= 100&&percentX>=0){					
				break;
			}
			else { 
				IO.reportBadInput();
				System.out.println("Please enter a number greater than or equal to zero and less than or equal to 100");
				
			}
		}
		
		int percentBlank;
		while (true){
			System.out.print("Enter percent Blank: ");   
			percentBlank = IO.readInt();
			if (percentBlank <=100&&percentBlank>=0&&percentBlank+percentX<=100){
				break;
			}
			else { 
				IO.reportBadInput();
				System.out.println("Please enter a number greater than or equal to zero, less than or equal to 100, and less than or equal to "+(100-percentX));
				
			}
		}
		int percentY;
		while (true){
			System.out.print("Enter percent Y: ");   
			percentY = IO.readInt();
			if (percentY <=100&&percentY>=0&&percentBlank+percentX+percentY<=100){
				break;
			}
			else { 
				IO.reportBadInput();
				System.out.println("Please enter a number greater than or equal to zero, less than or equal to 100, and less than or equal to "+(100-percentX-percentBlank));
				
			}
		}
		
		CellSimGUI grid = new CellSimGUI(n,10);
		assignCellTypes(tissue,percentBlank,percentX,percentY);			
		
		System.out.println("Initial board: ");
		printTissue(tissue);
		CellSimGUI.setColor(tissue,grid);
		
		int num_rounds=0;
		int unSatisfied=0;
		double percent=0.0;
		int num_movements=0;
		
		for(int i=1; i<=maxRounds; i++){
			num_movements+=moveAllUnsatisfied(tissue, threshold);
			tissue=moveAllCells(tissue, threshold);
			num_rounds++;
			if(i%frequency==0){	
				System.out.println();
				printTissue(tissue);
				CellSimGUI.setColor(tissue,grid);
			}
			if(boardSatisfied(tissue, threshold)){
				System.out.println("your board is satisfied and needed "+ num_rounds +" rounds." );
				break;
			}
			if(i==maxRounds){
				for(int a=0; a<tissue.length; a++){
					for(int b=0; b<tissue[0].length; b++){
						if(!isSatisfied(tissue, a, b, threshold)){
							unSatisfied++;
						}
					}
				}
				percent=((double)unSatisfied/Math.pow(n,2))*100;
				System.out.println(percent + "%  are unsatisfied.");
				
			}
		}
		System.out.println("Your final tissue is: ");
		printTissue(tissue);
		CellSimGUI.setColor(tissue,grid);
		System.out.println("There were " + num_movements+ " movements");
		
		
	
		
	}
		
		
	
	

	
	
	
	
	/**
	* Given a tissue sample, prints the cell make up in grid form
	*
	* @param tissue a 2-D character array representing a tissue sample
	* 
	***/
	public static void printTissue(char[][] tissue){
		int i = 0;
		int j = 0;
		for ( i =0; i<tissue.length; i++ ){					// prints the array
			for (j=0; j<tissue[i].length;j++){
				
				System.out.print(tissue[i][j]+" ");
				if (j==tissue[i].length-1){
					System.out.println("");
					continue;
				}
			}
		}
		
	
	}
	

	/**
	* Given a blank tissue sample, populate it with the correct cell makeup given the parameters. 
	* Cell type 'X' will be represented by the character 'X'
	* Cell type 'O' will be represented by the character 'O'
	* Vacant spaces will be represented by the character ' '
	*
	* Phase I: alternate X and O cells throughout, vacant cells at the "end" (50% credit)
	*		e.g.:	'X' 'O' 'X' 'O' 'X'
	*				'O' 'X' 'O' 'X' 'O'
	*				'X' 'O' 'X' 'O' 'X'
	*				' ' ' ' ' ' ' ' ' '
	*				' ' ' ' ' ' ' ' ' '
	*
	* Phase II: Random assignment of all cells (100% credit)
	*
	* @param tissue a 2-D character array that has been initialized
	* @param percentBlank the percentage of blank cells that should appear in the tissue
	* @param percentX Of the remaining cells, not blank, the percentage of X cells that should appear in the tissue. Round up if not a whole number
	*
	**/
	public static void assignCellTypes(char[][] tissue, int percentBlank, int percentX, int percentY){
		int i,j;
		double percentX1 = (double)percentX/100;
		percentX1 = percentX1 * tissue.length*tissue[0].length;
		double percentBlank1 = (double) percentBlank/100;
		percentBlank1 = percentBlank1 * tissue.length*tissue[0].length;
		double percentY1 = (double)percentY/100;
		percentY1 = percentY1 * tissue.length*tissue[0].length;
		int percentBlank2 = (int)percentBlank1;
		int percentX2 = (int)percentX1;
		int percentY2 = (int)percentY1;
		
		
			
			
		while(percentX2>0){
			i = (int)(Math.random()*tissue.length);					//randomly selects a cell
			j = (int)(Math.random()*tissue.length);
			if(tissue[i][j]=='\0'){
				tissue[i][j] = 'X';							//if cell is empty, stick an X in
				percentX2--;
			}
			else{
				continue;
			}
		}												//do this until percentX of cells have been filled
		
		
		while (percentBlank2>0){											
			i = (int)(Math.random()*tissue.length);				//randomly selects cell
			j = (int)(Math.random()*tissue.length);
			if(tissue[i][j]=='\0'){						//if cell is empty, stick a ' ' in
				tissue[i][j] = ' ';						
				percentBlank2--;
			}										
			else{										// if not, go to line 104
				continue;
			}
		}												// do this until percentBlank of cells have been filled
		
		while (percentY2>0){											
			i = (int)(Math.random()*tissue.length);				//randomly selects cell
			j = (int)(Math.random()*tissue.length);
			if(tissue[i][j]=='\0'){						//if cell is empty, stick a ' ' in
				tissue[i][j] = 'Y';						
				percentY2--;
			}										
			else{										// if not, go to line 104
				continue;
			}
		}	
		
		for(i=0;i<tissue.length;i++){						// fill all remaining cells with 'O'
			for(j=0;j<tissue[i].length;j++){				
				if(tissue[i][j]=='\0' ){
					tissue[i][j] = 'O';
				}
				else{
					continue;
				}
			}
		}
		
		
		return;
	}

	/**
	    * Given a tissue sample, and a (row,col) index into the array, determines if the agent at that location is satisfied.
	    * Note: Blank cells are always satisfied (as there is no agent)
	    *
	    * @param tissue a 2-D character array that has been initialized
	    * @param row the row index of the agent
	    * @param col the col index of the agent
	    * @param threshold the percentage of like agents that must surround the agent to be satisfied
	    * @return boolean indicating if given agent is satisfied
	    *
	    **/
	public static boolean isSatisfied(char[][] tissue, int row, int col, int threshold){
		double dthreshold=(double)threshold/100;
		
		boolean isSatisfied=false;
		int count=0;;
		int countX=0;
		int countO=0;
		int countY = 0;
		double percentageSame=0.0;
		if(row < 0 || col < 0 || threshold < 0){
			IO.reportBadInput();
			return false;
			
		}
		if(tissue.length != tissue[row].length){
			IO.reportBadInput();
			return false;
		}
		if(tissue.length==1 && tissue[tissue.length-1].length==1){
			isSatisfied=true;
			return isSatisfied;
			
		}
		if(row==0 && col==0) {
			
			if(tissue[0][1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[0][1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[0][1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[1][1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[1][1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[1][1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[1][0]=='X'){
				countX++;
				count++;
				
			}
			else if(tissue[1][0]=='O'){
				countO++;
				count++;
			}
			else if(tissue[1][0]=='Y'){
				countY++;
				count++;
			}
		}
		else if(row==tissue.length-1 && col==0){
			
			if(tissue[row-1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col+1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col+1]=='O'){
				countY++;
				count++;
			}
			if(tissue[row][col+1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col+1]=='Y'){
				countY++;
				count++;
			}
		}
		else if(row==0 && col==tissue[row].length-1){
			
			if(tissue[row][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row+1][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row+1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col]=='Y'){
				countY++;
				count++;
			}
		}
		else if(row==tissue.length-1 && col==tissue[row].length-1){
			
			if(tissue[row][col-1]=='x'){
				countX++;
				count++;
			}
			else if(tissue[row][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col]=='Y'){
				countY++;
				count++;
			}
		}
		else if(col==0){
			
			if(tissue[row-1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col+1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col+1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row][col+1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col+1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col+1]=='X'){
				countX++;
				count++;
		}	
			else if(tissue[row+1][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col+1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row+1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col]=='Y'){
				countY++;
				count++;
			}
	}
		else if(col==tissue[row].length-1){
			
			if(tissue[row-1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col-1]=='Y'){
				countY++;
				count++;
			}

			if(tissue[row+1][col-1]=='X'){
				countX++;
				count++;
		}	
			else if(tissue[row+1][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row+1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col]=='Y'){
				countY++;
				count++;
			}
		}
		else if(row==0){
			
			if(tissue[row][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row+1][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row+1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col+1]=='X'){
				countX++;
				count++;
			}	
			else if(tissue[row+1][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col+1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row][col+1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col+1]=='Y'){
				countY++;
				count++;
			}
		}
		else if(row==tissue.length-1){
			
			if(tissue[row][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col+1]=='X'){
				countX++;
				count++;
			}	
			else if(tissue[row-1][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col+1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row][col+1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col+1]=='Y'){
				countY++;
				count++;
			}
		}
		else{
			if(tissue[row][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col-1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row-1][col+1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row-1][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row-1][col+1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row][col+1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row][col+1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col+1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row+1][col+1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col+1]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row+1][col]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col]=='Y'){
				countY++;
				count++;
			}
			if(tissue[row+1][col-1]=='X'){
				countX++;
				count++;
			}
			else if(tissue[row+1][col-1]=='O'){
				countO++;
				count++;
			}
			else if(tissue[row+1][col-1]=='Y'){
				countY++;
				count++;
			}
			
		}
		
		if(count==0){
			return true;
		}
		
		
		if(tissue[row][col]=='X'){
			percentageSame=(double)countX/count;
			if(percentageSame>=dthreshold){
				isSatisfied=true;
				return isSatisfied;
				
			}
			else
				return false;
		}
		else if(tissue[row][col]=='O'){
				percentageSame=(double)countO/count;
				if(percentageSame>=dthreshold){
					isSatisfied=true;
					return isSatisfied;
				}
				else
					return false;
				
			
			
			}
		else if(tissue[row][col]=='Y'){
			percentageSame=(double)countY/count;
			if(percentageSame>=dthreshold){
				isSatisfied=true;
				return isSatisfied;
			}
			else
				return false;
			
		
		
		}
		else{
			return true;
		}
		
		
			
			
		}
	    /**
	    * Given a tissue sample, determines if all agents are satisfied.
	    * Note: Blank cells are always satisfied (as there is no agent)
	    *
	    * @param tissue a 2-D character array that has been initialized
	    * @return boolean indicating whether entire board has been satisfied (all agents)
	    **/
	    public static boolean boardSatisfied(char[][] tissue, int threshold){
	    for(int i = 0;i<tissue.length;i++){
	    	for(int j =0;j<tissue[i].length;j++){
	    		if(isSatisfied(tissue,i,j,threshold)){
	    			continue;
	    		}
	    		else{
	    			return false;
	    			
	    		}
	    	}
	 }  
	    return true;
	    }
	  
	   
	    /**
	    * Given a tissue sample, move all unsatisfied agents to a vacant cell
	    *
	    * @param tissue a 2-D character array that has been initialized
	    * @param threshold the percentage of like agents that must surround the agent to be satisfied
	    *
	    **/
	    
	  

	   
	 
	    public static char[][] moveAllCells(char[][] tissue, int threshold){
	    	char[][] temp=tissue;
	    	int num=0;
	    	char replacement=' ';
	    	for(int i=0; i<temp.length; i++){
	    		for(int j=0; j<temp[0].length; j++){
	    			if(!isSatisfied(temp, i, j, threshold)){
	    				for(int x=0; x<temp.length; x++){
	    					for(int y=0; y<temp[0].length; y++){
	    						replacement=temp[i][j];
	    						temp[i][j]=temp[x][y];
	    						temp[x][y]=replacement;
	    						
	    						if(isSatisfied(temp, i, j, threshold) && isSatisfied(temp, x, y, threshold)){
	    							num +=2;
	    							continue;
	    							
	    						}
	    						else{
	    							temp[x][y]=temp[i][j];
	    							temp[i][j]=replacement;
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
	    	return temp;
	    }		
			
	    
	  
	    
	    

	    public static int moveAllUnsatisfied(char[][] tissue, int threshold){
	    	char[][] temp=tissue;
	    	int num=0;
	    	char replacement=' ';
	    	for(int i=0; i<temp.length; i++){
	    		for(int j=0; j<temp[0].length; j++){
	    			if(!isSatisfied(temp, i, j, threshold)){
	    				for(int x=0; x<temp.length; x++){
	    					for(int y=0; y<temp[0].length; y++){
	    						replacement=temp[i][j];
	    						temp[i][j]=temp[x][y];
	    						temp[x][y]=replacement;
	    						if(isSatisfied(temp, i, j, threshold) && isSatisfied(temp, x, y, threshold)){
	    							num+=2;
	    							continue;
	    							
	    						}
	    						else{
	    							temp[x][y]=temp[i][j];
	    							temp[i][j]=replacement;
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
	    	return num;
	    }
}