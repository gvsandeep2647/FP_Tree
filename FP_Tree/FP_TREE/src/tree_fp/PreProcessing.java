package tree_fp;

import java.io.*;
import java.util.*;

import org.omg.PortableInterceptor.INACTIVE;



public class PreProcessing {
	public ArrayList<int []> data = new ArrayList<int[]>();
	public PreProcessing(String filename) {
		try{
			inputHandle(filename,data);
		}catch(Exception e){
			System.out.println("\nError In file Reading " + e);
		}
	}

	public void inputHandle(String filename,ArrayList<int[]> data)throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line=null;


		ArrayList<Integer> pregnancy = new ArrayList<Integer>();
		ArrayList<Integer> plasma = new ArrayList<Integer>();
		ArrayList<Integer> blood_pressure = new ArrayList<Integer>();
		ArrayList<Integer> skinFold = new ArrayList<Integer>();
		ArrayList<Integer> serum = new ArrayList<Integer>();
		ArrayList<Double> bmi = new ArrayList<Double>();
		ArrayList<Double> pedigree = new ArrayList<Double>();
		ArrayList<Integer> age = new ArrayList<Integer>();
		ArrayList<Boolean> diabetes = new ArrayList<Boolean>();

		while((line=br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line,",");

			while(st.hasMoreTokens()){
				pregnancy.add(Integer.parseInt(st.nextToken()));
				plasma.add(Integer.parseInt(st.nextToken()));
				blood_pressure.add(Integer.parseInt(st.nextToken()));
				skinFold.add(Integer.parseInt(st.nextToken()));
				serum.add(Integer.parseInt(st.nextToken()));
				bmi.add(Double.parseDouble(st.nextToken()));
				pedigree.add(Double.parseDouble(st.nextToken()));
				age.add(Integer.parseInt(st.nextToken()));
				diabetes.add(Integer.parseInt(st.nextToken())==1?true:false);
			}
		} 


		discPregnancy(pregnancy);
		discPlasma(plasma);
		discBP(blood_pressure);
		discSkinFold(skinFold);
		discSerum(serum);
		discBMI(bmi);
		discPedigree(pedigree);
		discAge(age);
		
		handleMissing(age);
		handleMissing(serum);
		handleMissing(skinFold);
		handleMissing(blood_pressure);
		handleMissing(plasma);
		handleMissing(pregnancy);
		
