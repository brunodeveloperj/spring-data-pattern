package com.mds.data.helper;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.NoArgsConstructor;

/**
 * Utility for extracting secret values from file paths or plain-text strings.
 *
 * <p>If the provided value is a valid file path, the file contents are read
 * and returned as the secret. Otherwise, the value itself is returned as-is.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
@NoArgsConstructor(access = PRIVATE)
public class SecretExtractorHelper {

  /**
   * Tests if the provided secret is an existing file. If it's a file, the contents are read and assumed as the password. Otherwise, we assume the password was passed in plain text.
   *
   * @param secret Path to a file containg the password or the password itself.
   * @return The password to be used for connection.
   */
  public static String extractSecretValue(String secret) {
    Path secretPath = Path.of(secret);
    try {
      return Files.readString(secretPath);
    } catch (IOException e) {
      return secret;
    }
  }

}
