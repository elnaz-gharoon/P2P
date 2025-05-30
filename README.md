# SSL Chat Projekt

## Projektbeschreibung
Dieses Projekt ist eine einfache Client-Server-Chat-Anwendung, die mithilfe von SSL/TLS-Verschlüsselung eine sichere Kommunikation zwischen zwei (oder mehreren) Clients und einem Server ermöglicht.
Die Verbindung basiert auf Java-Standardklassen (SSLSocket, SSLServerSocket) und verwendet einen selbst erstellten Keystore und Truststore zur Authentifizierung und Verschlüsselung der Nachrichten.
---

## Voraussetzungen
- Java Development Kit (JDK) 21 oder höher
- Maven (für das Bauen des Projekts)
- `keytool` (im JDK enthalten)

---

## Projekt bauen
Im Projektordner führe folgenden Befehl aus:

```bash
mvn compile
```

## Keystore und Truststore erstellen
Keystore und Truststore erstellen
1. Keystore für Server generieren
bash
```
keytool -genkeypair -alias chatKey -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore chatkeystore.p12 -validity 3650

```
und dann <Gib ein sicheres Passwort ein.>
Fülle die abgefragten Informationen (Name, Organisation etc.) aus.

2. Zertifikat aus Keystore exportieren
bash
```
keytool -exportcert -alias chatKey -storetype PKCS12 -keystore chatkeystore.p12 -file serverCert.pem
```
3. Truststore für Client anlegen und Zertifikat importieren
```
keytool -importcert -alias serverCert -file serverCert.pem -storetype PKCS12 -keystore chattruststore.p12
Passwort für Truststore eingeben.
```
Mit „Ja“ bestätigen, um dem Zertifikat zu vertrauen.

Server starten
bash
```
java -Djavax.net.ssl.keyStore=chatkeystore.p12 -Djavax.net.ssl.keyStorePassword=DEIN_PASSWORT -cp target org.example.SSLChatServer
Der Server lauscht auf Port 9999.
```
Client starten
bash
```
java -Djavax.net.ssl.trustStore=chattruststore.p12 -Djavax.net.ssl.trustStorePassword=DEIN_PASSWORT -cp target org.example.SSLChatClient
Der Client verbindet sich zum Server (localhost:9999).
```
Hinweise 1
Ersetze DEIN_PASSWORT durch die echten Passwörter, die du bei der Keystore-/Truststore-Erstellung vergeben hast.

Stelle sicher, dass Server vor dem Client gestartet wird.

Kommunikation ist verschlüsselt über SSL/TLS.

Lizenz:
Dieses Projekt ist unter der MIT-Lizenz lizenziert.


Ergebnis:
![image](https://github.com/user-attachments/assets/6c708205-c0ae-49d5-92c4-244b48787757)



