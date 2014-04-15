package twitter_crawl;

import java.util.Map;
import java.util.StringTokenizer;

import twitter4j.*;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;

public class Authenticate 
{
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
			String general = "src/output/";
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
				
				
 
				br = new BufferedReader(new FileReader("src/Files/search_timeline.txt"));
 
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
		
	public static void main()
	{
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
}
