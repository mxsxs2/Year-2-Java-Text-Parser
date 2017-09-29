/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.gmit.java2.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;

/**
 *
 * @author user
 */
public class UrlParser extends Parser{
    //Url
    private URL url;
    //variable for the buffered reader
    private BufferedReader br;
    //Filse size
    private int size=0;
    //Connection error
    private String connectionError="";
    //Content Type
    private String contentType="Not determinable";
    
    //They has to protected, because i use them as inherited methods,
    //but i don't want to use them outside of the class or the child class
    protected URL getUrl() {
	return url;
    }

    protected void setUrl(URL url) {
	this.url = url;
    }

    protected BufferedReader getBr() {
	return br;
    }

    protected void setBr(BufferedReader br) {
	this.br = br;
    }

    protected int getSize() {
	return size;
    }

    protected void setSize(int size) {
	this.size = size;
    }

	
    protected String getConnectionError() {
	return connectionError;
    }

    protected void setConnectionError(String connectionError) {
	this.connectionError = connectionError;
    }
    
    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setSource(Object o) {
        //Check if object is url or string
        if(o instanceof URL){
            //Set the url
        	this.setUrl((URL)o);
        }else{
            try {
                //Set the url form sting
            	this.setUrl(new URL(this.checkHttpProtocol((String)o)));
            } catch (MalformedURLException ex) {
               //Do nothing as available source will return false anyways.
               ie.gmit.java2.TextAnalyser.LOG.debug(ex.getMessage(), ex);
            }
        }
    }

    
    public String checkHttpProtocol(String url){
    	if(!url.contains("http://"))
    		url="http://"+url;
    	return url;
    }
    
    @Override
    public String getErrorMessage(){
        //Return the erro message
        return this.connectionError;
    }
    
    @Override
    public boolean availableSource() {
        //If the url is not set the return false;
       if(this.url==null) return false;
        try {
            //Get connection for url
            HttpURLConnection conn = this.openConnection("HEAD");
           //If the http request code is 200 the the url is readable. otherwise we leave it.
           
            //if the response was not 200 then return true;
            if(200==conn.getResponseCode()) return true;
            
            //If the response code was not 200
            this.connectionError="The server responded: "+conn.getResponseCode()+","+conn.getResponseMessage()+" (read more at: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)";
            
        } catch (IOException ex) {
            ie.gmit.java2.TextAnalyser.LOG.debug(ex.getMessage(), ex);
            //If an exception was thrown
            this.connectionError="The url cannot be resolved.";
        }
        //it is not available
        return false;
    }

    @Override
    public String getSourceName() {
        //return the url path
        return this.url.getProtocol() + "://" + 
               this.url.getHost() +
               //Check if port exists if it does then concat
               (this.url.getPort()!=-1 ? ":" +this.url.getPort() : "") +   
               this.url.getPath() +
               //Check if queries exists if it does then concat
               (this.url.getQuery()!=null ? "?"+this.url.getQuery() : "");
    }

    @Override
    public long getSizeofSource() {
        //Return the size
        return this.size;
    }

    @Override
    public boolean bufferContent(String separatorRegex, boolean filterLines) {
        try{
            //Get connection for url
            HttpURLConnection conn = this.openConnection("GET");
            //Get the stream
            InputStream is = conn.getInputStream();
                //Check if the content is gzipped
		if("gzip".equals(conn.getContentEncoding())){
                    //Create a new gzip stream 
                    is = new java.util.zip.GZIPInputStream(is);
		}
            //Create the buffer from the stream
            this.br = new BufferedReader(new InputStreamReader(is));
            //Try to decode the input stream
            //this.decodeInputStream(is, 0);
            //If the BufferedReader is still null then return false
            //if(this.br==null) return false;
            
            //Parse
            this.storeLines(this.br, separatorRegex,filterLines);
            return true;    
        }catch (IOException ex) {
                //Do nothing the file will be found anyways. We check this before.
                ie.gmit.java2.TextAnalyser.LOG.debug(ex.getMessage(), ex);
                
        }
        return false;
    }
    
    @Override
    public String getContentType(){
        return this.contentType;
    }
    
    
    /**
     * Opens a new Http connection to the given url
     * @param request -String. The request method
     * @return HttpURLConnection - The opened connection
     * @throws IOException 
     */
    private HttpURLConnection openConnection(String request) throws IOException{
        //Create the connection
        HttpURLConnection conn= (HttpURLConnection)this.url.openConnection ();
        //Set header so we wont get 403
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");
        //Set the request to head
        conn.setRequestMethod(request);
        //If we are here then we can get the size of the content
        //Pointless to have a separate call for this
        this.size=conn.getInputStream().available();
        //Also get the content type as well
        this.contentType=conn.getContentType();
        
        return conn;
    }
    
    /**
     * Auto detect the encoding of the file
     * @param charsetIndex
     */
    protected void decodeInputStream(InputStream is,int charsetIndex) throws IOException{
    	//Declare the encodings to try
    	String[] enc=this.getEncodings(); 	
    	try{
    		//Create a buffered reader with a given charset
    		BufferedReader br=new BufferedReader(new InputStreamReader(is,Charset.forName(enc[charsetIndex])));
    		//Temporary line holder
    		String line;
    		//Try to read the first line
    		if((line=br.readLine()) != null){
    			//Close the stream
    			br.close();
    			System.out.println(enc[charsetIndex]);
    			//If the line could be read then "rewind" the buffered reader and return it
        		this.br=new BufferedReader(new InputStreamReader(is,Charset.forName(enc[charsetIndex])));
        	}
    	}catch(MalformedInputException ex){
    		//If an exception was thrown (for example because of the wrong charset)
    		//Check if there is any more charset is available for testing
    		if(enc.length>++charsetIndex){
    			//Try to decode again with a different encoding
        		this.decodeInputStream(is,charsetIndex);
        	}
    	}
    }
}
