# SSL Chat Projekt

## Projektbeschreibung
Dieses Projekt implementiert einen einfachen SSL-gesicherten Chat zwischen Client und Server in Java. Die Kommunikation wird mit TLS/SSL verschlüsselt, um die Datenintegrität und Vertraulichkeit zu gewährleisten.

---

## Voraussetzungen
- Java Development Kit (JDK) 21 oder höher
- Maven (für das Bauen des Projekts)
- `keytool` (im JDK enthalten)

---

## Projekt bauen
Im Projektordner führe folgenden Befehl aus:

```bash
mvn clean package
```

## Keystore und Truststore erstellen
Keystore und Truststore erstellen
1. Keystore für Server generieren
bash
```
keytool -genkeypair -alias chatKey -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore chatkeystore.p12 -validity 3650
Gib ein sicheres Passwort ein.
```
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
Hinweise
Ersetze DEIN_PASSWORT durch die echten Passwörter, die du bei der Keystore-/Truststore-Erstellung vergeben hast.

Stelle sicher, dass Server vor dem Client gestartet wird.

Kommunikation ist verschlüsselt über SSL/TLS.

Lizenz
Dieses Projekt ist unter der MIT-Lizenz lizenziert.

