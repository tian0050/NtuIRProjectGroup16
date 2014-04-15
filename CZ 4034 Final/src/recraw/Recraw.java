package recraw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;
import twitter_crawl.Authenticate;
import twitter_crawl.Timeline_pull;
import twitter_index.Indexer;

public class Recraw extends HttpServlet{
	 public void doPost(HttpServletRequest request,
	            HttpServletResponse response)
	            throws ServletException, IOException
	{
		 PrintWriter out = response.getWriter();
		 show();
		 out.println("<script type=\"text/javascript\">");
		 out.println("alert(\"Recrawling Completed!\");");
		 out.println("</script>");
	}

	public void show() throws IOException {
		
		Authenticate();
		Timeline_pull();
	  	System.out.println("rebuildIndexes");
	  	Indexer  indexer = new Indexer();
	  	indexer.rebuildIndexes();
	  	System.out.println("rebuildIndexes done");
	  	
		
	}

	public void Timeline_pull() {
		OAuth2Token token = getOAuth2Token1();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		String tweetdata = "";
		int count = 0;
		cb.setApplicationOnlyAuthEnabled(true);
		cb.setOAuthConsumerKey("fxDAk7skJvrQ7oH4j6TkJcBMs");
		cb.setOAuthConsumerSecret("4au88j95SSr4ml1uqLdHUEXA4vpEQZMEkkXOZk1aiv7Xa2qCin");
		cb.setOAuth2TokenType(token.getTokenType());
		cb.setOAuth2AccessToken(token.getAccessToken());
		 // gets Twitter instance with default credentials
		TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
		try {
            List<Status> statuses;
            System.out.println("test");
            String user_list = get_timeline_name1();
            System.out.println(user_list);
            StringTokenizer st = new StringTokenizer(user_list);
            while(st.hasMoreElements())
            {
            	String user = st.nextElement().toString();
            	//System.out.println(user);
            	statuses = twitter.getUserTimeline(user); 
            	System.out.println("Showing @" + user + "'s user timeline.");
            	for (Status status : statuses) {
            		System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            		tweetdata = "##"+(status.getCreatedAt().toString())+"$@"+(status.getUser().getScreenName())+"$"+(status.getText())+"##";
            		writetofile1(tweetdata,count);
            }}
            System.out.println("Now printing the total number of records in file");
            number_of_records1();
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
	public static OAuth2Token getOAuth2Token1()
	{
		OAuth2Token token = null;
		ConfigurationBuilder cb;

		cb = new ConfigurationBuilder();
		cb.setApplicationOnlyAuthEnabled(true);

		cb.setOAuthConsumerKey("fxDAk7skJvrQ7oH4j6TkJcBMs").setOAuthConsumerSecret("4au88j95SSr4ml1uqLdHUEXA4vpEQZMEkkXOZk1aiv7Xa2qCin");

		try
		{
			token = new TwitterFactory(cb.build()).getInstance().getOAuth2Token();
		}
		catch (Exception e)
		{
			System.out.println("Could not get OAuth2 token");
			e.printStackTrace();
			System.exit(0);
		}

		return token;
	}
	
	public static void writetofile1(String tweetdata, int count)
	{
		try {
 
			System.out.println(tweetdata);
			String fname = "Crawled_File"+count+".txt";
			String general = "D:\\workspace\\test1\\src\\output\\";
			String filename = general+fname;
			
			File file = new File(filename);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				//System.out.println("File doesnt exist");
				file.createNewFile();
				
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(tweetdata);
			bw.newLine();
			bw.close();
 
			//System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get_timeline_name1()
	{
		BufferedReader br = null;
		String all_timelines = "";
		
		try {
				String sCurrentLine;
				String current_user;
				
 
				br = new BufferedReader(new FileReader("D:\\workspace\\test1\\src\\Files\\search_timeline.txt"));
 
				while ((sCurrentLine = br.readLine()) != null) 
				{
					
					all_timelines = all_timelines + sCurrentLine.trim() +" ";
				}
				System.out.println("file read");
				return all_timelines;
			} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)br.close();
			} catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public static void number_of_records1()
	{
		BufferedReader br = null;
		String sCurrentLine = ""; 
		try
		{
			br = new BufferedReader(new FileReader("D:\\workspace\\test1\\src\\output\\Crawled_File0.txt"));
			int count = 0; 
			while ((sCurrentLine = br.readLine()) != null) 
			{
				count = count + 1;
			}
			System.out.println("file read");
			System.out.println("The total number of records = "+count);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)br.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		
    }
	
	

	public void Authenticate() {
		OAuth2Token token = getOAuth2Token();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		String tweetdata = "";
		int count = 0;
		cb.setApplicationOnlyAuthEnabled(true);
		cb.setOAuthConsumerKey("fxDAk7skJvrQ7oH4j6TkJcBMs");
		cb.setOAuthConsumerSecret("4au88j95SSr4ml1uqLdHUEXA4vpEQZMEkkXOZk1aiv7Xa2qCin");
		cb.setOAuth2TokenType(token.getTokenType());
		cb.setOAuth2AccessToken(token.getAccessToken());

		//Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();


		//	Now do a simple search to show that the tokens work
		try
		{
			Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search");
			RateLimitStatus searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");

			System.out.printf("You have %d calls remaining out of %d, Limit resets in %d seconds\n",
							  searchTweetsRateLimit.getRemaining(),
							  searchTweetsRateLimit.getLimit(),
							  searchTweetsRateLimit.getSecondsUntilReset());

            			
			//String searchquery = get_searchterm(count);
			String query_list = get_query_word();
            System.out.println(query_list);
            StringTokenizer st = new StringTokenizer(query_list);
            while(st.hasMoreElements())
			{
            	Query q = new Query(st.nextElement().toString());	// Search for tweets that contain these two words
            	//System.out.println("test");
            	q.setCount(100);							// Let's get all the tweets we can in one call
						// Get all tweets
            	//q.setLang("en");						// English language tweets, please

            	QueryResult r = twitter.search(q);			// Make the call
            	//System.out.println("Test 2");
            	for (Status s: r.getTweets())				// Loop through all the tweets...
            	{
            		System.out.println("@"+s.getUser().getScreenName() +"-"+s.getText());
					tweetdata = "##"+s.getCreatedAt().toString()+"$@"+s.getUser().getScreenName()+"$"+s.getText()+"##";
            		writetofile(tweetdata,count);
		        }
			}	
            System.out.println("Now printing the total number of records in file");
            number_of_records();
		}
		catch (Exception e)
		{
			System.out.println("That didn't work well...wonder why?");

			e.printStackTrace();

		}
	}
		
	public static OAuth2Token getOAuth2Token()
	{
		OAuth2Token token = null;
		ConfigurationBuilder cb;

		cb = new ConfigurationBuilder();
		cb.setApplicationOnlyAuthEnabled(true);

		cb.setOAuthConsumerKey("fxDAk7skJvrQ7oH4j6TkJcBMs").setOAuthConsumerSecret("4au88j95SSr4ml1uqLdHUEXA4vpEQZMEkkXOZk1aiv7Xa2qCin");

		try
		{
			token = new TwitterFactory(cb.build()).getInstance().getOAuth2Token();
		}
		catch (Exception e)
		{
			System.out.println("Could not get OAuth2 token");
			e.printStackTrace();
			System.exit(0);
		}

		return token;
	}
	
	public static void writetofile(String tweetdata,int count)
	{
		try 
		{
			System.out.println(tweetdata);
			String fname = "Crawled_File_Search"+count+".txt";
			String general = "D:\\workspace\\test1\\src\\output\\";
			String filename = general+fname;
			
			File file = new File(filename);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				//System.out.println("File doesnt exist");
				file.createNewFile();
				
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(tweetdata);
			bw.newLine();
			bw.close();
 
			//System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		
	public static String get_query_word()
	{
		BufferedReader br = null;
		String all_queries = "";
		
		try {
				String sCurrentLine;
				
				
 
				br = new BufferedReader(new FileReader("D:\\workspace\\test1\\src\\Files\\search_timeline.txt"));
 
				while ((sCurrentLine = br.readLine()) != null) 
				{
					
					all_queries = all_queries + sCurrentLine.trim() +" ";
				}
				System.out.println("file read");
				return all_queries;
			} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)br.close();
			} catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public static void number_of_records()
	{
		BufferedReader br = null;
		String sCurrentLine = ""; 
		try
		{
			br = new BufferedReader(new FileReader("src/output/Crawled_File_Search0.txt"));
			int count = 0; 
			while ((sCurrentLine = br.readLine()) != null) 
			{
				count = count + 1;
			}
			System.out.println("file read");
			System.out.println("The total number of records = "+count);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)br.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		
    }

	
	
}
