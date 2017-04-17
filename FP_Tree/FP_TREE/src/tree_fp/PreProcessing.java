package tree_fp;

import java.io.*;
import java.util.*;



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

		br.close();
	}
	public void discPregnancy(ArrayList<Integer> pregnancy){

	}
	public void discPlasma(ArrayList<Integer> plasma){

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
}
