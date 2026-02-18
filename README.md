#💻 TechStore - Projektuppgift


## 🧩 Tema
TechStore är en backendapplikation byggd i Java/Spring Boot. Den hanterar teknikprodukter, kunder, leverantörer och reviews i en webbshop.


## 👥 Team
Anwar – Supplier‑flöde, GitHub‑setup & initierade driftsättning
Christoffer – Product‑flöde & branch‑struktur
Johanna – Review‑flöde & dokumentation
Paveena – Customer‑flöde & Homecontroller


## 🚀 Setup
1.Klona projektet från GitHut till din dator
2.Öppna projektmappen i Intellij
3. IntelliJ laddar automatiskt ner alla Maven-beroenden vid uppstart
4. Applikationen startas genom att köra Spring Boot-pluginet


## 🔐 Miljövariabler
Projektet använder en PostgreSQL-databasaslutning som ligger i application.properties.
Uppdatera url - username - password efter din egen miljö.


## 🐳 Docker (Sammanfattning)
Projektet kan köras i en Docker-container. Vi lade till en Dockerfile i projektets rotmapp och konfigurerade den för att köra den paketerade .jar-filen.

**1. Dockerfile**  
Inställningar för att köra applikationen i container:
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/TechStore-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

**2. Uppdatering av pom.xml**  
Version sattes till 1.0.0 (utan SNAPSHOT) för att Docker skulle kopiera rätt artefakt.  
Efter bygget ändrades versionen tillbaka till 2.0.0-SNAPSHOT.

**3. Paketera projektet**  
Projektet paketerades via Maven för att skapa .jar-filen som Docker använder.

**4. Bygga och köra containern**  
Containern byggdes och kördes lokalt.  
Applikationen blev tillgänglig på http://localhost:8080.

**5. Docker Hub-inloggning för CI/CD**  
DOCKERHUB_USERNAME och DOCKERHUB_TOKEN skapades och lades in som GitHub Secrets för att automatiskt push av images.

**6. Förberedelse för driftsättning**  
Docker-imagen kopplades till Render tillsammans med API-nyckeln inför deployment.
