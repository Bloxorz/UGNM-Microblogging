REM	curl test script for las2peer service

REM test authentication with test user alice

curl -v -X GET http://localhost:8080/ugnmMicro/hashtag/token
pause
curl -v -X GET http://localhost:8080/ugnmMicro/user/token
pause
curl -v -X GET http://localhost:8080/ugnmMicro/user/1/token
pause
curl -v -X GET http://localhost:8080/ugnmMicro/hashtag/token --user alice:pwalice
PAUSE
curl -v -X GET http://localhost:8080/ugnmMicro/hashtag/token
pause
curl -v -X GET http://localhost:8080/ugnmMicro/hashtag/2/token
pause
curl -v -X GET http://localhost:8080/ugnmMicro/expertises/token
pause
curl -v -X GET http://localhost:8080/ugnmMicro/expertise/3/token
pause
curl -v -X GET http://localhost:8080/ugnmMicro/questions/token
pause
curl -v -X GET http://localhost:8080/ugnmMicro/question/4/token
pause
curl -v -X GET http://localhost:8080/ugnmMicro/answer/5/token

REM more curl commandlines...
PAUSE

