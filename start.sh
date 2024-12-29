#!/bin/bash
set -e

docker-compose down --volumes
docker-compose rm -v
docker-compose up -d

MYSQL_CONTAINER_NAME=mysql-expense-tracker  
MYSQL_USER=root             
MYSQL_PASSWORD=root 
MYSQL_DATABASE=expense_tracker      

echo "Waiting for MySQL to start up and initialize..."
until docker exec -i "$MYSQL_CONTAINER_NAME" mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "SELECT 1" &>/dev/null; do
    echo "MySQL is unavailable - sleeping"
    sleep 3
done

echo "MySQL is up and running!"

# Ensure the database exists
echo "Checking if database $MYSQL_DATABASE exists..."

if ! docker exec -i "$MYSQL_CONTAINER_NAME" mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "SHOW DATABASES LIKE '$MYSQL_DATABASE';" | grep -q "$MYSQL_DATABASE"; then
    echo "Database $MYSQL_DATABASE does not exist. Creating it now..."
    docker exec -i "$MYSQL_CONTAINER_NAME" \
        mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "CREATE DATABASE \`$MYSQL_DATABASE\`;"
else
    echo "Database $MYSQL_DATABASE already exists. Skipping creation."
fi

# Execute SQL files
for sql_file in ./sql-scripts/**.sql; do
    echo "Copying $sql_file to $MYSQL_CONTAINER_NAME..."
    docker cp "$sql_file" "$MYSQL_CONTAINER_NAME:/tmp"

    filename=$(basename "$sql_file")

    echo "Executing $filename inside $MYSQL_CONTAINER_NAME..."
    docker exec -i "$MYSQL_CONTAINER_NAME" \
        mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$MYSQL_DATABASE" < "$sql_file"
    
    docker exec -i "$MYSQL_CONTAINER_NAME" rm "/tmp/$filename"
done

echo "All SQL files have been executed."
