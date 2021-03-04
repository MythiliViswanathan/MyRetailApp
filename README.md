# MyRetailApp


This is my POC of myRetail.com. I designed and implemented with Spring framework with Maven-multi module projects.

Please find the below projects in the repo 

retail-home : parent maven project
retail-api : consist of interfaces and classes used for outside world interactions
retail-app : Main application -starting point of the application
retail-datastore : contains the classes related to database.
retail-service : consist of restcontrollers and other classes which hosts restapi 

Restservices are hosted at

http://localhost:8080/myRetail/productMetaData/ =>Deals with product's name

http://localhost:8080/myRetail/products => Deals with the complete info of the product