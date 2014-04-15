package twitter_index;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TweetDatabase 
{
	public static ArrayList<Tweet> TweetsList = new ArrayList<Tweet>();
	
	public static void constructTweetsList()
	{
		TweetsList.clear();
		
		int id = 1;
		String dateTime, screenName, content;
		String [] tweetField;
		Tweet tweet;
		
		String tweetString = "";
		
		// read in keyword-pull file to totalString
		BufferedReader br = null;
		try
		{
			String sCurrentLine;
			br = new BufferedReader(new FileReader("D:\\workspace\\test1\\src\\output\\Crawled_File_Search0.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
				tweetString += sCurrentLine;
				if(sCurrentLine.trim().endsWith("##"))
				{
					System.out.println(id+" : "+tweetString);
					tweetString.trim();
					tweetField = tweetString.split("\\$");
					dateTime = tweetField[0];
					dateTime+="##";
					screenName = tweetField[1];
					content = tweetField[2];
					content = "##"+content;
					tweet = new Tweet(id+"", dateTime, screenName, content);
					TweetsList.add(tweet);
					id++;
					tweetString = "";
				}
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)
					br.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		// finish read file 1, empty tweetString
		
		tweetString = "";
		
		// read in time-line-pull file
		BufferedReader br_1 = null;
		try
		{
			String sCurrentLine;
			br_1 = new BufferedReader(new FileReader("D:\\workspace\\test1\\src\\output\\Crawled_File0.txt"));
			while ((sCurrentLine = br_1.readLine()) != null) 
			{
				tweetString += sCurrentLine;
				if(sCurrentLine.trim().endsWith("##"))
				{
					tweetField = tweetString.split("\\$");
					dateTime = tweetField[0];
					dateTime+="##";
					screenName = tweetField[1];
					content = tweetField[2];
					content = "##"+content;
					tweet = new Tweet(id+"", dateTime, screenName, content);
					TweetsList.add(tweet);
					id++;
					tweetString = "";
				}
				
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br_1 != null)
					br_1.close();
			} catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		
		tweetString = "";
		
	}

}
