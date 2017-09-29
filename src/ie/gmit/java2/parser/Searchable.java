package ie.gmit.java2.parser;

public interface Searchable {
    /**
     * Get the parsed lines
     * @return LinesMap
     */
    public LinesMap getLines();
    
    /**
     * Set the lines     * 
     * @param lines - Type of LinesMap
     */
    public void setLines(LinesMap lines);
    
    /**
     *Check if the parsed text contains a given string
     * @param String
     * @return boolean - true or false
     */
    public boolean contains(String String);
    
    /**
     * Get the size of words
     * @return int[] - Each line is a new index
     */
    public int[] count();
    
    /**
     * Get number of words in line by index
     * @param index - int
     * @return int - number of words
     */
    public int getNumberOfWordsInLine(int index);
    
    /**
     * Check how many times a given string occurs
     * @param String - A word
     * @return int - The number of occurrences
     */ 
    public int countOccurrences(String String);
    
    /**
     * Get the first index of a string
     * @param String - A word
     * @return int[] - Index 0 is the line. Index 1 is the position in the line
     */
    public int[] getFirstIndex(String String);
    
    /**
     * Get the last index of a string
     * @param String - A word
     * @return int[] - Index 0 is the line. Index 1 is the position in the line
     */
    public int[] getLastIndex(String String);
    
    /**
     * Get all index of a string
     * @param String - A word
     * @return int[][] - The first dimension are the lines and the second is the position in the line.
     */
    public int[][] getAllIndices(String String);
    
    /**
     * Delete the first occurrence of string
     * @param String - A word
     */ 
    public void delete(String String);
    
    /**
     * Delete content at a given index
     * @param index - int[]: Index 0 is the line. Index 1 is the position in the line
     */ 
    public void delete(int[] index);
    
    /**
     * Replace a given word to another one.
     * Modifiers can be used if the first,last or all of the occurrence of the word should be deleted
     * @param From - String
     * @param To - String
     * @param First - boolean
     * @param Last - boolean
     * @param All  - boolean
     */
    public void replace(String From,String To,boolean First,boolean Last,boolean All);
}
