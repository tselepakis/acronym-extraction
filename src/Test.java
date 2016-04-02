

public class Test {

	public static void main(String[] args) {
		
		AcronymExtraction mytest = new AcronymExtraction( 3, "StopWordsList.txt");
		mytest.initializeStructure();
		mytest.expansion();
		mytest.print();

	}
}
