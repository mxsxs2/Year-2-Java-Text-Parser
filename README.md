## Introduction
The application is written for Java Programming module in year 2 of Software Development(2016) course at Galway Mayo Institute of Technology, Galway Campus. The application is a text parser.
### Features of the application:
	* Reading a file
	* Reading a document from url 
	* Reading a url with JSOUP
	* Show different statistics of the parsed file/url
	* Search in the content by
		* Contains the word
		* Check occurrences
		* Find first index
		* Find last Index
		* Find all indexes
	* Modify the content
		* Delete all occurrences of a word
		* Delete a word at a given index
		* Replace a word with another
			* All occurrences of the word
			* First occurrence of the word
			* Last occurrence of the word
	* Save the content into a file

### Extra features added to the required ones:
	* Graphical User Interface
	* JSOUP url parsing
	* Timing the loading time of file opening, searching and file saving
	* Modular searching: You can choose any or all the searching methods
	* Replacing a word at all occasion or just the first or just the last or first and last positions
	* Saving the modified content into a new file

### Extra libraries used:
	* For debugging I chose to use sl4j with logback as I wanted to save every exception into a log file in a nicer format. It is easier to use than java.uitl.Logger (For me at least).
	* I used the JSOUP for parsing a URL.
	* Apache's commons io library to determine the extension of the file before saving it.



### Description
I choose to use SWING to build a Graphical User Interface rather than a command line. I decided to do this because an application with GUI is easier to use than a command line application.


When the user chooses an option and presses "Parse..." within a thread, the application will try to open and parse the source. Meanwhile it will show a "Please Wait" message.
I am using threads when: parsing the source, performing a search and when I write out into a file. 
The reason why I chose to use threads, because this way the GUI is not going to freeze out while the other processes are running, also the processes should never run in the same thread as the GUI, allowing the user to run them simultaneously. 



I have created a main Abstract Parser class for parsing files and urls. For each Parsing operation, I have classes what are extending the main Parser class.
Also, the Parser class implements Parseable and Cloneable interfaces.


To read a file, the application is using the newBufferedReader method of java.nio.file.Files. I chose this method over the regular file reader as this method simpler to use.



To read a document form a simple url, I created a HttpURLConnection, followed by "emulating" a real user agent and finally wrapped the result into a BufferedReader. 

URLs that works on both url parser:
	* http://koronakiraly.hu
	* http://koronakiraly.hu/warnp.txt
	* http://www.gmit.ie

http://google.ie works only with JSOUP

Both cases the protocol is not mandatory. The default protocol is http.

From the parsed content the html tags are not going to be filtered out unless you chose the JSOUP option at the begging. The normal url opener is recommended for documents.

To read a url with JSOUP I simply extended the previously mentioned URL Reader and overrode the relevant methods with the JSOUP version of them.
With JSOUP after reading the url I only used the body section of the result. 
I cleaned it from html tags then returned as plain text, reserving the line breaks.





When the program received any of the above as a BufferedReader, it will stream through each line, clean them from every character what is not a-z, A-Z, 0-9, space or '.
I leave ' because it can make totally different meaning to a word if I remove it.
After cleaning, I split the lines at every white space, then created an ArrayList from each line and finally added them to my LinesMap.

My LinesMap is extending a HashMap.
To use a HashMap containing many ArrayList, is a better alternative to using a String Array, as it is easier to maintain and faster as well.
I implemented a deepClone() method into the LinesMap as I did not want all the other methods to access the original content. 
 
As a result, when I perform a search in the content of the HashMap , I can tell exactly what line the word is on and its position on the line.
Due to this, all my methods what returns position of a string, will return an int array with the line number and the index of the word in that line.
The getAllIndeces method is an exception of this, because this function will return a two dimensional int array. 

Another advantage of the HashMap I can use streams. This can increase the speed of any operation on the content and allows me to create a shorter code over normal loops.



TextPromt.java is not written by me. The credit belongs to a user on Stackoverflow as I found it when I was looking for a solution for field spacer.










