import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AcronymExtraction 
{

	
	ProcessFile myProc = new ProcessFile();
	public List<String> records = myProc.firstList();
	
	public int window = 3;
	public String path = "StopWordsList.txt";
	public Map<String, Acronym> acronyms = new HashMap<>();
	public Set<String> stopWordsList = new HashSet<>();
	private BufferedReader br;
	
	int totalTokens = 0;

	//Pattern pattern = Pattern.compile("\\w*-?[A-Z]-?\\w*-?[A-Z]-?\\w*|\\w+-?[A-Z]+-?\\w*-?|\\(\\w*-?\\w*\\)");
	//Pattern pattern = Pattern.compile("\\w*[A-Z]\\w*[A-Z]\\w*|\\w+[A-Z]+\\w*");
	Pattern pattern = Pattern.compile("^[A-Z][a-z]?-?[A-Z]|^[a-z]+-?[A-Z]-?[A-Z]");
	
	public AcronymExtraction(int window, String path)
	{
		this.window = window;
		this.path = path;
	}

	public void initializeStructure()
	{
		
		try {
			br = new BufferedReader(new FileReader( path ));
			String line;
			while ((line = br.readLine())!=null)
			{
				line = line.replaceAll("[^A-Za-z0-9-/ ]", "");
				stopWordsList.add(line.trim());
			}

		} catch ( IOException e )
		{
			e.printStackTrace();
		}
	
		
		for (int i = 0; i < records.size(); i++)
		{
			searchRecord(i);
		}		
	}

	public void searchRecord(int i)
	{
		String[] words = records.get(i).replaceAll("/", " ").replaceAll("[^A-Za-z0-9-/ ]", "").split( "\\s+" );
		totalTokens += words.length;
		for (int k = 0; k < words.length; k++)
		{		
			Matcher matcher = pattern.matcher(words[k]);
			boolean foundMatch = matcher.find();
			if(foundMatch)
			{
				if(acronyms.containsKey( words[k] )){
					acronyms.get(words[k]).occurrences++;
				} 
				else
				{
					Acronym currentAcronym = new Acronym();
					currentAcronym.acronym = words[k];
					currentAcronym.occurrences = 1;
					acronyms.put( words[k], currentAcronym );
				}
				searchPossibleExpantion(k, words, true);
				searchPossibleExpantion(k, words, false);
			}
		}
	}

	//number normalization
	public String acronymNormalization( String acr )
	{
		String word = acr;
		String[] number = {"0","1","2","3","4","5","6","7","8","9"};
		String[] text  = {"Z", "O", "T", "T", "F", "F", "S", "S", "E", "N"};
		for( int w = 0; w < number.length; w++ ){
			word= word.replace(number[w], text[w]);
		}
		//word = word.replaceAll("\\p{Lower}", "");
		word = word.replaceAll("-", "");
		return word;
	}

	public void searchPossibleExpantion(int acronymPosition, String[] words, boolean directionLeft)
	{		
		String word = acronymNormalization(words[acronymPosition]);
		String[] acronym = word.split("");
		for(int l = -1; l < window + 1; l++){
			String expansion="";
			double score = 0;

			int offset = l + words[acronymPosition].length();

			int loopStart;
			int loopEnd;

			if(directionLeft)
			{	
				loopStart = (acronymPosition-offset>=0 ? acronymPosition-offset : 0);
				loopEnd = acronymPosition;
			}
			else
			{
				loopStart = acronymPosition + 1;
				loopEnd = (acronymPosition+offset+1<=words.length ? acronymPosition+offset+1 : words.length);
			}

			for (int i = 0; i < acronym.length; i++)
			{

				String regexFormation = "^["+acronym[i].toLowerCase()+acronym[i].toUpperCase()+"]";
				Pattern wordPattern = Pattern.compile(regexFormation);

				for (int j = loopStart; j < loopEnd; j++)
				{

					Matcher matcher = wordPattern.matcher(words[j]);
					boolean foundMatch = matcher.find();

					if(foundMatch && words[j].length()>1)
					{
						if(!stopWordsList.contains(words[j].trim()))
						{
							expansion += words[j].toLowerCase()+" ";
							score += 0.5 + 2.0/Math.abs(acronymPosition-j);

							loopStart = j + 1;
							j = loopEnd + 1;
						}

					}
				}
			}

			if(expansion!="")
			{
				Expansion currentExpantion = new Expansion();
				currentExpantion.expansion = expansion;
				currentExpantion.score = score;
				acronyms.get(words[acronymPosition]).possibleExpansions.add(currentExpantion);	
			}	
		}
	}

	public void expansion()
	{		
		for (Map.Entry<String, Acronym> entry : acronyms.entrySet())
		{
			double max=0;
			String expantion="";
			for (Expansion exp : entry.getValue().possibleExpansions)
			{
				if(exp.score>max)
				{
					max = exp.score;
					expantion = exp.expansion;					
				}	
			}
			entry.getValue().acronymExpantion = expantion;
			entry.getValue().acronymScore = max;
		}
	}

	public void print() {
		//System.out.println(totalTokens);
		for (Map.Entry<String, Acronym> entry : acronyms.entrySet())
		{
			String acronym = entry.getValue().acronym;
			String acronymExpantion = entry.getValue().acronymExpantion;
			int acronymCount = entry.getValue().occurrences;
			double score = entry.getValue().acronymScore;
			//System.out.println(acronym+":	"+acronymExpantion+"	"+score);
			//we can print only expansions that we are more confident they are correct
			if(score>=4 && acronym.length()<=15)
			{
				//System.out.format("%60s%10s%16f\n", acronymExpantion, acronym, score);
				System.out.println(acronym+":	"+acronymExpantion+"	"+Math.round(score));
				//System.out.println(Math.round(score));
			}
			if(entry.getKey().equals(""))
			{
				for (Expansion exp : entry.getValue().possibleExpansions)
				{
					System.out.println("	"+exp.expansion+"	"+exp.score);
				}
			}
		}
	}

}
