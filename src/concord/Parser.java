package concord;

import java.util.*;
import java.io.*;
/*
 * Toss in your author tags as you update classes. Gracias. --W
 * @author Will Forrest
 */
public class Parser {

	//create the concordance
	//query created concordances

	public ArrayList<Word> wordlist;
	public int lineNumber = 0;
	String fileName = "";
    /**
     * Prepare a concordance for the given file.
     *
     * @param fileName
     * @throws java.io.FileNotFoundException
     */
    public void Parser(String fileName) throws FileNotFoundException {    	
    	this.fileName = fileName;
    	wordList = new ArrayList<Word>();
        System.out.println("created arrayList");
        inputWords(fileName);
        Collections.sort(wordList); //sort the words by frequency
        //outputWords();
    }

    public void inputWords(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(fileName));
        //uses other punctuation to start a new word
        in.useDelimiter("[\\s\"_;.,:!?()-]");

        
        int quit = 0;
        String tempWord, tempWord2;

        bookStart:
        while (in.hasNext()) {
            tempWord = in.next().toLowerCase();
            if ("***".compareTo(tempWord) == 0) {
                quit += 1;
            }
            if (quit >= 2) {
                break bookStart;
            }
        }
	parse:
        while (in.hasNextLine()) {
            ++lineNumber;
            tempWord = in.nextLine();
            Scanner in2 = new Scanner(tempWord);
            in2.useDelimiter("[\\s\"_;.,:!?()-]");
            while (in2.hasNext()) {
            	tempWord2 = in2.next().toLowerCase();
                if ("***".compareTo(tempWord2) == 0) {
                    break parse;
                }
                Word word = new Word(tempWord2);
                
                int index = wordList.indexOf(word);
                if (index == -1) {
                    wordList.add(word);
                    word.addLine(lineNumber);
                } else {
                    wordList.get(index).increment();
                    wordList.get(index).addLine(lineNumber);
                }
            }

        }

    }

    /**
     * output the array list of words with their count
     */
    public void outputWords() {
        for (int i = 0; i < wordList.size(); i++) {
            if(wordList.get(i).toString().equals("")){
                
            }
            else{
            System.out.println("\"" + wordList.get(i) + "\" appears " 
                    + wordList.get(i).getFrequency() + " times");
            }
        }
    }
    
    public void outputLineNumbers(){
        for(int i = 0; i < wordList.size(); i++){
            if(wordList.get(i).toString().equals("")){
                
            }
            else{
            System.out.print("\"" + wordList.get(i));
            System.out.print("\" appears on lines ");
            wordList.get(i).getLines();
            System.out.println("");
            }
        }
    }
    
    public int getWordCount(String newWord){
        newWord = newWord.toLowerCase();
        for(int i = 0; i < wordList.size(); i++){
            if(newWord.compareTo(wordList.get(i).toString()) == 0){
                return wordList.get(i).getFrequency();
            }
           
        }
        return 0;
    }
    
    public void getLines(String newWord){
        newWord = newWord.toLowerCase();
        for(int i = 0; i < wordList.size(); i++){
            if(newWord.compareTo(wordList.get(i).toString()) == 0){
                wordList.get(i).getLines();
            }
        }
    }
    
    public boolean adjacency(/*int line, */String targetPhrase) throws FileNotFoundException {
        targetPhrase = targetPhrase.toLowerCase();
        Scanner strngScan = new Scanner(targetPhrase);
        String tempWord1 = "";
        String firstWord = "";
        String secondWord = "";
        String thirdWord = "";
        String compareWord = "";
        int wordCount = 0;
        int adjCount = 0;
        int firstOccurrenceCount = 0;
        int secondOccurrenceCount = 0;
        int thirdOccurrenceCount = 0;
        //Parses the phrase
        strngScan.useDelimiter("[\\s\"_;.,:!?()-]");
        while (strngScan.hasNext()) {
            //++wordCount;
            tempWord1 = strngScan.next();
            if (tempWord1.equals("") == false) {
                ++wordCount;

                if (wordCount == 1) {
                    firstWord = tempWord1;

                } else if (wordCount == 2) {
                    secondWord = tempWord1;

                } else if (wordCount == 3) {
                    thirdWord = tempWord1;

                }
            }

        }

        for (int j = 0; j <= wordCount; j++) {

            for (int i = 0; i < wordList.size(); i++) {
                String word = wordList.get(i).getWord();
                if (j == 1) {
                    if (firstWord.compareTo(word) == 0) {
                        firstOccurrenceCount += 1;
                    }
                } else if (j == 2) {
                    if (secondWord.compareTo(word) == 0) {
                        secondOccurrenceCount += 1;
                    }
                } else if (j == 3) {
                    if (thirdWord.compareTo(word) == 0) {
                        thirdOccurrenceCount += 1;
                    }
                }

            }
        }

        if (wordCount == 2) {
            thirdOccurrenceCount = 4;
        }

        if (firstOccurrenceCount < 1) {
            //System.out.println(targetPhrase + " does not appear in the text");
            return false;
        } else if (secondOccurrenceCount < 1) {
            //System.out.println(targetPhrase + " does not appear in the text");
            return false;
        } else if (thirdOccurrenceCount < 1) {
            //System.out.println(targetPhrase + " does not appear in the text");
            return false;
        }

        Scanner in4 = new Scanner(new File(this.fileName));
        //uses other punctuation to start a new word
        in4.useDelimiter("[\\s\"_;.,:!?()-]");

        //int lineNumber = 0;
        int quit = 0;
        String tempWord;

        bookStart:
        while (in4.hasNext()) {
            tempWord = in4.next().toLowerCase();
            if ("***".compareTo(tempWord) == 0) {
                //System.out.println("true");
                quit += 1;
            }
            if (quit >= 2) {
                break bookStart;
            }
        }

        while (in4.hasNextLine()) {
            ++lineNumber;
            tempWord = in4.nextLine().toLowerCase();
            Scanner in2 = new Scanner(tempWord);
            in2.useDelimiter("[\\s\"_;.,:!?()-]");

            while (in2.hasNext()) {
                compareWord = in2.next().toLowerCase();
                //System.out.println("The current word is: " + compareWord);
                //System.out.println(compareWord.equals(""));
                if (compareWord.equals("") == false) {
                    //System.out.println("we are comparing with word: " + compareWord);
                    //System.out.println("the string was blank");
                    if (wordCount == 3) {
                        if (adjCount == 0) {
                            if (compareWord.compareTo(firstWord) == 0) {
                                adjCount += 1;
                                System.out.println(adjCount);
                            }
                        } else if (adjCount == 1) {
                            if (compareWord.compareTo(secondWord) == 0) {
                                adjCount += 1;
                                System.out.println(adjCount);
                            } else {
                                adjCount = 0;
                            }
                        } else if (adjCount == 2) {
                            if (compareWord.compareTo(thirdWord) == 0) {
                                adjCount += 1;
                                System.out.println(adjCount);
                            } else {
                                adjCount = 0;
                            }
                        }
                    } else if (wordCount == 2) {
                        if (adjCount == 0) {
                            if (compareWord.compareTo(firstWord) == 0) {
                                adjCount += 1;
                                System.out.println(adjCount);
                            }
                        } else if (adjCount == 1) {
                            if (compareWord.compareTo(secondWord) == 0) {
                                adjCount += 1;
                                System.out.println(adjCount);
                            } else {
                                adjCount = 0;
                            }
                        }
                    }

                }

            }

        }

        if (adjCount == wordCount) {
            System.out.println(targetPhrase + " does appear in the text");
            return true;
        }
        return false;
    }
    
    /*
     if the word does not occur in text return false
     if the word does not occur on the line return false
     if target does not occur in the text return false
     if distance is not greater than 0 return false
     then check if target is in the distance if it is return true; 
     else return false
     i = instance - n
     int min = instance - n
     int max = instance + n
     if max > size; max = size;
     if min < 0; min = 0;
     for i = instance - n; i < instance + n; i ++
        
     distance is +-
    
     */
    public boolean wordOccurrence(String baseWord, String targetWord,
            int baseWordLine, int lineRange) {
        baseWord = baseWord.toLowerCase();
        targetWord = targetWord.toLowerCase();
        int minLine = baseWordLine - lineRange;
        int maxLine = baseWordLine + lineRange;
        int baseOccurrenceCount = 0;
        int targetOccurrenceCount = 0;
        int baseWordIndex = -1;
        int targetWordIndex = -1;
        //checks if the given word occurs in the text
        for (int i = 0; i < wordList.size(); i++) {
            String word = wordList.get(i).getWord();
            if (baseWord.compareTo(word) == 0) {
                System.out.println(baseWord + " appears in the text");
                baseWordIndex = i;
                baseOccurrenceCount += 1;
            }
        }
        //checks if the given word is in text
        if (baseOccurrenceCount < 1) {
            System.out.println(baseWord + " does not occur in the text.");
            return false;
        }

        //checks if the given word occurs on the given line
        if (wordList.get(baseWordIndex).occursOnLine(baseWordLine) == false) {
            System.out.println(baseWord + " does not occur on the line "
                    + baseWordLine);
            return false;
        }
        //checks if the target word is in the text
        for (int i = 0; i < wordList.size(); i++) {
            String word = wordList.get(i).getWord();
            if (targetWord.compareTo(word) == 0) {
                System.out.println(targetWord + " appears in the text");
                targetWordIndex = i;
                targetOccurrenceCount += 1;
            }
        }

        if (targetOccurrenceCount < 1) {
            System.out.println(targetWord + " does not occur in the text.");
            return false;
        }

        /* checks to see if the base word line given is a valid line number
         for the overall text
         should not need this because it already checks if the word is on the
         given line number. If the given line number is not in the line array of
         the given word then the method will return false.
         if(baseWordLine > lineNumber){
         System.out.println("There are not that many lines in the text");
         }*/
        //if the line distance is not positive then it will be invalid
        if (lineRange < 0) {
            System.out.println("Your line distance is not valid");
            return false;
        }

        if (maxLine > lineNumber) {
            maxLine = lineNumber;
        }
        if (minLine < 0) {
            minLine = 0;
        }
        //checks if the target word can be found in the specified line range
        //of the base word
        int num = 0;
        for (int i = minLine; i <= maxLine; i++) {
            if (wordList.get(targetWordIndex).occursOnLine(i) == false) {
                num += 1;
            } else {
                System.out.println(targetWord + " does occur within plus or "
                        + "minus " + lineRange + " lines of " + baseWord);
                return true;

            }
        }

        if (num == minLine + maxLine + 1) {
            System.out.println(targetWord + " does not occur within given line"
                    + " range of " + baseWord);
        }

        return false;
    }
    
    public void parseToFile(File fileDest){
    	System.out.println("are we touching this?");
    	for(int i = 0; i < this.wordList.size(); i++){
    		Word curr = wordList.get(i);
    		System.out.println(curr);
    	}
    }
    
    public void fileToParse(File fileDest, File fileToParse){
    	
    }
}
