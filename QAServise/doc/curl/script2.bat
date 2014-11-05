REM	curl test script for las2peer service

REM test getUser with test userId 1
curl -v -X GET http://localhost:8080/user/userID --userID 1
PAUSE