		handleMissingDouble(pedigree);
		handleMissingDouble(bmi);
		br.close();
	}
	/**
	 * @param pregnancy Takes in an ArrayList of Pregnancies and classifies into sub categories as mentioned.
	 * The Classification Principle is Equal Frequency Classes
	 */
	public void discPregnancy(ArrayList<Integer> pregnancy){
		for(int i=0;i<pregnancy.size();i++)
		{
			if(pregnancy.get(i)==0)
				pregnancy.set(i,1);
			else if(pregnancy.get(i)==1)
				pregnancy.set(i,2);
			else if(pregnancy.get(i)==2)
				pregnancy.set(i,3);
			else if(pregnancy.get(i)==3 ||pregnancy.get(i)==4)
				pregnancy.set(i,4);
			else if(pregnancy.get(i)>=5 && pregnancy.get(i)<=7)
				pregnancy.set(i,5);
			else
				pregnancy.set(i,5);
		}
		String categories[] = {"0","1","2",">=3 and <=4",">=5 and <=7",">7"};
		DataRef.subclasses.put(1,categories);
	}
	
	/**
	 * @param plasma Takes in an ArrayList of Plasma Glucose Conc. and classifies into sub categories as mentioned.
	 * The Classification Principle is Equal Frequency Classes
	 * The function feeds -1 in case of missing values which in this data set have been represented by 0
	 */
	public void discPlasma(ArrayList<Integer> plasma){
		for(int i=0;i<plasma.size();i++)
		{
			if(plasma.get(i)==0)
				plasma.set(i,-1);
			else if(plasma.get(i)>=44 &&plasma.get(i)<=98)
				plasma.set(i,1);
			else if(plasma.get(i)>=99 && plasma.get(i)<=110)
				plasma.set(i,2);
			else if(plasma.get(i)>=111 && plasma.get(i)<=126)
				plasma.set(i,3);
			else if(plasma.get(i)>=127 && plasma.get(i)<=150)
				plasma.set(i,4);
			else
				plasma.set(i,5);
		}
		String categories[] = {"44-98","99-110","111-126","127-150","150+"};
		DataRef.subclasses.put(2,categories);
	}

	/**
	 * @param blood_pressure Takes in an ArrayList of Blood Pressures and classifies into sub categories as mentioned. 
	 * The classification into classes is based on the domain knowledge.
	 * The function feeds -1 in case of missing values which in this data set have been represented by 0
	 */
	public void discBP(ArrayList<Integer> blood_pressure){
		for(int i=0;i<blood_pressure.size();i++)
		{
			if(blood_pressure.get(i)==0)
				blood_pressure.set(i,-1);
			else if(blood_pressure.get(i)<=60)
				blood_pressure.set(i,1);
			else if(blood_pressure.get(i)<=80)
				blood_pressure.set(i,2);
			else if(blood_pressure.get(i)<=90)
				blood_pressure.set(i,3);
			else
				blood_pressure.set(i,4);
		}
		String categories[] = {"<=60",">60 and <=80",">80 and <=90",">90"};
		DataRef.subclasses.put(3,categories);
	}


	public void discSkinFold(ArrayList<Integer> skinFold){
		
	}
	public void discSerum(ArrayList<Integer> serum){

	}


	/**
	 * @param bmi Takes in an ArrayList of BMIs and classifies into sub categories as mentioned.
	 * The classification into classes is based on the domain knowledge.
	 * The function feeds -1 in case of missing values which in this data set have been represented by 0
	 */
	public void discBMI(ArrayList<Double> bmi){
		for(int i=0;i<bmi.size();i++)
		{
			if(bmi.get(i)==0)
				bmi.set(i,-1.0);
			else if(bmi.get(i)<=18.5)
				bmi.set(i,1.0);
			else if(bmi.get(i)<=25)
				bmi.set(i,2.0);
			else
				bmi.set(i,3.0);
		}
		String categories[] = {"<=18.5",">18.5 and <=25",">25"};
		DataRef.subclasses.put(6,categories);
	}


	public void discPedigree(ArrayList<Double> pedigree){

	}
	
	/**
	 * @param age Takes in an ArrayList of Ages and classifies into sub categories as mentioned.
	 * This discretisation function uses the equal width stratergy to classify data
	 * Missing data is represented as -1
	 */
	public void discAge(ArrayList<Integer> age){
		int min = 100,max = 0;
		for(int i=0;i<age.size();i++){
			if(age.get(i)>max)
				max = age.get(i);
			if(age.get(i)<min)
				min = age.get(i);
		}
		int width = (max-min)/5;
		for(int i=0;i<age.size();i++)
		{
			if(age.get(i)==0)
				age.set(i,-1);
			else
			{
				for(int j=1;j<6;j++)
				{
					if(age.get(i)<= min+(j*width))
						age.set(i,j);
				}
			}
		}
		String temp[] = new String[5];
		for(int i=1;i<6;i++)
		{
			String temp1 =" >= "+ Integer.toString(min+(i-1)*width)+ " and <= " + Integer.toString(min+i*width);
			temp[i-1] = temp1;
		}
		DataRef.subclasses.put(8,temp);
	}

	/**
	 * @param list The list which contains Missing Values
	 * Fills the missing values with the class which occurs the most.
	 */
	public void handleMissing(ArrayList<Integer> list){
		TreeSet<Integer> unique = new TreeSet<Integer>();
		for(int i=0;i<list.size();i++)
			unique.add(list.get(i));
		
		HashMap<Integer, Integer> freqTable = new HashMap<Integer,Integer>();
		
		for(Integer temp : unique){
			freqTable.put(temp, freqTable.get(temp)+1);
		}
		int max = 0;
		for(Integer temp : unique){
			if(freqTable.get(temp)>max)
				max = temp;
		}
		
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i)==-1)
				list.set(i,max);
		}
	}
	/**
	 * @param list The list which contains Missing Values
	 * Fills the missing values with the class which occurs the most.
	 */
	public void handleMissingDouble(ArrayList<Double> list){
		TreeSet<Double> unique = new TreeSet<Double>();
		for(int i=0;i<list.size();i++)
			unique.add(list.get(i));
		
		HashMap<Double, Integer> freqTable = new HashMap<Double,Integer>();
		
		for(Double temp : unique){
			freqTable.put(temp, freqTable.get(temp)+1);
		}
		double max = 0;
		for(Double temp : unique){
			if(freqTable.get(temp)>max)
				max = temp;
		}
		
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i)==-1.0)
				list.set(i,max);
		}
	}
}
