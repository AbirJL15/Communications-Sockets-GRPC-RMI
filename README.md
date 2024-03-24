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

## 3. GRPC module: Messaging Service
This module implements a messaging service using gRPC, allowing clients to send text messages to specific recipients and retrieve received messages for a given user.

### Overview

The module consists of two main components:
1. **Protocol Buffer Definition**: Defines the message types and service methods using Protocol Buffers (protobuf) syntax.
2. **Server and Client Implementation**: Includes the server-side and client-side implementation of the messaging service using gRPC.

### Protocol Buffer Definition

The protocol buffer definition (`Messaging.proto`) specifies the following message types and service methods:
- **TextMessage**: Represents a text message with fields for message ID, sender ID, recipient ID, and message text.
- **MessagingService**: Defines two RPC methods:
  - `SendMessage`: Allows clients to send a text message to a specified recipient.
  - `GetReceivedMessages`: Allows clients to retrieve received messages for a given user ID.

### Server Implementation

The `MessageServiceImpl` class implements the server-side logic for the messaging service:
- `sendMessage`: Handles the `SendMessage` RPC method, including message sending logic and response construction.
- `getReceivedMessages`: Handles the `GetReceivedMessages` RPC method, including retrieving received messages for the given user ID and sending them through the stream.

### Client Implementation

The `MessageClient` class provides a client application to interact with the messaging service:
- Creates a gRPC channel to connect to the server.
- Creates a stub for making RPC calls to the server.
- Calls the `SendMessage` RPC method to send a text message to a recipient.
- Handles any exceptions that occur during the RPC call and shuts down the channel after use.

### How to Run

1. Compile the Protocol Buffer definition file (`Messaging.proto`) to generate Java classes using `protoc`.
2. Compile the Java source files for the server (`MessageServiceImpl.java`) and client (`MessageClient.java`).
3. Start the gRPC server by running `MessageServiceImpl.main()`.
4. Run the client application (`MessageClient.main()`) to send messages or retrieve received messages.



