
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<h1>Customer Signup And Login</h1>
	<p>This project requires a backend for login and signup REST APIs with Security/Web tokens. The project is built on SpringBoot and uses MySql database. Follow the steps below to install and set up the project.</p>
less
Copy code
<h2>Prerequisites</h2>
<ul>
	<li>Java JDK 11 or higher</li>
	<li>Maven 3.6.3 or higher</li>
</ul>

<h2>Installation</h2>
<ol>
	<li>Clone the project from GitHub: <code>git clone https://github.com/username/https://github.com/fp05107/GreenStitch_Assignment.git</code></li>
	<li>Navigate to the project directory: <code>cd GreenStitch_Assignment</code></li>
	<li>Build the project using Maven: <code>mvn clean install</code></li>
	<li>Start the application: <code>java -jar target/GreenStitch_Assignment.jar</code></li>
</ol>

<h2>Usage</h2>
<p>The application runs on port 8080 by default. You can access the following endpoints:</p>

<h1>Customer Controller </h1>
<p>The CustomerController class contains the REST API endpoints for handling customer-related requests.</p>
<h2>Endpoints</h2>
<p>The following endpoints are available:</p>
<ul>
  <li>GET /hello - returns a welcome message</li>
  <li>POST /customers - saves a new customer to the database</li>
  <li>GET /customers/{email} - retrieves a customer by email</li>
  <li>GET /customers - retrieves all customers</li>
</ul>
<h1>Login Controller</h1>
<p>This controller handles GET requests to the "/signIn" endpoint to return the details of the authenticated customer.</p>
	<h2>Endpoints</h2>
<p>The following endpoints are available:</p>
<ul>
  <li>GET /signIn - Customer can login using this end point</li>
  
</ul>
	
<p>For more information on each endpoint, see the JavaDocs in the code.</p>


</body>
</html>
