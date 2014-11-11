RESTful Serivce API documentation for UGNM Microblogging group

In the following we describe each resource including its supported operations in detail.

__date:__ 10.11.2014   
__version:__ 1.0 

Hashtag Resource
--
__URL Template:__ /hashtag

__Operations:__

* __Retrieve resource:__ retrieves a collection of all hashtags.

   * __HTTP Operation:__ GET
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String'}]`
   * __Parameter:__ authorization header
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized 
            * 500: server error

* __Create resource__

   * __HTTP Method:__ POST
   * __Consumes:__ application/json; JSON string in the form ``{'token':'String', 'text':'String'}`
   * __Produces:__ - application/json; JSON string in the form ``{'id':'long'}`
   * __Parameter:__ authorization header, path parameter 'text' - the hashtag text
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error

__URL Template:__ /hashtag/{id}

__Operations:__

* __Update resource:__ updates a hashtag.

   * __HTTP Operation:__ PUT
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String', 'text':'String'}`
   * __Parameter:__ authorization header, path parameter 'text' - the hashtag text
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized 
            * 500: server error
            * 304: not modified
            * 400: bad request




* __Delete resource__

   * __HTTP Method:__ DELETE
   * __Consumes:__ application/json; JSON string in the form ``{'token':'String', 'id':'long'}`
   * __Parameter:__ authorization header, path parameter 'id' - the hashtag id
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error

__URL Template:__ /hashtag{id}/questions

__Operations:__

* __Retrieve resource:__ retrieves a collection of all questions related to given hashtag.

   * __HTTP Operation:__ GET
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String', 'timestamp':'timestamp', 'authorId':'long'}]`
   * __Parameter:__ authorization header
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error


Question Resource
--
__URL Template:__ /question

__Operations:__

* __Retrieve resource:__ retrieves a collection of all questions.

   * __HTTP Operation:__ GET
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String', 'timestamp':'timestamp', 'authorId':'long'}]`
   * __Parameter:__ authorization header, 'text' question text, 'timestamp' creation time, 'authorId' userId of the author
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized 
            * 500: server error

 __Create resource__ creates a new question resource
   * __HTTP Method:__ POST
   * __Consumes:__ application/json; JSON string in the form `{'token':'String', 'text':'String', 'authorId':'long'}`
   * __Produces:__ - application/json; JSON string in the form ``{'id':'long'}`
   * __Parameter:__ authorization header, path parameter 'text' - the question text, 'authorId' the author's id
   * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 304: not modified
            * 400: bad request
            * 403: forbidden you are not authorized
            * 500: server error
            


__URL Template:__ /question/{id}

* __Update resource:__ updates a question text.

   * __HTTP Operation:__ PUT
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String', 'text':'String'}`
   * __Parameter:__ authorization header, path parameter 'text' - the question text
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized 
            * 500: server error
            * 304: not modified
            * 400: bad request
            * 404: not found, resource with given id does not exist


*

* __Delete resource__ deletes a question and it's related answers

   * __HTTP Method:__ DELETE
   * __Consumes:__ application/json; JSON string in the form ``{'token':'String', 'id':'long'}`*/
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

* __Retrieve resource:__ retrieves a collection of all answers to a given question.

   * __HTTP Operation:__ GET
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String', 'id':'long'}`
   * __Produces:__ application/json; JSON string in the form `[{'id':'long', 'text':'String', 'timestamp':'timestamp', 'authorId':'long'}]`
   * __Parameter:__ authorization header, 'id' the question id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized 
            * 500: server error
            * 404: not found, resource with given id does not exist
            * 400: bad request
__URL Template:__ /question/{id}/bookmarked

__Operations:__

* __Retrieve resource:__ retrieves a collection of all users who favoured this question.

   * __HTTP Operation:__ GET
   * __Consumes:__ -application/json; JSON string in the form `{'token':'String', 'id':'long'}`
   * __Produces:__ application/json; JSON string in the form `[{...}]`
   * __Parameter:__ authorization header, 'id' the question id
  * __HTTP Status Codes:__
    * Success: 200
    * Errors:
            * 403: forbidden, you are not authorized 
            * 500: server error
            * 404: not found, resource with given id does not exist
            * 400: bad request