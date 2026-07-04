# Getting Started (Zero Assumptions)

This guide assumes nothing except a terminal. Ten minutes here saves hours later.

## 1. Install a JDK

You need JDK **21 or newer** (the project compiles to Java 21 bytecode).

- macOS: `brew install temurin` (or download Eclipse Temurin from adoptium.net)
- Check it worked:

  ```bash
  java -version   # should print 21 or higher
  ```

You do **not** need to install Maven. The repo ships the Maven wrapper (`./mvnw`), which downloads the right Maven version on first run.

## 2. Pick an IDE

IntelliJ IDEA Community Edition (free) is the standard choice for Java. Open the repo folder; IntelliJ detects `pom.xml` and imports everything. Two things to verify:

- **File → Project Structure → SDK** points at your JDK 21+.
- You can right-click any test file and choose "Run" — the green-arrow workflow is faster than the terminal for single tests.

VS Code with the "Extension Pack for Java" also works fine.

## 3. Prove the Project Works

From the repo root:

```bash
./mvnw verify
```

First run downloads dependencies (a few minutes); afterwards it is fast. It should end with `BUILD SUCCESS`. If it doesn't, the two usual causes:

- **Wrong Java version** — `error: release version 21 not supported` means your `java -version` is older than 21.
- **`JAVA_HOME` mismatch** — the wrapper uses `JAVA_HOME` if set. `echo $JAVA_HOME` and make sure it points at the same JDK.

## 4. The Core Loop: Red → Green

Every wired exercise in this repo works the same way:

1. **Find the test.** Exercise `JAVA-02` lives in `src/test/java/.../exercises/java02/AuthorizationStateMachineTest.java`. The folder name is always the lowercase exercise id.
2. **Delete the `@Disabled(...)` line** at the top of the test class. That line is what keeps the build green before you start.
3. **Run it and watch it fail (RED):**

   ```bash
   ./mvnw test -Dtest=AuthorizationStateMachineTest
   ```

4. **Open the matching stub** in `src/main/java/.../exercises/java02/` and replace the `throw new UnsupportedOperationException("TODO ...")` with real code.
5. **Re-run until GREEN.** The test is the answer key — when it passes, your solution is correct by definition.

Stuck? Use the [progressive hints](hints.md): one hint at a time.

## 5. How to Read a Failing Test

The Maven output for a failure looks scary but has only three parts that matter:

```text
[ERROR] Failures:
[ERROR]   AuthorizationStateMachineTest.receivedToApprovedIsAllowed:25
          expected: APPROVED
           but was: RECEIVED
```

- **Which test** failed (`receivedToApprovedIsAllowed`, line 25) — open it and read what it asserts.
- **Expected vs actual** — the single most useful pair of lines in the output.
- **The stack trace** (for exceptions rather than assertions) — read from the top; the first line mentioning *your* package (`com.nick.javafundamentals`) is almost always where to look.

An **assertion failure** means your code ran but produced the wrong answer. An **exception** means your code (or a stub you have not implemented yet) blew up before it could answer. `UnsupportedOperationException: TODO ...` simply means "you haven't implemented this method yet."

## 6. A Note on Speed

`./mvnw verify` runs everything. While working one exercise, run only its test:

```bash
./mvnw test -Dtest=MoneyTest
```

Or run it from the IDE, which is faster still and gives you a debugger.

## 7. Where to Go Now

- Never written Java before, or rusty: start with the [Beginner On-Ramp](../exercises/00-beginner-onramp.md).
- Comfortable with Java, new to this repo: read the worked example `exercises/java01/` (code, test, and the matching note in `notes/`), then follow the [difficulty ladder](../exercises/README.md#difficulty-ladder).
- Interview in two weeks: the Suggested First Track in the top-level README, plus the [war-room drills](../exercises/09-production-war-rooms.md) and [what interviewers expect](../interview/what-interviewers-expect.md).
