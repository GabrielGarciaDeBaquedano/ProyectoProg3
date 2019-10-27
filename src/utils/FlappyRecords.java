package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;


public class FlappyRecords{
	
	
		
	public static void volcarArrayListAFichero(String AllRecord, ArrayList<Record> aRecords){
		try {
			FileOutputStream fos = new FileOutputStream(AllRecord);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			
			for(Record e : aRecords)
				oos.writeObject(e);
			
			oos.writeObject(null);
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<Record> volcarFicheroAArrayList(String AllRecords){
		ArrayList<Record> aRecords =  new ArrayList<Record>();
		
		try {
			
			FileInputStream fis = new FileInputStream(AllRecords);
			ObjectInputStream ois =  new ObjectInputStream(fis);
			Record e = (Record) ois.readObject();
			while(e!=null){
				aRecords.add(e);
				e = (Record) ois.readObject();
			}
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return aRecords;
	}
}
