package twitter_index;

public class Tweet {
	
	// field
	private String id;
	private String dateTime;
	private String screenName;
	private String description;
	
	// constructor 1
    public Tweet() {
    }

    // constructor 2
    public Tweet(String id,
    			 String dateTime,
                 String screenName,  
                 String description) {
        this.id = id;     
        this.dateTime = dateTime;         
        this.screenName = screenName;
        this.description = description; 
    }
    
    // setter & getter ID
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    // setter & getter dateTime
    public String getDateTime() {
        return this.dateTime;
    }
    
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    
    // setter & getter screenName
    public String getscreenName() {
        return this.screenName;
    }
    
    public void setscreenName(String screenName) {
        this.screenName = screenName;
    }

    // setter & getter description
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    public String toString() 
    {
        return "Tweet "
               + getId()
               +": "
               + getDateTime()
               +" ( "
               + getscreenName()
               +": "
               + getDescription()
               +" )";
    }
    

}
