**Method:**


    I used springboot with an MVC design for this task, relying on a java backend.
    
    I also went with maven over gradle.

    I was going to use project lombok - hence the annotations - but it was not working properly so I used setters/getters
    
    as normal. That is a future direction for this.
    
    For HTTP requests I used apache.
    
    I relied on GSON for string/JSON transforms and deserialization.
    
    
    I reliaed heavily on Tree structures and TreeSets and TreeMaps to preserve 
        1) order in dates without sorting
        2) tree structure for myself
        
    I wrote a test endpoint in my controller to get the initial data.
    
    
    I began writing a sample front end in react-redux - but I didn't get to finish it.
    
    I also did not have time to write mocks/tests but intended to use for further testing
    
    of the endpoint. 
    
    I also could not autowire the utility - so it's instanciated.
    
    
    (a bug that's workable).

RUN:
    
    I tested the endpoints in postman, but here is how to run the project.
    
    
    1) mvn clean install spring-boot:run
    
    2) the GET end point is at localhost:8080/api/request
