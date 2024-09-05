package com.iwomi.nofiaPay.core.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorageUtil {
    public static Path createSubFolder(Path path, String folderName) {
        try {
            return Files.createDirectories(path.resolve(folderName));
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't create the directory for dispose", ex);
        }
    }
}
