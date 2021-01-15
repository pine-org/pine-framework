# Getting Started

## Prerequisites

- java 8
- maven 3
- Apache ActiveMQ Classic

### Install Apache ActiveMQ

- Download [Apache ActiveMQ Classic](https://activemq.apache.org/components/classic/download/) package.
- Extract package then go to the bin directory.
- Run ActiveMQ `./activemq start`
- Shutdown ActiveMQ `./activemq stop`

### Using the administrative interface

- URL: http://127.0.0.1:8161/admin/
    - Login: admin
    - Password: admin

### Run

    mvn spring-boot:run