Video Game Store API:

A Spring Boot Rest API powering an online video game store.



Video Game Store API:

Overview:

The Video Game Store API is the backend for an e-commerce website where users can browse video games, search and filter products, manage a shopping cart, and place orders. This project was completed as the capstone project for the Year Up United Application Development program. The goal was to extend an existing Spring Boot application by fixing bugs, implementing new features, and improving the overall backend architecture.



Features:

Existing Features:

* User registration
* User authentication/login
* Browse products by category
* Search and filter products
* Shopping cart
* Checkout and order processing



New Features Implemented:

* Creating a functional shopping cart
* Created a User Profile
* Created a functional Checkout system



Bugs Fixed:

* Bug 1: The 1st bug was the API's search functionality. It would return incorrect results. Some products that should appear never shows up in the list.

  * The solution: the bug was located in the service layer for Product in the search method. On line 29 and 30  there was a filter that only allowed the featured video games to show. Just buy removing those line it solved the issue. Initially, it was a hunch to check the service layer's search method, but my suspensions were confirmed after running a unit test and testing in insomnia. 
* Bug 2: The 2nd bug was the API's ability to allow the user to update the products stock. The feature would seem to update but it would never save the changes.

  * The solution: The bug was located in the service layer for Product in the update method. Figuring out the issue cause almost slipped past me, so I a unit test in order to catch it. The source of this bug was the could that would allow the user to update the stock was never written. So by adding one line on line 58, the issue was solved. 



Technologies Used:

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* MySQL
* Maven
* Insomnia
* Git \& GitHub



Database:

This project uses MySQL. The provided database script was imported before running the application.



API Testing:

All endpoints were tested using Insomnia.



***\*POSSIBLY ADD SCREEN SHOTS HERE\****

&#x09;***could be screenshots of the homepage, product search, shopping cart, checkout, insomnia testing.***
![image_alt](https://github.com/yu26s9-AlexisB-hue/video-game-store/blob/dfeeec889c2b47c2a1a2672f3d3ba915a646521d/Screenshot%202026-06-25%20160118.png)



Interesting Code:

The piece of code that I found to be the most interest is my checkout method in the service layer of my order class. I am the proudest of this code because at this point it was like the moment of truth on weather I created the models and repositories correctly for this service layer to work. There was a lot of trial and error in order to make this layer run smoothly. I felt truly accomplished when this method was completed.





