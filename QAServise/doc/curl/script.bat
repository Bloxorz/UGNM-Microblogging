REM	curl test script for las2peer service

REM test authentication with test user alice

curl -v -X GET http://localhost:8080/ugnmMicro/hashtag/3/questions
pause

curl -v -X GET http://localhost:8080/ugnmMicro/hashtag/3
pause


REM more curl commandlines...
PAUSE

