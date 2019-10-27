package utils;

import java.io.IOException;
public class FileManager {
	public static Score readScoreFromFile() {
		java.io.ObjectInputStream ois=null;
		try {
			java.io.InputStream is = new java.io.FileInputStream( "score.dat" );
		    ois = new java.io.ObjectInputStream( is );
			Score s = (Score) ois.readObject();
			try {
				ois.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return s;
			
		} catch (Exception e) {		
		}
		return new Score(0);
	}
	public static void saveScoreToFile(Score s){
		try{
			java.io.OutputStream os = new java.io.FileOutputStream( "score.dat" );
			java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream( os );
			oos.writeObject( s );
			oos.close();
		}catch(Exception e){
			System.out.println("error: saveScoreToFile ");
		}
		
	} 
	public static void main(String []args){
		Score s = new Score(0);
		saveScoreToFile(s);
	}
	
}