REM	curl test script for las2peer service

REM test authentication with test user alice
curl -v -X GET http://localhost:8080/ugnmMicro/hashtag/token --user alice:pwalice
PAUSE

curl -v -d "spielen" -X POST http://localhost:8080/ugnmMicro/hashtag/token --user alice:pwalice
PAUSE

REM more curl commandlines...
PAUSE

