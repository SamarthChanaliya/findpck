package com.github.samarthchanaliya;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testHelpOption() {
        Main app = new Main();
        CommandLine cmd = new CommandLine(app);
        int exitCode = cmd.execute("--help");
        assertEquals(0, exitCode);
    }

    @Test
    void testVersionOption() {
        Main app = new Main();
        CommandLine cmd = new CommandLine(app);
        int exitCode = cmd.execute("--version");
        assertEquals(0, exitCode);
    }

    @Test
    void testCommandLineCopyFlagParsing() {
        Main app = new Main();
        CommandLine cmd = new CommandLine(app);
        cmd.parseArgs("jackson", "-c", "-f", "maven", "--copy-index", "2");

        // Verify that arguments were parsed without error
        assertNotNull(cmd);
    }
}
