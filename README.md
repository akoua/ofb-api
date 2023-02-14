# OFB API
Git repository of the RESTful API for the OFB (Office français de la biodiversité) project.

## Steps to setup
1. Clone the repository
    ```
    git clone https://gitlab.istic.univ-rennes1.fr/m2-miage-mmm/ofb-api.git
    ```
2. Create MySQL database
    ```
   create database ofb_api
    ```
3. Change mysql username and password as per your installation
   - Open src/main/resources/application.yml
   - Change spring.datasource.username and spring.datasource.password as per your MySQL installation
4. Run the app using maven
    ```
   mvn spring-boot:run
    ```
   The app will start running at http://localhost:8080


    