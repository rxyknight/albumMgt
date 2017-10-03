# Album Management Project by Java 
## Overview
This project aims to build up a Album management systems, in C-S structure, by utilising the basic syntax of Java with common components, including network socket, multi thread, Java GUI and Derby Database.
##How to run
1.  Run the Server

```
javac MyCollectionServer.java
java MyCollectionServer
```

2. Open another terminal then run the Client

```
javac MyCollection.java
java MyCollection
```

## Environment & Usage
This software includes Server side and Client side.
+ Server Side
  
  Server side requires: initial album collcetion.xml file and customised data structure to stands for album. They needs to be add as following structure. The "MyCollectionServer" is the main programm ruuning at server side


  ![image](https://cloud.githubusercontent.com/assets/28517651/26786921/8101eeec-4a4b-11e7-97c4-cf9192cf1f34.png)


+ Client Side

  Client side has GUI, utilising Java GUI components - swing. It also requires the same customised data structure to stands for album, thus to communicate with Server side to implement functions, such as adding, deleting, etc. They needs to be added as following structure 

  ![image](https://user-images.githubusercontent.com/28517651/27001088-ab5067f2-4e04-11e7-8e6c-8ea12d02e611.png)

To run the project, you should run "MyCollectionServer" first on server side. The server process will be running waiting for the connection from any client. Then run "MyCollection" on client side. The GUI window will pup-out. By clicking "connection server" button, you need to specify the server address and then connection will be build.

![image](https://user-images.githubusercontent.com/28517651/31112816-96a2f790-a861-11e7-8f22-c86e4735fdba.png)

![image](https://user-images.githubusercontent.com/28517651/27001148-a3016b5e-4e05-11e7-8d78-f730095e9865.png)

By running this project, you can view all the album records on server via client GUI. By clicking then album name, details will display. Meanwhile, you can also add, delete the albums.

![image](https://user-images.githubusercontent.com/28517651/27036084-0f42220a-4fc7-11e7-857d-7e6a722eac2f.png)

## Technical and Key points

This project utilises following four key components of Java: Java network program (socket), Multi-process, Java GUI (litsener) and Derby database.
+ Socket

  Socket is the combination of IP and port number to identify the uniq service on the internet. Client and Server can use socket to establish connections to communicate with each other.
  + #### reference libraries

    | Name | Description |
    | ---- | ----------- | 
    |java.net.socket | Main library cotains all the socket objects and relavant members|
    |java.net.ConnectException | test|
    |java.net.UnknownHostException | test|
    |java.net.ServerSocket | test|

  + #### key concept and process

    Server will run a particular service on server machine, locating by IP of server and port of the process; socket is the way to identify this service. A new socket will run for a server service. After this, server side will be ready for any client connection. On client side, to initial a socket to connect to server, by specifying server IP and server port number. 

    ![image](https://user-images.githubusercontent.com/28517651/27036830-7b604dd4-4fc9-11e7-8492-e88eb733eb81.png)

  + #### Input & Output method

    There is a standard way to deal with the input and output data for both client side and server side
    + Input 

      There are 3 layers of wraps
      ``` java
      Inputstream sin = new Inputstream;
      sin = socket.getInputStream(); //first layer

      InputStreamReader sinread = new InputStreamReader(sin); //second layer

      BufferedReader buffer = new BufferedReader(sinread); //third layer

      //then, the inputsrtream data can be read, and used as logic condition
      String str;
      str=buffer.readLine();
      if (str = "sample") {
        ...
      }
      ```
    + Output

      There are two methods 
      ```java
      printwriter(sout).println
      .....flush();

      objectOutputStream(sout).writeObject(...)
      .....flush();

      ```
+ Multi-thread            
  + #### life cycles
    
    It's important to know the life cycles of a thread. The java thread states are as: 

    1. New
    2. Runnable
    3. Running
    4. Non-Runnable (Blocked)
    5. Terminated

    In addition, the relationship between five states above, illustrated as following:

    ![image](https://user-images.githubusercontent.com/28517651/27128172-e0daa650-5140-11e7-94ad-0578f309e4e1.png)
      
  + #### key concept and process

    There are 2 ways of defining

    1. By extending Thread class
        - override run() method;
        - everything within run() is called a job.
        - after instantiating t.start(), job from run() begins
        - both main thread and child thread runs simultaneously
        - no fixed flow of execution between main and child
        - Thread scheduler is part of JVM
    2. By implementing Runnable Interface (in java.lang and only method is run()) (Best approach)
        - class A implements Runnable{ public void run(){ … }
        - after instantiating, Thread t = new Thread(target Runnable object) and t.start();
+ Java Gui and listener
  It's a bit complicated that we Java GUI will utilise multiple libraries to display proper windows, buttons and actions on different button.
  + #### reference libraries
    | Name | Description |
    | ---- | ----------- | 
    |java.awt.BorderLayout ||
    |java.awt.Color ||
    |java.awt.FlowLayout ||
    |java.awt.GridLayout ||
    |java.awt.Toolkit ||
    |java.awt.event.ActionEvent ||
    |java.awt.event.ActionListener ||
    |java.awt.event.WindowEvent ||
    |java.awt.event.WindowListener ||

  + #### anonymous inner class listeners
    Defining an inner class listener to handle events is a very popular style. You can create an inner class without specifying a name, this is known as an anonymous inner class. Anonymous inner classes can make your code easier to read because the class is defined where it is referenced. 
  + #### code structure
    Examples of Using *EventHandler*. The simplest use of EventHandler is to install a listener that calls a method on the target object with no arguments. In the following example, we create an ActionListener that invokes the toFront method on an instance of javax.swing.JFrame.
    ```java
    //Equivalent code using an inner class instead of EventHandler.
    myButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.toFront();
        }
    });
    ```

 