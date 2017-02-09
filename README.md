# DB Server - Lunch Voting System
This project was developed to solve the problem of choose a local for lunch. To deal with that incredible problem a voting system was developed, in this system each team user can select his restaurant preference for each day of the week and after everyone had make a choice a restaurant winner is presented.

This experiment will be running in a Ubuntu 14.04LTS machine with eclipse, for another environments you will need to adapt.

This system will allow the users to vote using a list of available restaurants and also to see the restaurant winner.

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

This will add to system PA# Instalando Maven para controle de dependências
> $ sudo apt-get install maven  

## User account configuration (for web ui access)
open the following configuration file:
> $ sudo gedit $CATALINA_HOME/conf/tomcat-users.xml  

Add the following lines between the tags `<tomcat-users>` and `</tomcat-users>`:
> <!-- user manager can access only manager section -->  
> <role rolename="manager-gui" />  
> <user username="manager" password="_SECRET_PASSWORD_" roles="manager-gui" />  

> <!-- user admin can access manager and admin section both -->  
> <role rolename="admin-gui" />  
> <user username="admin" password="_SECRET_PASSWORD_" roles="manager-gui,admin-gui" />  

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

Update the target folder and run the system in local or production modes:
> $ mvn clean install  
> $ java -jar target/voting-0.0.1-SNAPSHOT.jar --spring.profiles.active=local  
> $ java -jar target/voting-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod  

Open in your browser this [System url](http://localhost:8080/voting).

# Tests
An automated test coverage is important so a value over 90% is considered acceptable.
To run the tests execute the following command:
> $ TBD

# Challenges
Thinking about the needed architecture and also make the initial setup configuration is of course the most part of the problem.

# Improvements
Configure the use of Flyway plugin to deal with database migrations.
