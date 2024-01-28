#!/bin/bash

set -e

host="$1"
shift
cmd="$@"

# Make sure to export these variables in your Docker Compose or Dockerfile
export MYSQL_USER=root
export MYSQL_PASSWORD=root

until mysqladmin ping -h "$host" --silent; do
  >&2 echo "MySQL is unavailable - sleeping"
  sleep 1
done

>&2 echo "MySQL is up - executing command"
exec $cmd