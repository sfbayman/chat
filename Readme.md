• Instructions on how to compile your service
    
    Need java 8 and latest maven installed 
    clone the git repo
    get into the project directory -> cd chat
    compile using the following command 
    mvn clean install
    
• Instructions on how to run your service locally
  
    java  -jar target/chat-0.0.1-SNAPSHOT.jar com.ua.chat.ChatApplication
        
  
• The decisions you made
    
    Used H2 inmemory database
    Tried to deliver the features as per given spec
    Integrated with Swagger to see the rest documentation
    To see the swagger documentaion, use the below URL : 
      http://localhost:8080/swagger-ui.html
    Written test cases for rest layer and persistency layer 
    Since apis specs are expecting different type of response, i have created multiple models and resources 
  
• The limitations of your implementation
  
    It has in-memory database - H2
    Not integrated with relational database
    No cache implementation
    Limited test cases
    No index on username column
    
   
• What you would do if you had more time
    
    Good documentation of classes/interfaces
    Better exception handling with error codes and custom exceptions
    I would have written more unit test cases and integration test cases using karate framework 
    
 
• How you would scale it in the future
    
    scaling application perspectinve, 
      a) need to use non blocking io /reactive programming and that makes application more scalable
      b) not using any state makes ie easier to scale horizontally       
    scaling database perspectinve, data access layer need changes 
    
      
     
api examples:

   - POST /chat
    
    POST /chat HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    cache-control: no-cache
    Postman-Token: f6a45fad-916b-4b38-a726-63b2c994d5b7
    {    	
    "username": "paulrad", "text": "This is a messagwe2ww23", "timeout": 30
    }
    
    
   - GET /chat/:id
    
    GET /chat/2 HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    cache-control: no-cache
    Postman-Token: 7fee5cf1-ca69-4756-ae05-151630ea99d9
    {    	
    "username": "paulrad", "text": "This is a messagwe2ww23", "timeout": 30
    }    
    
    
    
   - GET /chats/:username
    
        
    GET /chats/paulrad HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    cache-control: no-cache
    Postman-Token: edee118a-06b4-4b77-88e5-b196d2f97685
    {    	
    "username": "paulrad", "text": "This is a messagwe2ww23", "timeout": 30
    }
