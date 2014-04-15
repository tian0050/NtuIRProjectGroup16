package twitter_index;

import java.util.Iterator;



import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.IndexSearcher;


public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

      try {
    	  	// build a lucene index
    	  	System.out.println("rebuildIndexes");
    	  	Indexer  indexer = new Indexer();
    	  	indexer.rebuildIndexes();
    	  	System.out.println("rebuildIndexes done");

	        // perform search on "Notre Dame museum"
	        // and retrieve the result
	        System.out.println("performSearch");
	        SearchEngine instance = new SearchEngine();
	        Hits hits = instance.performSearch("Moyes");
	
	        System.out.println("Results found: " + hits.length());
	        Iterator<Hit> iter =  hits.iterator();
	        while(iter.hasNext()){
	            Hit hit = iter.next();
	            Document doc = hit.getDocument();
	            System.out.println(doc.get("screenName")
	                               + " " + doc.get("dateTime")
	                               + " " + doc.get("description")
	                               + " (" + hit.getScore() + ")");

        }
        System.out.println("performSearch done");
      } catch (Exception e) {
        System.out.println("Exception caught.\n");
      }
    }
    
}
