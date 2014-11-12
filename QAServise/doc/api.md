
RESTful Serivce API documentation for UGNM Microblogging group

In the following we describe each resource including its supported operations in detail.

__date:__ 11.11.2014   
__version:__ 1.0

Hashtag Resource
--
__URL Template:__ /hashtag

__Operations:__

* __Retrieve resource:__ Retrieves a collection of all hashtags.

   * __HTTP Operation:__ GET
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String'}]`
   * __Parameter:__ authorization header
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error

* __Create resource:__ Creates a new hashtag

   * __HTTP Method:__ POST
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'text':'String'}`
   * __Produces:__ application/json; JSON string in the form `{'id':'long'}`
   * __Parameter:__ authorization header, path parameter 'text' - the hashtag text
   * __HTTP Status Codes:__
    * Success: 201
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error

__URL Template:__ /hashtag/{id}

__Operations:__
* __Retrieve resource:__ Retrieves a hashtag.

   * __HTTP Operation:__ GET
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `{'text':'String'}`
   * __Parameter:__ authorization header, id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error

* __Update resource:__ Updates a hashtag.

   * __HTTP Operation:__ PUT
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'text':'String'}`
   * __Parameter:__ authorization header, path parameter 'text' - the hashtag text, id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
            * 304: not modified
            * 400: bad request

* __Delete resource__

   * __HTTP Method:__ DELETE
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Parameter:__ authorization header, path parameter 'id' - the hashtag id
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error

__URL Template:__ /hashtag/{id}/questions

__Operations:__

* __Retrieve resource:__ retrieves a collection of all questions related to given hashtag.

   * __HTTP Operation:__ GET
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String', 'timestamp':'timestamp', 'authorId':'long'}]`
   * __Parameter:__ authorization header, id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error

__URL Template:__ /hashtag/{id}/expertises

__Operations:__

* __Retrieve resource:__ retrieves a collection of all expertises competent of this hashtag.

   * __HTTP Operation:__ GET
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String'}]`
   * __Parameter:__ authorization header, id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error

* __Create resource__ makes new expertise competent of this hashtag
   * __HTTP Method:__ POST
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'expertiseId':'long'}`
   * __Produces:__ -
   * __Parameter:__ authorization header, id, expertiseId
   * __HTTP Status Codes:__
    * Success: 201
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error
            
Question Resource
--
__URL Template:__ /question

__Operations:__

* __Retrieve resource:__ retrieves a collection of all questions.

   * __HTTP Operation:__ GET
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String', 'timestamp':'timestamp', 'authorId':'long'}]`
   * __Parameter:__ authorization header
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error

* __Create resource__ creates a new question resource
   * __HTTP Method:__ POST
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'text':'String', 'authorId':'long'}`
   * __Produces:__ application/json; JSON string in the form `{'id':'long'}`
   * __Parameter:__ authorization header, path parameter 'text' - the question text, 'authorId' the author's id
   * __HTTP Status Codes:__
    * Success: 201
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error
            


__URL Template:__ /question/{id}

* __Retrieve resource:__ Retrieves a question.

   * __HTTP Operation:__ GET
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'id':'long'}`
   * __Produces:__ application/json; JSON string in the form `{'text':'String', 'timestamp':'timestamp', 'authorId':'long'}`
   * __Parameter:__ authorization header, id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
* __Update resource:__ Updates a question text.

   * __HTTP Operation:__ PUT
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'text':'String'}`
   * __Parameter:__ authorization header, path parameter 'text' - the question text, id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
            * 304: not modified
            * 400: bad request
            * 404: not found, resource with given id does not exist



* __Delete resource:__ Deletes a question and it's related answers

   * __HTTP Method:__ DELETE
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Parameter:__ authorization header, path parameter 'id' - the question id
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 404: not found, resource with given id does not exist
            * 500: server error

__URL Template:__ /question/{id}/answers

__Operations:__

* __Retrieve resource:__ Retrieves a collection of all answers to a given question.

   * __HTTP Operation:__ GET
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String', 'timestamp':'timestamp', 'authorId':'long'}]`
   * __Parameter:__ authorization header, 'id' the question id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
            * 404: not found, resource with given id does not exist
            * 400: bad request
            
* __Create resource:__ Posts a new answer relative to a given question id

   * __HTTP Method:__ POST
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'text':'String', 'authorId:'long'}`
   * __Produces:__ application/json; JSON string in the form `{'id':'long'}`
   * __Parameter:__ authorization header, path parameter 'text' - the answer text, 'id' the question id
   * __HTTP Status Codes:__
    * Success: 201
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 404: not found, resource with given id does not exist
            * 500: server error

__URL Template:__ /question/{id}/bookmarked

__Operations:__

* __Retrieve resource:__ Retrieves a collection of all users who favoured this question.

   * __HTTP Operation:__ GET
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'userId':'long', 'rating':'int', 'email':'String'}]`
   * __Parameter:__ authorization header, 'id' the question id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
            * 404: not found, resource with given id does not exist
            * 400: bad request
        
Answer Resource
--
__URL Template:__ /answer/{id}

__Operations:__

* __Retrieve resource:__ Retrieves an answer.

   * __HTTP Operation:__ GET
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `{'text':'String', 'timestamp':'String', 'authorId':'long'}`
   * __Parameter:__ authorization header, 'id' the answer id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 404: not found, resource with given id does not exist
            * 500: server error
            
* __Delete resource:__ Deletes an answer.

   * __HTTP Operation:__ DELETE
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Parameter:__ authorization header, 'id' the answer id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
            * 404: not found, resource with given id does not exist
            * 400: bad request

* __Update resource:__ Modifies an existing answer

   * __HTTP Method:__ PUT
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'text':'String'}`
   * __Parameter:__ authorization header, path parameter 'text' - the answer text, 'id' the answer id
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 404: not found, resource with given id does not exist
            * 500: server error
            
