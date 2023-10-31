# Use an official MySQL runtime as a parent image
FROM mysql:latest

# Set the root password for MySQL
ENV MYSQL_ROOT_PASSWORD blueBank

# Expose the default MySQL port
EXPOSE 3306

# Create a new database
ENV MYSQL_DATABASE appointment-db

# Set the character set and collation
ENV MYSQL_CHARSET utf8mb4
ENV MYSQL_COLLATION utf8mb4_unicode_ci

# Set the default time zone
ENV TZ=UTC

# Start MySQL server
CMD ["mysqld"]

# Mount the data volume for persistent storage
VOLUME /var/lib/mysql