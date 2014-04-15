package twitter_index;

import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;

public class Indexer 
{
	private IndexWriter indexWriter = null;
	
    /** Creates a new instance of Indexer */
    public Indexer() 
    {
    
    }
 
    
    
    public IndexWriter getIndexWriter(boolean create) throws IOException 
    {
        if (indexWriter == null) 
        {
            indexWriter = new IndexWriter("index-directory", new StandardAnalyzer(), create);
        }
        return indexWriter;
    }    
   
    public void closeIndexWriter() throws IOException 
    {
        if (indexWriter != null) 
        {
            indexWriter.close();
        }
   }
    
    public void indexTweet(Tweet tweet) throws IOException 
    {

        System.out.println("Indexing tweet: " + tweet);
        IndexWriter writer = getIndexWriter(false);
        Document doc = new Document();
        doc.add(new Field("id", tweet.getId(), Field.Store.YES, Field.Index.NO));
        doc.add(new Field("dateTime", tweet.getDateTime(), Field.Store.YES, Field.Index.TOKENIZED));
        doc.add(new Field("screenName", tweet.getscreenName(), Field.Store.YES, Field.Index.UN_TOKENIZED));
        doc.add(new Field("description", tweet.getDescription(), Field.Store.YES, Field.Index.TOKENIZED));
        String fullSearchableText = tweet.getscreenName() + " " + tweet.getDateTime() + " " + tweet.getDescription();
        doc.add(new Field("content", fullSearchableText, Field.Store.NO, Field.Index.TOKENIZED));
        writer.addDocument(doc);
    }   
    
    public void rebuildIndexes() throws IOException 
    {
          //
          // Erase existing index
          //
          getIndexWriter(true);
          //
          // Index all Accommodation entries
          TweetDatabase.constructTweetsList();
          
          for(Tweet tweet : TweetDatabase.TweetsList) 
          {
              indexTweet(tweet);              
          }
          //
          // Don't forget to close the index writer when done
          //
          closeIndexWriter();
     }    
    
  
    
    
}
