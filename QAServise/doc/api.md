UserResource
--
__URL Template:__ /user/{id}

__Operations:__

* __Retrieve resource:__ user.

   * __HTTP Operation:__ GET
   * __Consumes:__ -
   * __Produces:__ application/json; a JSON string in the following form `{'rating':'int (1..5)','imagePath':'String','contactInfo':'String', 'email':'String','pass':'String'}`
  * __Parameters:__ path parameter 'id' - the userId
  * __HTTP Status Codes:__
     * Success: 200
     * Errors:
	        * 404: user does not exist

* __Update resource__

   * __HTTP Method:__ PUT
   * __Consumes:__ application/json; content in the form `{'rating':'int (1..5)','imagePath':'String','contactInfo':'String', 'email':'String','pass':'String'}` 
   * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id' - the userId
    * __HTTP Status Codes:__
     * Success: 200
     * Errors:
           * 400: content data in invalid format
           * 404: user does not exist
           * 500: Server error

* __Delete resource__

   * __HTTP Method:__ DELETE
   * __Consumes:__ application/json; content in the form `{'id':}`
   * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id' - the userId
    * __HTTP Status Codes:__
     * Success: 200
     * Errors:
           * 400: content data in invalid format
           * 404: user does not exist
           * 500: Server error
           
QuestionResoure
--
__URL Template:__ /question/{id}

__Operations:__

* __Retrieve resource:__ questions to user.

   * __HTTP Operation:__ GET
   * __Consumes:__ -
   * __Produces:__ application/json; a JSON string in the following form `{ timeStamp':'String', 'text:'String', 'authorId':'long'}`
  * __Parameters:__ path parameter 'id' - the questionId
  * __HTTP Status Codes:__
     * Success: 200
     * Errors:
	        * 404: question does not exist

* __Update resource__

   * __HTTP Method:__ PUT
   * __Consumes:__ application/json; content in the form `{__URL Template:__ /question/{id}

__Operations:__

* __Retrieve resource:__ question.

   * __HTTP Operation:__ GET
   * __Consumes:__ -
   * __Produces:__ application/json; a JSON string in the following form `{'timeStamp':'String', 'text:'String', 'authorId':'long'}`
  * __Parameters:__ path parameter 'id' - the questionId
  * __HTTP Status Codes:__
     * Success: 200
     * Errors:
	        * 404: question does not exist

* __Update resource__

   * __HTTP Method:__ PUT
   * __Consumes:__ application/json; content in the form `{'timeStamp':'String', 'text:'String', 'authorId':'long', 'hashtagIDs':'long[]'}` 
   * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id' - the questionId
    * __HTTP Status Codes:__
     * Success: 200
     * Errors:
           * 400: content data in invalid format
           * 404: question does not exist
           * 500: Server error

* __Delete resource__

   * __HTTP Method:__ DELETE
   * __Consumes:__ application/json; content in the form `{'id':}`
   * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id' - the userId
    * __HTTP Status Codes:__
     * Success: 200
     * Errors:
           * 400: content data in invalid format
           * 404: question does not exist
           * 500: Server error
* __Post resource__

   * __HTTP Method:__ POST
   *               
   * __Consumes:__ application/json; content in the form `{'timeStamp':'String', 'text:'String', 'authorId':'long', 'hashtagIDs':'long[]'}`
   * __Produces:__ application/json; content in the form `{'id':}`
    * __HTTP Status Codes:__
     * Success: 200
     * Errors:
           * 400: content data in invalid format
           * 500: Server error

}` 
   * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id' - the questionId
    * __HTTP Status Codes:__
     * Success: 200
     * Errors:
           * 400: content data in invalid format
           * 404: question does not exist
           * 500: Server error

* __Delete resource__

   * __HTTP Method:__ DELETE
   * __Consumes:__ application/json; content in the form `{'id':}`
   * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id' - the userId
    * __HTTP Status Codes:__
     * Success: 200
     * Errors:
           * 400: content data in invalid format
           * 404: question does not exist
           * 500: Server error
* __Post resource__

   * __HTTP Method:__ POST
   *               
   * __Consumes:__ application/json; content in the form `{'timeStamp':'String', 'text:'String', 'authorId':'long', 'hashtagIDs':'long[]'}`
   * __Produces:__ application/json; content in the form `{'id': long}`
    * __HTTP Status Codes:__
     * Success: 200
     * Errors:
           * 400: content data in invalid format
           * 500: Server error

__URL Template:__ /question/user/detail/{id}

__Operations:__ Returns the questions given to a userid

* __Retrieve resource:__ question.

   * __HTTP Operation:__ GET
   * __Consumes:__ -
   * __Produces:__ application/json; a JSON string in the following form `{[]{'questionID': 'long','timeStamp':'String', 'text:'String', 'authorId':'long'}}`
  * __Parameters:__ path parameter 'id' - the userId
  * __HTTP Status Codes:__
     * Success: 200
     * Errors:
	        * 404: question does not exist
	        * 500: Server Error
	        
__URL Template:__ /question/user/{id}

__Operations:__  Returns the questionsIds given to a userid

* __Retrieve resource:__ question.

   * __HTTP Operation:__ GET
   * __Consumes:__ -
   * __Produces:__ application/json; a JSON string in the following form `{[]{'questionID'}}`
  * __Parameters:__ path parameter 'id' - the userId
  * __HTTP Status Codes:__
     * Success: 200
     * Errors:
	        * 404: question does not exist
	        * 500: Server Error
	        
__URL Template:__ /question/hashtag/detail/{id}

__Operations:__ Returns the questions given to a hashtag id

* __Retrieve resource:__ question.

   * __HTTP Operation:__ GET
   * __Consumes:__ -
   * __Produces:__ application/json; a JSON string in the following form `{[]{'questionID': 'long','timeStamp':'String', 'text:'String', 'authorId':'long'}}`
  * __Parameters:__ path parameter 'id' - the hashtagId
  * __HTTP Status Codes:__
     * Success: 200
     * Errors:
	        * 500: Server Error
	        
__URL Template:__ /question/hashtag/{id}

__Operations:__ Returns the questionIds given to a hashtag id

* __Retrieve resource:__ question.

   * __HTTP Operation:__ GET
   * __Consumes:__ -
   * __Produces:__ application/json; a JSON string in the following form `{[]{'questionID'}}`
  * __Parameters:__ path parameter 'id' - the hashtagId
  * __HTTP Status Codes:__
     * Success: 200
     * Errors:
	        * 404: question does not exist
	        * 500: Server Error





