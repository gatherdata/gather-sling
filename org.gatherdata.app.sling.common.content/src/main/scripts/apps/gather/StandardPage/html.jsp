<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<%@page session="false" %>
<%@page import="org.apache.sling.sample.*" %>
<%@page import="java.util.*" %>
<%@page import="org.apache.sling.api.resource.Resource"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%-- Ensure the presence of the Sling objects --%>
<sling:defineObjects/>

<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>html</title>
	
	<!-- Framework CSS -->  
    <link rel="stylesheet" href="/css/blueprint/screen.css" type="text/css" media="screen, projection" />  
    <link rel="stylesheet" href="/css/blueprint/print.css" type="text/css" media="print" />  
    <!--[if IE]><link rel="stylesheet" href="/css/blueprint/ie.css" type="text/css" media="screen, projection" /><![endif]-->  
  
    <!-- Import fancy-type plugin. -->  
    <link rel="stylesheet" href="/css/blueprint/plugins/fancy-type/screen.css" type="text/css" media="screen, projection" />  

</head>
<body>

	<div class="container">
		<div id="header" class="span-24 last">
			 <h1 id="book_haven"><img src="/images/header.gif" alt="Book header image" id="header-image" /></h1>
        </div>
        <hr />

        <div id="subheader" class="span-24 last">
          <h3 class="alt">Get opinions on your latest novel, and read what other people are writing about.</h3>
          This in from jstl: <c:out value="Hello world!"/>
        </div>
        <hr />

    <div class="span-17 colborder" id="content">
          <h3 class="loud">Featured Book: "The World Without Us"</h3>
          <p>
            <img class="right" src="images/world-book.gif" alt="featured book" />
            Lorem ipsum dolor sit amet, consectetuer adipiscing elit. 
          </p>


          <p>
            Cras sagittis. Fusce porttitor felis sed massa. In hac habitasse platea dictumst.           
          </p>

          <hr />
          <div class="span-8 colborder">
            <h4 class="prepend-5">Upload a Book</h4>
            <p>
              Nam vitae tortor id ante sodales facilisis. 
            </p>      
          </div>

          <div class="span-8 last">
            <h4 class="prepend-5">Write a Review</h4>
            <p>
              Nam vitae tortor id ante sodales facilisis. Mauris ipsum. 
            </p>      
          </div>
        </div>

        <div class="span-6 last" id="sidebar">

          <div id="recent-books">
            <h3 class="caps">Recent Books</h3>

            <div class="box">
              <a href="#" class="quiet">Hygiene</a>
              <div class="quiet">Nov. 29, 2008</div>
              <div class="quiet">by Craven</div>
            </div>

          </div>

          <div class="prepend-top" id="recent-reviews">
            <h3 class="caps">Recent Reviews</h3>
            <div class="box">
              <span class="quiet">Thor reviewed Hygiene</span>
              <div class="quiet">Rating: 4/5</div>
              <a href="#" class="quiet">Read this review</a>
            </div>

          </div>
        </div>

        <hr />
    
	</div>
	
<p class="quiet">Rendered by apps/gather/StandardPage/html.jsp</p>

</body>
</html>
