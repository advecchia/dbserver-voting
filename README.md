# DB Server - Lunch Voting System
This project was developed to solve the problem of choose a local for lunch. To deal with that incredible problem a voting system was developed, in this system each team user can select his restaurant preference for each day of the week and after everyone had make a choice a restaurant winner is presented.

This experiment will be running in a Ubuntu 14.04LTS machine with eclipse, for another environments you will need to adapt.

This system will allow the users to vote using a list of available restaurants and also to see the restaurant winner.

# Jenkins configuration
I have followed the following link to install a local Jenkins, you can do the same for the sake of the test: [Install Jenkins](https://wiki.jenkins-ci.org/display/JENKINS/Installing+Jenkins+on+Ubuntu "Jenkins on Ubuntu").  

Change the current port for Jenkins to `8081` because we will use `8080` for Tomcat server. Follow these steps:  
> $ sudo gedit /etc/init.d/jenkins

Look for `$HTTP_PORT` variable and change its value to `8081`. close the file and restart the server, execute:  
> sudo service jenkins restart

You will can access the Jenkins in your browser followin this link: [Link to local jenkins](http://localhost:8081/jenkins/login?from=%2Fjenkins%2F).  

After the initial steps and accout creation, modify the JDK and Maven settings, go to [Configure Tools](http://localhost:8081/jenkins/configureTools/). Change JDK(build 1.8.0_121-b13) and Maven(3.0.5) installations.  

Go to Jenkins home and a new item, add the github project url: `https://github.com/advecchia/dbserver-voting/` and add the github repository to download everything (at Source Code Management): https://github.com/advecchia/dbserver-voting.git   

Now at Jenkins home you will can a running job!

# Installing Maven for dependecy control
> $ sudo apt-get install maven

# Installing Tomcat to run server
I will be running with Tomcat 9 and for that Java 8 version from Oracle is necessary.

You can follow the installation steps using the below links or following my instructions after them: 
[Tomcat for Linux](https://tomcat.apache.org/tomcat-9.0-doc/setup.html "Installing Tomcat")
[Step by step for Linux](http://askubuntu.com/questions/777342/how-to-install-tomcat-9 "Tutorial")

## Tomcat Download
For this operation you will need administrator permissions:

> $ cd /home/<your-user-account>/tomcat  
> $ sudo wget http://www-us.apache.org/dist/tomcat/tomcat-9/v9.0.0.M17/bin/apache-tomcat-9.0.0.M17.tar.gz  
> $ sudo tar xzf apache-tomcat-9.0.0.M17.tar.gz  

## Creating a Tomcat user
To run locally we will create a group and user for Tomcat:

> $ sudo groupadd tomcat  
> $ sudo useradd -s /bin/false -g tomcat -d /home/<your-user-account>/tomcat/apache-tomcat-9.0.0.M17 tomcat  

Change the access permissions for this user:

> $ cd /home/<your-user-account>/tomcat/apache-tomcat-9.0.0.M17  
> $ sudo chgrp -R tomcat conf  
> $ sudo chmod g+rwx conf  
> $ sudo chmod g+r conf/*  
> $ sudo chown -R tomcat work/ temp/ logs/  

## Environment configuration
Open the `.bashrc` file in your home and add the following lines (correct the paths when need):

> export CATALINA_HOME=/home/<your-user-account>/tomcat/apache-tomcat-9.0.0.M17  
> export JAVA_HOME=/usr/lib/jvm/java-8-oracle  
> export JRE_HOME=/usr/lib/jvm/java-8-oracle/jre  

Add maven to system configuration to deal with project dependencies:  
> $ sudo apt-get install maven  

## User account configuration (for web ui access)
open the following configuration file:
> $ sudo gedit $CATALINA_HOME/conf/tomcat-users.xml  

Add the following lines between the tags `<tomcat-users>` and `</tomcat-users>`:
> `<!-- user manager can access only manager section -->`  
> `<role rolename="manager-gui" />`  
> `<user username="manager" password="_SECRET_PASSWORD_" roles="manager-gui" />`  

> `<!-- user admin can access manager and admin section both -->`  
> `<role rolename="admin-gui" />`  
> `<user username="admin" password="_SECRET_PASSWORD_" roles="manager-gui,admin-gui" />`  

For security, change the passwords

If you want, you can change other features of Tomcat in the following file (for example http port)
> $ sudo gedit $CATALINA_HOME/conf/server.xml  

Stop the execution and restart following the below commands:
> $ sudo $CATALINA_HOME/bin/catalina.sh stop  
> $ sudo $CATALINA_HOME/bin/catalina.sh start  

If you can see the home page for Tomcat in the following url `http://localhost:8080` everything is installed successfully. so you can stop the service again, because the project will call a Tomcat execution when needed.
> $ sudo $CATALINA_HOME/bin/catalina.sh stop  

# Run the system
Download the application from Git using the following [repository](https://github.com/advecchia/dbserver-voting.git "Repository Download").
> $ git clone https://github.com/advecchia/dbserver-voting.git  
> $ cd dbserver-voting  

Install javascript dependencies:  
> $ npm install  

Update the target folder and run the system in local or production modes:  
> $ mvn spring-boot:run -Drun.profiles=local  
> $ mvn spring-boot:run -Drun.profiles=prod  

Open in your browser this [System url](http://localhost:8080/voting).  

# Back end Tests
Run unit test and see the coverage report from Jacoco plugin using the below command:  
> mvn clean test jacoco:report  

Open the file `index.html` at `{project_folder}/target/site/` to see the report. The coverage will be below 60% but there some private classes from API that I have not make test at this moment because they are not accessible.  

# Front end Tests
An automated test coverage is important so a value over 90% is considered acceptable.  
To run the front end tests execute the following command:  
> $ karma start  

After that you can see a new folder called `coverage` in the root folder.  
In the second folder level there is a `index.html` file that when opened show the coverage report.  

# Documentation
The API docs are generated using swagger2 plugin, after run the system, take a look at `http://localhost:8080/voting/swagger-ui.html` to see it.  

# Challenges
Think about the architecture and also make the initial setup configuration is of course the most difficult part of the task.  

# Improvements
Configure the use of Flyway plugin to deal with database migrations.  
