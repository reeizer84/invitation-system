# Invitation system
A simple invitation system created in Java using JavaFX. Users' logging and registration handled with SQL database, currently logged users and pending invitations stored and managed by server (multithreading).  
Initially this was meant to be a chequers game. Unfortunately, ended as the invitation system with user log-on. Will be worked on in the future.  
UI made in polish language.

### How to run
1. Add mysql-connector-j-8.0.33.jar from the lib folder as library.
2. Setup a database, needed table is in the chequers.sql file. Connection establishes in ConnectionClass.java. There are things like database name, user name and password to login. I use MySQL - if you want other database system or you changed default login and password, look into this file.
3. Run the server by compiling Server.java. Start as many clients as needed by compiling HelloApplication.java. You can register or check for exisiting users in the database.
4. After logging in, enter the server's ip or type 'local' to connect to local server.
5. Now you can enter a nick of invited person. Two buttons underneath provide information about received and sent invitations (in this order).
