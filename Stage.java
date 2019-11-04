// =============================================================================
/**
 * The Stage class holds an array of dancers and holds the majority of the code 
 * for the regression analysis in the method nextFormation.
 *
 * @author Sophie Koh
 **/
// =============================================================================
 
// IMPORTS
 
// =============================================================================
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
 
// =============================================================================

 
 
 
// =============================================================================
public class Stage{
// =============================================================================


// =============================================================================
// FIELDS
// =============================================================================
public static int WIDTH;
public static int LENGTH;
public Dancer[] dancers; 
public static int cumulativeDistCount;
public static double[] cumulativeDist;

// =============================================================================
// CONSTRUCTORS
// =============================================================================
public Stage(int w,int l){
	WIDTH=w;
	LENGTH=l;
	}
public Stage(int w,int l, Dancer[] d){
	WIDTH=w;
	LENGTH=l;
	dancers =d;
	}
public Stage(int w,int l, int d){
	WIDTH=w;
	LENGTH=l;
	dancers = new Dancer[d];
	}
// =============================================================================

// =============================================================================
// METHODS
// =============================================================================

// =============================================================================
// method nextFormation returns a Dancer[] of the dancers in the first array in 
// the positions of the second array, by finding the least cumulative distance
// that everyone will have to travel to get to the next spot, the regression algorithm
// =============================================================================
public Dancer[] nextFormation(Dancer[] nextDancers){
		Dancer[] d = new Dancer[dancers.length];
		for(int i=0;i<dancers.length;i++){ 
			d[i]=new Dancer(dancers[i].getPositionX(),dancers[i].getPositionY(), dancers[i].getName(), dancers[i].getColor());
		}
		//finds the number of permutations of the dancers
		int numCombinations=factorial(dancers.length); 

		//cumulativeDist stores the cumulative distance of a permutation of dancers
		cumulativeDist=new double[numCombinations]; 
		cumulativeDistCount=0;

		//finding permutations of dancers and store
		ArrayList<Dancer[]> permutations = new ArrayList();
		permute(d,0,dancers.length-1, permutations);

		//for all of the permutations, find the cumulative distance
		for(int j=0; j<numCombinations; j++)
		{
			double totalDist = findCumulativeDist(permutations.get(j),nextDancers);
			cumulativeDist[j]=totalDist;
		}

		//finds the index of the smallest cumulative distance
		int indexMin=0;
		for(int i=0; i<cumulativeDist.length; i++)
		{
			if(cumulativeDist[i]<cumulativeDist[indexMin])
			{
				indexMin=i;
			}
		}
		//finds the permutation of the smallest cumulative distance
		Dancer[] permutedDancers = permutations.get(indexMin);

		//loops through all of the dancers, and sets the positions to the nextDancers
		for(int i=0;i<dancers.length;i++){
			for(int j=0;j<dancers.length;j++){
				if(d[j]==permutedDancers[i])
				{
					d[j].setPositionX(nextDancers[i].getPositionX());
					d[j].setPositionY(nextDancers[i].getPositionY());
				}
			}
		}
		return d;
		
	} //nextFormation()
	// =============================================================================

	// =============================================================================
	//factorial is a recursive method, returns the number of permutations
	public static int factorial(int n){
		if(n==0)
			return 1;
		else
			return n*factorial(n-1);
	} //factorial()
	// =============================================================================

	
	public static void permute(Dancer[] d,int l, int r, ArrayList<Dancer[]> permutations)
	{
		if(cumulativeDistCount<cumulativeDist.length)
		{
		if(l==r)
		{
			if(cumulativeDistCount<cumulativeDist.length)
			{
				Dancer[] d0 = new Dancer[d.length];
				for(int j=0;j<d.length;j++)
				{
					d0[j]=d[j];
				}
				permutations.add(cumulativeDistCount, d0);
			}
			cumulativeDistCount++;
		}
		else
		{
			for (int i = l; i <= r; i++) 
            { 
                swap(d,l,i);
                permute(d, l+1, r,permutations); 
                swap(d,l,i);
            } 
		}
		}
	} //permute()
	public static Dancer[] swap(Dancer[] array, int x, int y) //swaps two elements in the array
	{
		Dancer temp = array[x];
		array[x]=array[y];
		array[y]=temp;
		return array;
	} //swap()

	public static double findCumulativeDist(Dancer[] current,Dancer[] next)
	{
		double distTotal=0;
		for(int i=0;i<current.length;i++)
		{
			double distX = Math.pow(1.0*(next[i].getPositionX()-current[i].getPositionX()),2);
			double distY = Math.pow(1.0*(next[i].getPositionY()-current[i].getPositionY()),2);
			double dist=Math.pow(distX+distY,0.5);
			distTotal+=dist;
		}
		return distTotal;
	}//findCumulativeDist()

// =============================================================================
} // class Stage
// =============================================================================

