# Invitation system
A simple invitation system created in Java using JavaFX. The users' logging and registration handled with a SQL database. The currently logged users and pending invitations are stored and managed by a server (multithreading).
Initially this was meant to be a chequers game. Unfortunately, it ended as the invitation system with user log-on. Will be worked on in the future.  
UI made in the polish language.

### How to run
1. Add the mysql-connector-j-8.0.33.jar from the lib folder as library.
2. Setup a database, the needed table is in the chequers.sql file. Connection establishes in the ConnectionClass.java. There are things like the database name, user name and password to login. I use MySQL - if you want other database system or you've changed default login and password, take a look into this file.
3. Run a server by compiling Server.java. Start as many clients as needed by compiling the HelloApplication.java. You can register or check for the exisiting users in the database.
4. After logging in, enter the server's ip or type 'local' to connect to a local server.
5. Now you can enter a nick of invited person. The two buttons underneath provide information about received and sent invitations (in this order).
