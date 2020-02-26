# QuickFrame

Technologies Needed:
Open-JDK 13
PostgreSQL 12+:
Maven
IDE (Ideally, use IntelliJ)

## IDE Configuration
Ensure that the maven dependencies are imported automatically.
In IntelliJ IDE, go to Preferences/Settings (depends on what OS you have), go to Maven section and in its Importing section, make sure that the "Import Maven projects automatically" checkbox is checked.

## Environment Configuration
### Creating the database:
Open up your terminal. Enter the following command:
-       $ psql postgres

Now that you're in the postgres console, enter the following two commands:
-       postgres=# create database quickframe;
-       postgres=# alter database quickframe owner to postgres;

NOTE: If you want to use a different name for the database, that's fine. Make sure to edit the following line in the "application.properties" file of this project.
spring.datasource.url = jdbc:postgresql://localhost:5432/<database-name>

### Add the credentials to system env for database access:
- Go to your system configuration file(e.g. bash_profile). (In my case, its .zshenv file)
Add the following two lines:
#quickframe db credentials
-       export QUICKFRAME_DB_USER=<your_db_username>
-       export QUICKFRAME_DB_PASS=<your_db_pass>

Set the above variables to the username and password of the user that created the database.

## Application Run
There are two ways of running the application:
1. Open the project in IntelliJ IDE. Right click on the file, QuickFrameApplication.java, under the package named "com.quickframe". Then, click "Run QuickFrameApplication".
2. Open your terminal. 
     Type the following command:
-       mvn spring--boot:run

Once the application is done running, head over back to the postgres console and verify the data in the table, "classification_totals".
-       postgres=# \c quickframe;
-       postgres=# select * from classification_totals;

That's all!
