# findpck

> ⚡ **A fast terminal CLI to search Maven Central and copy Gradle & Maven dependencies straight to your clipboard.**

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Picocli](https://img.shields.io/badge/CLI-Picocli%204.7-brightgreen)](https://picocli.info/)
[![Maven Central](https://img.shields.io/badge/Repository-Maven%20Central-C71A36)](https://search.maven.org/)

Stop opening your browser every time you need a dependency coordinate. `findpck` brings Maven Central search right into your terminal and generates ready-to-paste snippets for Gradle and Maven with a single command.

---

## ✨ Features

- 🔍 **Instant Maven Central Search**: Query packages and libraries without leaving your command line.
- 📋 **Clipboard Integration**: Automatically copy the dependency snippet directly to your clipboard.
- 📦 **Dual Build Tool Support**: Generates snippets for both **Gradle (Kotlin DSL)** and **Maven (`pom.xml`)**.
- 📅 **Up-to-Date Metadata**: Inspect the latest version and the exact release date at a glance.
- 🎯 **Targeted Copying**: Pick and copy any result by index (`--copy-index`).

---

## 📋 Prerequisites

- **Java 17** or higher installed (`java -version`)
- Git (optional, for cloning)

---

## 🚀 Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/samarthchanaliya/findpck.git
cd findpck
```

### 2. Build the executable

Use the included Gradle wrapper to build the distribution:

**Linux / macOS:**
```bash
./gradlew installDist
```

**Windows (PowerShell / CMD):**
```powershell
.\gradlew.bat installDist
```

The compiled binaries will be generated under `build/install/findpck/bin/`.

### 3. Add to PATH (Optional, recommended)

To run `findpck` from anywhere in your terminal:

- **Linux / macOS:**
  Add this to your `~/.bashrc` or `~/.zshrc`:
  ```bash
  export PATH="$PATH:/path/to/findpck/build/install/findpck/bin"
  ```
  Or create a symlink:
  ```bash
  sudo ln -s /path/to/findpck/build/install/findpck/bin/findpck /usr/local/bin/findpck
  ```

- **Windows:**
  Add the full path to `build\install\findpck\bin` to your User `PATH` environment variable, or run:
  ```powershell
  [Environment]::SetEnvironmentVariable("Path", $env:Path + ";$pwd\build\install\findpck\bin", [EnvironmentVariableTarget]::User)
  ```

---

## 💻 Usage

```bash
findpck [OPTIONS] <query>
```

> **Note:** If you haven't added the binary to your `PATH`, you can also run it via Gradle:
> ```bash
> ./gradlew run --args="<query> [OPTIONS]"
> ```

---

## ⚙️ Options & Flags

| Flag | Long Option | Description | Default |
| :--- | :--- | :--- | :--- |
| `<query>` | *(positional)* | The package name or keyword to search for *(required)*. | — |
| `-c` | `--copy` | Copy dependency snippet to clipboard (default: top result). | `false` |
| | `--copy-index=<index>` | 1-based index of the result to copy. | `1` |
| `-f` | `--format=<format>` | Format to copy: `gradle` (Kotlin DSL) or `maven`. | `gradle` |
| `-l` | `--limit=<limit>` | Number of results to display. | `3` |
| `-h` | `--help` | Show help message and exit. | — |
| `-V` | `--version` | Print version information and exit. | — |

---

## 📖 Examples

### 1. Basic Search
Search for a library and display the top results:
```bash
findpck jackson
```

**Sample Output:**
```text
Found 1948 packages matching "jackson" (showing top 3)

[1] org.openidentityplatform.commons.json-ref : jackson
    Latest: 2.3.0 | Updated: 2025-06-19

  Maven:
    <dependency>
        <groupId>org.openidentityplatform.commons.json-ref</groupId>
        <artifactId>jackson</artifactId>
        <version>2.3.0</version>
    </dependency>

  Gradle (Kotlin DSL):
    implementation("org.openidentityplatform.commons.json-ref:jackson:2.3.0")

[2] community.flock.wirespec.integration : jackson
    Latest: 0.14.11 | Updated: 2025-05-09

  Maven:
    <dependency>
        <groupId>community.flock.wirespec.integration</groupId>
        <artifactId>jackson</artifactId>
        <version>0.14.11</version>
    </dependency>

  Gradle (Kotlin DSL):
    implementation("community.flock.wirespec.integration:jackson:0.14.11")
```

---

### 2. Limit the Number of Results (`-l`)
Show 5 results instead of default 3:
```bash
findpck lombok -l 5
```

---

### 3. Copy Top Result to Clipboard (`-c`)
Copies the top result formatted as a Gradle Kotlin DSL dependency:
```bash
findpck picocli -c
```
*Clipboard contents:*
```kotlin
implementation("info.picocli:picocli:4.7.7")
```

---

### 4. Copy as Maven Dependency (`-f maven`)
Copies the top result formatted as a Maven XML block:
```bash
findpck junit-jupiter -c -f maven
```
*Clipboard contents:*
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.12.0</version>
</dependency>
```

---

### 5. Copy a Specific Result by Index (`--copy-index`)
If the 2nd result is the one you need:
```bash
findpck jackson-databind -c --copy-index 2 -f gradle
```

---

## 🛠️ Development

### Run tests
```bash
./gradlew test
```

### Build Distribution Archive (.zip / .tar)
```bash
./gradlew distZip
```
The archives will be located in `build/distributions/`.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
