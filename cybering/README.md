# Prerequisites:
  The following should be installed to get the application up-and-running the fastest way possible:
   * IntelliJ IDEA Community (configured with Java 16)
   * Angular IDE by CodeMix
   * MongoDB Community Server (with MongoDB Compass)
  Note that this is only the recommended software and the application can also be run with different ways provided the setup is done the right way.

# Project Structure:
  The project structure follows the convention to keep the client and server sides separated. Consequentially, the project is divided as follows:
   * Inside the 'cybering' folder resides the back-end server side and this is the folder that will be opened with IntelliJ IDEA. This folder also contains the 'cybering-app' folder in which the front-end client side is present.
   * Inside the 'cybering-app' folder resides the front-end client side and this is the folder that will be opened with Angular IDE.
 
  Both folders contain a 'src' folder in which the whole logic of each side is contained.

# Adding the node-modules:
  In order for the front-end to run, the following commands should be run inside the 'cybering-app' folder:
        
     npm install -g node-modules
     npm install ngx-cookie-service --save
     
# Running the Client Side:
  After installing the required node-modules, you can run the client side directly from the Angular IDE: 
  
    Servers (bottom left window) -> Web Applications -> cybering-app -> Start Server

# Running the Server Side:
  After setting up the IntelliJ IDEA to look at the 'cybering' folder, make sure to include all the Maven dependencies (done automatically within IntelliJ) and simply
  
    Run Application || Shift+F10

# Application:
  To use the application navigate to:
    
    localhost:4200

# Database:
  In order to have a better overview of the database, use MongoDB Compass with the following connection string:
      
    localhost:27017
      
