# Overview #

The goal of this Proof of Concept (PoC) is to show an application that receives FpML documents/messages, parse and convert them to the JSON format,  and finally save them into the Database. 

# Design & Architecture #

The application can read/accept FpML messages from two sources:

* Reading from a Queue 
* * We are using Apache ActiveMQ. 
* Receiving from a Web Service (WS)
* * We are using a REST-like WS. We could also have used a SOAP message. 

After receiving the FpML message we parse and validate the FpML message with free available C24-iO Open Edition (http://www.c24.biz/) component.
There’s also another free component that we could have used for the FpML validation Rules http://www.handcoded.com/Technology/Validation.aspx#demo

Technologies/Components used in the PoC: 

* C24-iO Open Edition
* Apache Camel 
* Apache Active MQ
* Mongo DB 

![![poc.png](https://bitbucket.org/repo/9MLayB/images/2720156272-poc.png)](https://bitbucket.org/repo/9MLayB/images/1847583446-poc.png)

# Scenarios #

We are using a FpML Swap Trade Affirmation message.  We used two different FpML versions: v4.1 and version v5.3. 

1. FpML v5.3: After Receiving the FpML message we parse/validate using only XSDs. We don't use any third-party library.
2. FpML v4.1: After receiving the FpML message we parse and validate the FpML message with free available C24-iO Open Edition (http://www.c24.biz/) component.  The reason we are using v4.1, is because the free license works only with this version.

For each scenario, we devised 3 tests (.bat files):

* Import a valid FpML message 
* Import an invalid FpML message (missing tags)
* Import an invalid FpML (Invalid Business rule)

# How To Run #

## 1. Software Required ##

1. Install MongoDB. We are using localhost: 27017
1. Install ActiveMQ. We are using localhost: 61616
1. You DONT need to install Apache Camel. It already comes bundled with the application.

## 2. Before running… ##
 
You can see that there are no trades in the database. (MondoDB and FpML Camel Service need to be running) You can check this in two ways:

1)	Via Browser. In the browser you can type

		a.	http://localhost:1024/poc/trade/count 
		        This path counts number of records in the database
		b.	http://localhost:1024/poc/trade/findall
		        This is path invokes the find all web service, which retrieves all trades from the database

2) 	Via Database. You can access mongoDB database and check for all trades:

	Follow the steps below:
		1. run /bin/mongo (to run mongo command line)
		2. use poc (to use the poc database)
		3. db.trades.count() (checks that there’s nothing)

## 3. Running the examples… ##

Start the services/application in the following order:


		1. Start MongoDB (Once installed, it should be a windows service)
		2. Start Active MQ. (on Windows, just go to bin folder and run activemq.bat)
		3. Start CPQi FpML Camel Service 
		   a. Go to folder bin/release
		   b. run runImportExample.bat (starts Camel Service) 
		4. Run any of the scripts (.bat files). You need to be inside the folder bin/release to run the examples (scripts). Look in the Camel Console for errors/messages.

Scripts available to run (.bat files):

	runInsertValidFpML41File.bat
	runInsertValidFpML41WebService.bat
	runInsertInvalidFpML41File.bat
	runInsertInvalidFpML41WebService.bat

	runInsertValidFpML53File.bat
	runInsertValidFpML53WebService.bat
	runInsertInvalidFpML53File.bat
	runInsertInvalidFpML53WebService.bat
	runInsertInvalidRuleFpML41File.bat
	runInsertInvalidRuleFpML41WebService.bat

***The third-party components C24 that we are using expires after 30 min. After this time he will not validate FpML anymore. Just restart the CPQi FpML Camel Service if this happens.***

## 4. After running… ##

After running each the script (.bat file), you can check what was inserted into the database:

	1. db.trades.count() (counts number of records in the db)
	2. db.trades.find() (list all trades in the db. Similar to SQL select)

To clear the database after each run:

	In the MongoDB console, type:

	    db.trades.remove({})