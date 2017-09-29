package ie.gmit.java2.parser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class JsoupParser extends UrlParser {
   
    @Override
    public boolean availableSource() {
        //If the url is not set the return false;
       if(this.getUrl()==null) return false;
        try {
            //Get connection to the url
            Connection.Response conn = this.openConnection();
            //Get the content type
            this.setContentType(conn.contentType());
            //if the response was not 200 then return true;
            if(200==conn.statusCode()) return true;
            //If the response code was not 200
            this.setConnectionError("The server responded: "+conn.statusCode()+","+conn.statusMessage()+" (read more at: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)");
            
        } catch (IOException ex) {
            ie.gmit.java2.TextAnalyser.LOG.debug(ex.getMessage(), ex);
            //If an exception was thrown
            this.setConnectionError("The url cannot be resolved.");
        }
        //it is not available
        return false;
    }

    @Override
    public boolean bufferContent(String separatorRegex,boolean filterLines) {
        try{
            //Get connection for url
            Connection.Response conn = this.openConnection();
            //Parse the result
            Document doc=conn.parse();
            //Add to line break to the br
            doc.select("br").append("\\n");
            //Add line break to the paragraph
            doc.select("p").prepend("\\n");
            //Replace escaped line breaks to normal line break
            String s = doc.html().replaceAll("\\\\n", "\n");
            //Clean up the document and convert to text
            String Text= Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
        	//Remove multiple line breaks and preserve one
            Text=Text.replaceAll("(?m)(^ *| +(?= |$))", "").replaceAll("(?m)^$([\r\n]+?)(^$[\r\n]+?^)+", "$1");
            //Replace html space to space
            Text=Text.replaceAll("&nbsp", " ");
            //Convert string to buffered reader
            InputStream is = new ByteArrayInputStream(Text.getBytes());
            //Get the size of our content
            this.setSize(is.available());
            //Create the buffer from the stream
            this.setBr(new BufferedReader(new InputStreamReader(is)));
            //Parse
            this.storeLines(this.getBr(), separatorRegex,filterLines);
            return true;     
        }catch (IOException ex) {
                //Do nothing the file will be found anyways. We check this before.
                ie.gmit.java2.TextAnalyser.LOG.debug(ex.getMessage(), ex);
        }
        return false;
    }
    
    /**
     * Opens a new connection to the set url
     * @return Connection.Response - The opened connection
     * @throws IOException 
     */
    private Connection.Response openConnection() throws IOException{
    	//Open JSOUP connection
    	Connection.Response conn = Jsoup.connect(this.getUrl().toString())
    			  			  			.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36")
    			  			  			.execute();
        return conn;
    }
    

    
}
