
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessFile {

	public ArrayList<String> firstList () {
	//public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br;
		String line = "";
		String description = "";
		RecordParser rp = new RecordParser();
		rp.parser();
		
		ArrayList<String> firstList = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader("newFile.txt"));
			int count = 2;
			
			try {
				while (line != null) {
					while ((line = br.readLine()) != null) {
						// System.out.println(line);
						//description = description + line;
						if (line.contains(" /WW ")) {
							count = 1;
							break;
						} else {
							description += line + " \n ";
						}
						//

					}
					if (description.equalsIgnoreCase(null)) {
						break;
					}
					firstList.add(description);
					description = "";
				}
				 //System.out.println(firstList.get(8));
			  // return firstList;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return firstList;
	}

}
