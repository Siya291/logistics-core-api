# Smart City Logistics Engine

A Spring Boot 4.0.3 backend service designed for high-performance urban logistics. This project features a **multi-tenant architecture** and **spatial data handling** to manage courier fleets across different vendors.

## 🚀 Key Features
* **Multi-Tenancy:** Separate data schemas for different logistics vendors using a shared-database, separate-schema approach.
* **Spatial Intelligence:** Integrated with **PostGIS** and **Hibernate Spatial** to handle real-time GPS coordinates (Geography Point 4326).
* **Automated Auditing:** JPA Auditing enabled to track `created_at` and `last_updated` timestamps automatically.
* **Modern Stack:** Built with Java 25 and Spring Boot 4.

## 🛠 Tech Stack
* **Java 25**
* **Spring Boot 4.0.3**
* **PostgreSQL 18+** with **PostGIS** extension
* **Hibernate 7.2.4**
* **Project Lombok**

## 🚦 Getting Started
1. **Database Setup:** Ensure PostgreSQL is running and the PostGIS extension is installed:
   ```sql
   CREATE DATABASE smart_city_logistics;
   CREATE EXTENSION postgis;
   CREATE SCHEMA vendor_delivery_co;