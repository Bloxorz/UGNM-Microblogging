REM	curl test script for las2peer service

REM test authentication with test user alice
curl -v -X GET http://localhost:8080/example/validate --user alice:pwalice
PAUSE
curl -v -X POST http://localhost:8080/example/myMethodPath/HalloWelt! --user alice:pwalice
PAUSE
curl -v -X POST http://localhost:8080/example/user/2/setPassword --user alice:pwalice --data {'password':'hallo!'}

REM more curl commandlines...
PAUSE

