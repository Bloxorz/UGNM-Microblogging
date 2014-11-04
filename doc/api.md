Your Service API Documentation
==

This service offers...

In the following we describe each resource including its supported operations in detail.

AuthResource
--
__URL Template:__ /resource/{id}

__Operations:__

* __Retrieve resource:__ retrieves a resource given its identifier.

    * __HTTP Operation:__ GET
    * __Consumes:__ -
    * __Produces:__ application/json; a JSON string in the following form `{'field1':'field1_val'}` (describe fields)
    * __Parameters:__ path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 404: resource does not exist

* __Update resource__

    * __HTTP Method:__ PUT
    * __Consumes:__ application/json; content in the form `{'field1':'field1_val'}` (describe fields)
    * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 400: content data in invalid format
            * 404: resource does not exist

User Password
--
__URL Template:__ /user/{id}/setPassword

__Operations:__

* __Update password__

    * __HTTP Method:__ POST
    * __Consumes:__ application/json; content in the form `{'password':'hashvalue of new password'}`
    * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 400: content data in invalid format
            * 404: resource does not exist
            * 500: internal error

User Contact
--
__URL Template:__ /user/{id}/setContact

__Operations:__

* __Update contact__

    * __HTTP Method:__ POST
    * __Consumes:__ application/json; content in the form `{'contact':'new contactlink'}`
    * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 400: content data in invalid format
            * 404: resource does not exist
            * 505: internal error
            
* __Delete contact__

    * __HTTP Method:__ DELETE
    * __Consumes:__ -
    * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 404: resource does not exist
            * 505: internal error
            
User Elo
--
__URL Template:__ /user/{id}/setElo

__Operations:__

* __Update elo__

    * __HTTP Method:__ POST
    * __Consumes:__ application/json; content in the form `{'elo':'new elo'}`
    * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 400: content data in invalid format
            * 404: resource does not exist
            * 500: internal error

FavoriteQuestion Resource
--
__URL Template:__ /favorites/{id}

__Operations:__

* __Retrieve favorite question:__ .retrive all favorite questions from user

    * __HTTP Operation:__ GET
    * __Consumes:__ -
    * __Produces:__ application/json; a JSON string in the following form `{'id':'questions_id', 'text':'questions_text'}` 
    * __Parameters:__ authorization header
    * __HTTP Status Codes:__
        * Success: 200
        * Errors: 
            * 404: resource does not exist

* __Favorite the question:__ mark question as favorite

    * __HTTP Method:__ POST
    * __Consumes:__ -
    * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 400: data in invalid format
            * 404: resource does not exist

* __Delete the favorite question:__ delete question from favorite questions

    * __HTTP Method:__ DELETE
    * __Consumes:__ -
    * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 400: data in invalid format
            * 404: resource does not exist


Answer Resource
--
__URL Template:__ /answers/questions/{id}

__Operations:__

* __Retrieve retrive answers:__ .retrive all answers

    * __HTTP Operation:__ GET
    * __Consumes:__ -
    * __Produces:__ application/json; a JSON string in the following form `{'id':'questions_id', 'text':'questions_text'}` 
    * __Parameters:__ authorization header
    * __HTTP Status Codes:__
        * Success: 200
        * Errors: 
            * 404: resource does not exist

* __Favorite the question:__ mark the question as favorite

    * __HTTP Method:__ POST
    * __Consumes:__ -
    * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 400: data in invalid format
            * 404: resource does not exist

* __Delete the favorite question:__ delete the question from favorite questions

    * __HTTP Method:__ DELETE
    * __Consumes:__ -
    * __Produces:__ -
    * __Parameter:__ authorization header, path parameter 'id'
    * __HTTP Status Codes:__
        * Success: 200
        * Errors:
            * 400: data in invalid format
            * 404: resource does not exist
