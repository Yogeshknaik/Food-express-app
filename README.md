# Spring-Boot-with-Thymeleaf-Foodie-Web-Project
An online food ordering system generally has two components – a website or app that allows customers to view the menu and place an order, and an admin interface that enables the restaurant to receive and fulfill customer orders. this is a basic CRUD project without the implementation of a payment gateway, but customers have access to demo checkout as well.

# Configuration of Client ID & Secret in application.properties file
Follow the below steps to achieve the same.
1. Log in to the Google Cloud console, from **Navigation Menu**, and Open **API and Services**.
2. **Credentials** -> **Create Credentials** -> **OAuth Client ID** -> **Application Type (Web Application)**
3. Name -> **Any Name(Follow Good Practices, i.e. Project Name)**
4. **Authorized redirect URIs** -> **Add URI** -> **http://localhost:8080/login/oauth2/code/{client_name}**
   Note: In our project, we use Google SSO Login. So, we use **google** as **client_name** in URI
5. Click **Create**. A pop-up window came which has the **Client ID** & **Client Secret** available in the panel.
6. Then, open **project** -> **application.properties** and add the value to it.