User Resource
--

__URL Template:__ /user/

__Operations:__

* __Create resource:__ create a new User.

   * __HTTP Operation:__ POST
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String','email':'String','passhash':'String', 'kontaktlink':'String', 'profilbild':'String', 'elo':'Integer'}`
   * __Produces:__ application/json; JSON string in the form `{'id':'long'}`
   * __Parameter:__ authorization header, email, passhash, kontaklink, profilbild, elo
  * __HTTP Status Codes:__
    * Success: 201
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error

* __Retrieve resource:__ Retrieves all users.

   * __HTTP Operation:__ GET
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'email':'String', 'kontaktlink':'String', 'profilbild':'String', 'elo':'Integer'}]`
   * __Parameter:__ authorization header
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error

__URL Template:__ /user/{id}

__Operations:__

* __Retrieve resource:__ Retrieves a users.

   * __HTTP Operation:__ GET
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `{'email':'String', 'kontaktlink':'String', 'profilbild':'String', 'elo':'Integer'}`
   * __Parameter:__ authorization header, 'id' the user id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 404: not found, resource with given id does not exist
            * 500: server error


* __Update resource:__ Update a User.

   * __HTTP Operation:__ PUT
   * __Consumes:__ application/json; JSON string in the form `{'token':'String','email':'String','passhash':'String', 'kontaktlink':'String', 'profilbild':'String', 'elo':'Integer'}`
   * __Produces:__ -
   * __Parameter:__ authorization header, id, email, passhash, kontaklink, profilbild, elo
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
            * 304: not modified
            * 400: bad request
            * 404: not found, resource with given id does not exist


* __Delete resource:__ Deletes a User.

   * __HTTP Operation:__ DELETE
   * __Consumes:__ application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ -
   * __Parameter:__ authorization header, 'id' the user id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
            * 404: not found, resource with given id does not exist
            * 400: bad request

__URL Template:__ /user/{id}/bookmark

__Operations:__

* __Retrieve resource:__ Retrieves a collection of all question which are favoured by this user.

   * __HTTP Operation:__ GET
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'questionId':'long',  'text':'String', 'timestamp':'timestamp'}]`
   * __Parameter:__ authorization header, 'id' the user id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
            * 404: not found, resource with given id does not exist
            * 400: bad request

* __Add resource:__ Add a bookmark to a question.

   * __HTTP Operation:__ POST
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'questionid':'long'}`
   * __Produces:__ application/json; JSON string in the form `{'IdUserToQuestion':'long'}`
   * __Parameter:__ authorization header, 'id' the user id
  * __HTTP Status Codes:__
    * Success: 201
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error
            * 404: not found, resource with given id does not exist
            * 400: bad request




Expertise Resource
--
__URL Template:__ /expertise/

__Operations:__

* __Retrieve resource:__ Retrieves a collection of all expertises.

   * __HTTP Operation:__ GET
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String'}]`
   * __Parameter:__ authorization header
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 500: server error

* __Create resource:__ Posts a new expertise

   * __HTTP Method:__ POST
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'text':'String'}`
   * __Produces:__ - application/json; JSON string in the form `{'id':'long'}`
   * __Parameter:__ authorization header, 'text' - the expertise text
   * __HTTP Status Codes:__
    * Success: 201
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 404: not found, resource with given id does not exist
            * 500: server error

__URL Template:__ /expertise/{id}

__Operations:__

* __Retrieve resource:__ Retrieves an expertise.

   * __HTTP Operation:__ GET
   * __Consumes:__ -
   * __Produces:__ application/json; JSON string in the form `{'text':'String'}`
   * __Parameter:__ authorization header, 'id' the expertise id
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 404: not found, resource with given id does not exist             * 500: server error
            
* __Delete resource:__ Deletes an expertise.

   * __HTTP Operation:__ DELETE
   * __Consumes:__ -
   * __Produces:__ -
   * __Parameter:__ authorization header, 'id' the expertise id
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 404: not found, resource with given id does not exist
            * 500: server error

* __Update resource:__ Modifies an existing expertise

   * __HTTP Method:__ PUT
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'text':'String'}`
   * __Produces:__ -
   * __Parameter:__ authorization header, text, id
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 404: not found, resource with given id does not exist
            * 500: server error

__URL Template:__ /expertise/{id}/assignedhashtags

__Operations:__

* __Retrieve resource:__ Retrieves all hashtags which are referred to by the expertise with given id

   * __HTTP Operation:__ GET
   * __Consumes:__ -
   * __Produces:__ application/json; JSON string in the form `[{'text':'String', 'idHashtag':'long'}]`
   * __Parameter:__ authorization header, id
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized
            * 404: not found, resource with given id does not exist             * 500: server error
            
* __Create resource:__ Assigns a hashtag to the expertse with given id

   * __HTTP Method:__ POST
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'id':'long'}`
   * __Produces:__ - application/json; JSON string in the form `{'id':'long'}`
   * __Parameter:__ authorization header, id
   * __HTTP Status Codes:__
    * Success: 201
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 404: not found, resource with given id does not exist
            * 500: server error


