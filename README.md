<p align="center">
  <picture>
    <img src="./assets/logo.png" width="200">
  </picture>
</p>

# Repository pubblica – Progetto scolastico MidiFlux

## Nota importante

- Il progetto, incluse alcune parti della GUI, è stato sviluppato con il supporto di strumenti di Intelligenza Artificiale.
- Il design e la progettazione dell’interfaccia grafica sono stati definiti interamente dai membri del gruppo, senza utilizzo diretto di IA.
- Gli strumenti di IA sono stati utilizzati esclusivamente come supporto creativo e per il miglioramento estetico e visivo del progetto.

---

# Build del progetto

Il plugin Maven per la generazione del file `MANIFEST.MF` è già incluso nel progetto.

## Requisiti

- **JDK**: versione 25 o 26
- **Maven**: installato e configurato nel `PATH` (obbligatorio)

## Clonazione della repository

```bash
git clone https://github.com/zimip/midiflux.git
```

Entrare nella cartella del progetto:

```bash
cd MidiFlux
```

---

# Compilazione ed esecuzione

## Generazione JAR

```bash
mvn package
```

La build completata sarà disponibile nella cartella `/target/` presente nella root del progetto.

## Esecuzione della classe Main

```bash
mvn compile exec:java -Dexec.mainClass="net.zimi.midiflux.Main"
```

Verrà eseguita la classe `Main` senza generare alcun artefatto.
