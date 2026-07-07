![Logo](./assets/logo.png)

# Repository pubblica – Progetto scolastico MidiFlux

> [!NOTE]
> **Uso di strumenti IA**
> - Il progetto, incluse alcune parti della GUI, è stato sviluppato con il supporto di strumenti di Intelligenza Artificiale.
> - Il design e la progettazione dell'interfaccia grafica sono stati definiti interamente dai membri del gruppo, senza utilizzo diretto di IA.
> - Gli strumenti di IA sono stati utilizzati esclusivamente come supporto creativo e per il miglioramento estetico e visivo del progetto.

---

## Documentazione di progetto

La documentazione è consultabile [qui](https://pdfhost.io/it-IT/v/jsF2W8Nyy8_Documentazione_progetto_MidiFlux_ver_3_0) in formato PDF.

> [!WARNING]
> La licenza della documentazione **non** è condivisa con quella del progetto.

---

## Build del progetto

Il plugin Maven per la generazione del file `MANIFEST.MF` è già incluso nel progetto.

### Requisiti

- **JDK**: versione 25 o 26
- **Maven**: installato e configurato nel `PATH` (obbligatorio)

> [!IMPORTANT]
> Senza Maven correttamente configurato nel `PATH`, i comandi `mvn` riportati di seguito non funzioneranno.

### Clonazione della repository

```bash
git clone https://github.com/zimip/midiflux.git
cd midiflux
```

---

## Compilazione ed esecuzione

### Generazione JAR

```bash
mvn package
```

La build completata sarà disponibile nella cartella `/target/` presente nella root del progetto.

### Esecuzione della classe Main

```bash
mvn compile exec:java -Dexec.mainClass="net.zimi.midiflux.Main"
```

Verrà eseguita la classe `Main` senza generare alcun artefatto.

### Alternativa con Releases

Scaricare il Jar precompilato dalla pagina delle [releases](https://github.com/zimip/midiflux/releases/tag/midiflux):

```bash
java -jar midiflux.jar
```
