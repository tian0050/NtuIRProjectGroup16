package com.theopentutorials.servlets;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.*;
import javax.servlet.http.*;


import twitter_index.*;
import twitter_crawl.*;

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
 
public class RetrievingAllParams extends HttpServlet
{
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
{
        Enumeration paramNames = request.getParameterNames();
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String searchvalue = null;
        while(paramNames.hasMoreElements())
        {
            String paramName = (String)paramNames.nextElement();
            //out.print("<tr><td>" + paramName + "\n<td>");
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1)
            {
            	searchvalue = paramValues[0];
            }
        }
        ArrayList<String[]> searchData = new ArrayList<String[]>();
        System.out.print (searchvalue);
        
        out.print("<html>");
        out.print("<head>");
        out.print("<script type=\"text/javascript\">");
  
        out.print("function show(shown){");
        
        out.print("var val= document.getElementById(\"div1\").getAttribute('value');");   
        out.print("for (i=1;i<=val;i++){");
        out.print("var tid=\"table\"+i.toString();");
        out.print("document.getElementById(tid).style.display='none';");
        out.print("var rid=\"ref\"+i.toString();");
        out.print("document.getElementById(rid).style.display='block';");
        out.print("}");
        out.print("document.getElementById(shown).style.display='block';");
        out.print("return false;");
        out.print("}");

        
        out.print("</script>");
        out.print("<style type='text/css'>");
        out.print(".main{ width:1002px;display:none;height : 100;  margin-left:auto; margin-right:auto;}");
        out.print(".main1{ width:200px;height : 50; }");
        out.print(".one{background-color:#A4D3EE; font-size:30px; word-wrap:break-all; text-align:left}");
        out.print(".two{background-color:#FFB90F; font-size:30px; word-wrap:break-all; text-align:left}");
		out.print(".one1{background-color:#A4D3EE; font-size:20px; word-wrap:break-all; text-align:left}");
    	out.print(".two1{background-color:#FFB90F; font-size:20px; word-wrap:break-all; text-align:left}");
     	out.print(".three{ font-size:20px;display:none;}");
        out.print("</style>");
        out.print("</head>");
        out.print("<body>");
        out.print("<div style='width:900px;height : 50px;  margin-left:auto; margin-right:auto; vertical-align: middle'>");
        out.print("</br>");
        out.print("<form action='allparams.do' method='post'  align='middle'>");
        out.print("<img src=\"1.jpg\"  alt=\"img1\" align=\"middle\" class=\"main1\"/>&nbsp&nbsp&nbsp&nbsp<input type='text' name='name' style ='height: 30px ;font-size:14pt;' size = '60'>&nbsp&nbsp<input type='submit' value='Search' style = 'height:2.7em ;width:5em '>");
        out.print("</form>");
        out.print("</div>");
        out.print("<h1 style='font-family: Comic Sans MS;'></h1>");
        
        int count = 0;
        int ntable=1;
        searchData = SearchResult(searchvalue);
        //System.out.println(searchData);
        for (Iterator<String[]> iter1 = searchData.iterator(); iter1.hasNext();  ) {
        	String[] temp;
        	temp = iter1.next();
        	if (count==0){
        		out.println("<table class='main' id = 'table1' >");
        		ntable=1;
        	}
        	else if (count==25){
        		out.println("</table>");
        		out.println("<table class='main' id = 'table2'>");
        		ntable=2;
        	}
        	else if (count==50){
        		out.println("</table>");
        		out.println("<table class='main' id = 'table3'>");
        		ntable=3;
        	}
        	else if (count==75){
        		out.println("</table>");
        		out.println("<table class='main' id = 'table4'>");
        		ntable=4;
        	}
        	else if (count==100){
        		
        		break;
        	}
        	if ((count+ntable)%2==0){
	            out.print("<tr class='one' style='font-family: Comic Sans MS;'> <td colspan='3'>" + temp[7] +"</td></tr>");
	            out.print("<tr class='one1' style='font-family: Comic Sans MS;'> <td>Name: " + temp[1] +"</td><td>Time: " + temp[3] +"</td><td>Score: " + temp[5] +"</td><td></tr>");
        	}else{
        	    out.print("<tr class='two' style='font-family: Comic Sans MS;'> <td colspan='3'>" + temp[7] +"</td></tr>");
                out.print("<tr class='two1' style='font-family: Comic Sans MS;'> <td>Name: " + temp[1] +"</td><td>Time: " + temp[3] +"</td><td>Score: " + temp[5] +"</td><td></tr>");
                
        	}
            count++;
            
        }
       
        out.println("</table>");
        
        out.println("<div id='div1' value=\""+ntable+"\">");
        
        out.print("<table align = 'center' ><tr><td>");
        
	    out.print("<a id= \"ref1\" class=\"three\" href=\"#\" onclick=\"return show('table1');\">1</a></td><td>");
	    out.print("<a id= \"ref2\" class=\"three\" href=\"#\" onclick=\"return show('table2');\">2</a></td><td>");
	    out.print("<a id= \"ref3\" class=\"three\" href=\"#\" onclick=\"return show('table3');\">3</a></td><td>");
	    out.print("<a id= \"ref4\" class=\"three\" href=\"#\" onclick=\"return show('table4');\">4</a></td><td>");
	    out.print("</table>");
        
        out.print("<script type=\"text/javascript\">");

        

        
        out.print("show('table1');");
        
        out.print("</script>");

       
        
        
        }

	public void show() {
		// TODO Auto-generated method stub
		
	}

	public void createTable() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<String[]> SearchResult(String searchvalue) {

		ArrayList<String[]> searchData = new ArrayList<String[]>();
	      try {
	  		
	    	

	        SearchEngine instance = new SearchEngine();
	        Hits hits = instance.performSearch(searchvalue);
	        System.out.println("Results found: " + hits.length());
	        Iterator<Hit> iter =  hits.iterator();
	        while(iter.hasNext()){
	        	String[] singleSearchData = new String[8];
	            Hit hit = iter.next();
	            Document doc = hit.getDocument();
	            //System.out.println(doc.get("name")
	                              // + " " + doc.get("city")
	                              // + " (" + hit.getScore() + ")");
	            singleSearchData[0] = "name";
	            singleSearchData[1] = doc.get("screenName");
	            singleSearchData[2] = "time";
	            singleSearchData[3] = doc.get("dateTime");
	            singleSearchData[4] = "score";
	            singleSearchData[5] =  " (" + hit.getScore() + ")";
	            singleSearchData[6] = "description";
	            singleSearchData[7] =  doc.get("description");
	            searchData.add(singleSearchData);
	        }	       
	      } catch (Exception e) {
	        System.out.println("Exception caught.\n");
	      }
		return searchData;
		
	}
}