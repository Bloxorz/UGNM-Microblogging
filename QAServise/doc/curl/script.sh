#!/bin/sh
URL='http://localhost:8080/example'
echo "curl test script for las2peer service"

echo "test authentication with test user alice"
curl -v -X GET $URL/validate --user alice:pwalice
echo 
echo "PRESS RETURN TO CONTINUE..."
read

echo "more curl commandlines..."

curl -v -X GET http://localhost:8080/example/getUser/2 --user alice:pwalice
read
curl -v -X PUT -d "{"rating":"UNRATED","imagePath":"http://edited","contactInfo":"http://new.html","email":"fromPut","pass":"asge90","id":"3"}" http://localhost:8080/example/editUser/3 --user alice:pwalice
read
curl -v -X GET http://localhost:8080/example/getUser/3 --user alice:pwalice


