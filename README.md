# INFO4 CPOO project

This is the base project your team **MUST** FORK to implement its version of the instant messaging app.

## Server

The server is a Spring Boot app located in the /server directory.
An embryo allowing to receive and send messages to other domains is provided.

## Client

The client is an Angular Web app located directly under the /client directory.

## API and end-to-end tests

The /api/serverapi.yml file contains the OpenAPI specification of the server API.
The /api/e2etests contains Java end-to-end test code of the server API (generated using OpenAPI code generator).

## Mockups

The "static" mockup of the UI (images, HTML files...) is in the /client/mockups directory.

The "actionable" mockup is delivered directly under the /client directory as an angular app embryo.

## Router

The message router is a Spring Boot app located in the /router directory.
The server uses it to send and receive messages.
It is deployed at https://cpoo-router.mightycode.tech/.

