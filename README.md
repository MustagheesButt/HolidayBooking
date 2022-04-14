## Getting Started

Download and install/configure:

- [JDK](https://openjdk.java.net/install/) (openjdk 17.0.2 2022-01-18)
- Maven (3.8.5)
- ~~Glassfish (6)~~ [Wildfly 26.1](https://www.wildfly.org/downloads/)
- MySQL (using XAMPP or any other)

## Start Server

Start MySQL server.

### Wildfly

First create a user:

`wildfly-26.1.0.Beta1/bin/add-user.sh`

Then run the server:

`./wildfly-26.1.0.Beta1/bin/standalone.sh`

Note: To setup with Eclipse follow this guide https://docs.wildfly.org/26/Getting_Started_Developing_Applications_Guide.html

### Glassfish

`./glassfish6/bin/asadmin start-domain`

Then if you will open localhost:8080 it will show a page with link to admin panel. From the admin panel, you can find a link to this application from "List Deployed Applications".


## Setting Up Database

### Wildfly

Follow this: https://medium.com/@hasnat.saeed/install-and-configure-mysql-jdbc-driver-on-jboss-wildfly-e751a3be60d3

Make sure the connector file name in `module.xml` and the one you downloaded match.

When adding DataSource, keep JNDI name: java:/local-mysql

### Glassfish
Download MySQL connector from https://dev.mysql.com/downloads/connector/j/
Select platform independent and download zip or tar file. Extract and find `jar` file and put in `glassfish6/glassfish/lib` and in `glassfish6/glassfish/domains/domain1/lib`

Create a JDBC Connection pool
 - Pool name: HBPool
 - Resource type: java.sql.Driver
 - Driver Class name: com.mysql.jdbc.Driver
 - Additional properties:
   - user, password, url = jdbc:mysql://localhost:3306/holiday-booking

After saving, in General tab, click ping button to test if connection is successfull.

After that create a JDBC Resource named `local-mysql`

## Building/Compiling

`mvn clean -f "/home/<user_name>/Documents/Projects/holiday-booking/pom.xml"`

`mvn package -f "/home/<user_name>/Documents/Projects/holiday-booking/pom.xml"`

Or just use the menu shortcuts (if) provided by your IDE. This will create a `holiday-booking.war` in `target` directory.

## Deploying

### Wildfly

Use admin panel to upload `.war` file. After deployed successfully, it should be available at localhost:8080/

### Glassfish
You just have to copy the `target/holiday-booking.war` to glassfish (`glassfish6/glassfish/domains/domain1/autodeploy`).

On Linux, you can just run `deploy.sh` (You might need to adjust `deploy.sh`)