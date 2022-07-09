## Getting Started

Download and install/configure:

- [JDK](https://openjdk.java.net/install/) (openjdk 17.0.2 2022-01-18)
- Maven (3.8.5)
- Eclipse (optional)
- [Wildfly 26.1](https://www.wildfly.org/downloads/)
  if you have installed Eclipse, you can do this inside Eclipse by adding a new server.
- MySQL (using XAMPP or any other)

## Start Server

Start MySQL server. Create a DB named or `holiday-booking` or anything, using phpMyAdmin from XAMPP (or any other UI manager of your choice).

### Wildfly

Note: To setup with Eclipse follow this guide https://docs.wildfly.org/26/Getting_Started_Developing_Applications_Guide.html

First create a user (Management User):

`./<wildfly-dir>/bin/add-user.sh` (if it gives error, make sure this file has execution permission)

Then run the server:

`./<wildfly-dir>/bin/standalone.sh`


## Setting Up Database

Before doing this, I recommend doing the file renaming part in `Setting Up JMS` section, so you don't have to move you datasources later in that section.

### Wildfly

Follow this: https://medium.com/@hasnat.saeed/install-and-configure-mysql-jdbc-driver-on-jboss-wildfly-e751a3be60d3

Make sure the connector file name in `module.xml` and the one you downloaded match.

When adding DataSource, keep JNDI name: `java:/holidayds`

## Setting up JMS

To enable messaging services in Wildfly, rename `<wildfly-dir>/standalone/configuration/standalone.xml` to `standalone.old`, and then rename `standalone-full.xml` to `standalone.xml`.

Copy your existing `<datasources>` from the `standalone.old` file, if any, so you don't have to create them again.

Next you need to create a JMS Queue, named `holidayQueue`, from the admin panel. Use `java:/jms/holidayQueue` as the entries value.

Find the menu from:
Configuration > Subsystems > Messaging > Server > default > Destinations > JMS Queue > Add

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