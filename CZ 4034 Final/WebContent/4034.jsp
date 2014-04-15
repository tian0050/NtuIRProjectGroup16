<%@ page import="twitter_index.*" %>
<%@ page import="twitter_crawl.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<HTML>
<head>
<script type="text/javascript">


</script>

<style type="text/css">
.main{ width:1002px;display:none;height : 100;  margin-left:auto; margin-right:auto;}
.main1{ width:1002px;height : 200;  margin-left:auto; margin-right:auto;}
.main2{width:500px;height : 200;  margin-le1ft:auto; margin-right:auto; margin-top:50px}
.one{background-color:#A4D3EE; font-size:30px; word-wrap:break-all; text-align:left}
.two{background-color:#FFB90F; font-size:30px; word-wrap:break-all; text-align:left}
.one1{background-color:#A4D3EE; font-size:20px; word-wrap:break-all; text-align:left}
.two1{background-color:#FFB90F; font-size:20px; word-wrap:break-all; text-ali1gn:left}
.three{ font-size:20px;display:none;}
</style>




</head>
<BODY> 
<div class="main1">
<img src="1.jpg"  alt="img1" align="middle" class="main1"/> </br>
</div>
<form action="allparams.do" method="post">
<h2  align = "center"><input type="text" name="name" align= 'middle'style ="height : 30px ;font-size:14pt;" size = "80">&nbsp&nbsp<input type="submit" value="Search"  align= 'middle' style = "height:2.7em ;width:5em "></h2>
</form>

<form action="recraw.do" method="post">
<h2  align = "center"> <input type="image" src="Logo.JPG" alt="Submit Form"  class="main2" title="Click the image to crawl data; 
Note: crawling should be done at most once per week" /></h2>

</form>
<p align="right" style="margin-top: 140px; font-family: Comic Sans MS;">Note: Crawling should be done at most once per week</p>
</BODY>
</HTML>