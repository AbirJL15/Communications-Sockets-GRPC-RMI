## Mini-Projet Distributed Communication 

This project demonstrates communication using different technologies: gRPC, RMI, and Sockets. It consists of three modules.

## 1. Socket Module: Group Chat Application

## Overview
This module implements a simple group chat application where multiple clients can connect to a server and exchange messages in a chat room setting.

## Features
- **Server**: The server is responsible for accepting incoming client connections and facilitating communication between clients.
- **Client**: Clients can connect to the server, send messages to the chat room, and receive messages from other clients.
- **Multi-threading**: Both server and client utilize multi-threading to handle multiple connections simultaneously.
- **Username**: Each client can choose a username, which is displayed along with their messages in the chat.

## Files
- **Server.java**: Contains the server-side logic for accepting client connections and managing communication.
- **Client.java**: Implements the client-side functionality for connecting to the server, sending messages, and receiving messages.
- **ClientHandler.java**: Represents a thread handling communication with a specific client on the server-side.
- **README.md**: This file. Provides an overview of the project and instructions for running the application.

## How to Run
1. Compile all Java files using `javac *.java`.
2. Start the server by running `java Server`.
3. Run multiple instances of the client using `java Client`.
4. Enter a username for each client when prompted.
5. Start chatting!

## 2. RMI module:  Task List Management

This module demonstrates task list management using Java Remote Method Invocation (RMI). It allows users to add tasks to a list, remove existing tasks from the list, and retrieve the complete list of tasks.

### Overview

The module consists of two main components:
1. **RemoteTaskList Interface**: Defines the remote methods that can be invoked by clients. These methods include adding a task, removing a task, and retrieving all tasks.
2. **RemoteTaskListImpl Class**: Implements the RemoteTaskList interface and provides the actual functionality for task list management. It maintains an internal list of tasks and allows clients to interact with it remotely.

### Features
- **Add Task**: Clients can add tasks to the task list.
- **Remove Task**: Clients can remove existing tasks from the task list.
- **Get All Tasks**: Clients can retrieve the complete list of tasks.

### Files
- **RemoteTaskList.java**: Interface defining remote methods for task list management.
- **RemoteTaskListImpl.java**: Implementation class providing functionality for task list management using RMI.
- **TaskListClient.java**: Client application that interacts with the task list server to perform various operations.

### How to Run
1. Compile all Java files using `javac *.java`.
2. Start the RMI registry by running `rmiregistry` command in a separate terminal or command prompt window.
3. Start the Task List Server by running `java RemoteTaskListImpl`.
4. Run the Task List Client by running `java TaskListClient`.
5. Follow the instructions in the client application to perform task list operations.





