# Project Documentation: Bundle Creation Guide

## Introduction
Welcome to the documentation for the bundle creation process of the project. This project serves as a bundle, acting as a wrapper that combines multiple components into a final application. It includes a Java Spring Boot backend and three microfrontends implemented using different frameworks: Angular, React, and Vue.js.

The purpose of this bundle is to provide users with a sample or template to kickstart their own application development.

## Prerequisites
Before proceeding with the bundle creation process, ensure that you have the following prerequisites installed on your system:

1. Java Development Kit (JDK) - version 17
2. Node.js - version 18 or higher
3. NPM - version 9 or higher
4. text editor or an integrated development environment (IDE) of your choice

## Bundle Creation Process

### Initialize Your Bundle
1. Open a terminal or command prompt.
2. Navigate to the directory where you want to create your bundle.
3. Run the following command to initialize your bundle:
```bash
ent bundle init my-bundle --from-hub --hub-url=<hub_url>
```
Replace `<hub_url>` with the URL of the hub from where you want to initialize your bundle.

### Optional: Remove Unwanted Microfrontends
1. Open a terminal or command prompt. 
2. Navigate to the project root directory. 
3. Run the following command to remove a specific microfrontend:
```bash
ent bundle mfe rm <mfe_name>
```
Replace <mfe_name> with the name of the microfrontend you want to remove (e.g., vue-widget-agenda, angular-widget-agenda, react-widget-agenda).
Note: Be cautious when removing a microfrontend as it will permanently delete the corresponding files and configurations.

### Backend Configuration and Start
1. Open the project in your preferred Java IDE or text editor.
2. Customize the backend code and configurations according to your application requirements.
3. Start the Spring Boot backend application by running the following command in a terminal or command prompt:
```bash
ent bundle run springboot-agenda
```
> Please note, the springboot-agenda microservice require both a Postgresql database, and a Keycloak to run.
> This bundle provides SVC to run them as container for development purposes
> run `ent bundle svc start agenda-postgresql` and `ent bundle svc start agenda-keycloak`

### Microfrontend Configuration and Build
1. For each microfrontend framework (Angular, React, and Vue.js):
- Open the respective microfrontend module in your preferred IDE or text editor.
- Customize the microfrontend code and configurations as per your application needs.
2. Install the project dependencies and build the microfrontends by running the following command in a terminal or command prompt:
```bash
ent bundle run <mfe-name>
```
> Replace `<mfe_name>` with the name of the microfrontend you want to run.

If you want to run all the microfrontends using one command, you use that command instead:
```bash
ent bundle run --all-mfe
```
## Conclusion
Congratulations! You have successfully created your own bundle based on this project. You can now start developing your application by customizing the backend and microfrontends according to your requirements. Feel free to explore the code and leverage this bundle as a sample or template for your future projects.

If you need any further assistance or have any questions, please refer to the project documentation or reach out to our (https://join.slack.com/t/entandocommunity/shared_invite/zt-g609owdv-2K~YRh8zrI6lqlWo4aFWUw)[Slack Community] for help.
