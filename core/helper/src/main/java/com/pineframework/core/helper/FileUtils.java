package com.pineframework.core.helper;

import io.vavr.control.Try;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static com.pineframework.core.helper.StringUtils.requireNotEmpty;
import static java.lang.Thread.currentThread;
import static java.util.Objects.requireNonNull;

/**
 * @author Saman Alishirishahrbabak
 */
public final class FileUtils {

    private FileUtils() {
    }

    /**
     * replace dot with slash then return new string.
     *
     * @param packageName the package string
     * @return new string that replaced dot with slash
     */
    public static String packageToPath(String packageName) {
        requireNotEmpty(packageName);
        return packageName.replace('.', '/');
    }

    public static String pathToPackage(Path path) {
        Objects.requireNonNull(path);
        return path.toString().replace('/', '.');
    }

    public static URL getResourcesUnderPackage(String packageName) {
        requireNotEmpty(packageName);
        return currentThread().getContextClassLoader().getResource(packageToPath(packageName));
    }

    public static Path getPathsUnderPackage(String packageName) {
        requireNotEmpty(packageName);
        return Try.of(() -> Paths.get(getResourcesUnderPackage(packageName).toURI())).get();
    }

    public static Stream<String> walkThrowPackage(String packageName) {
        return Try.of(() -> Files.walk(getPathsUnderPackage(packageName)).map(path -> pathToPackage(path))).get();
    }

    public static URI toUri(String path) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL dir = classLoader.getResource(path);
        requireNonNull(dir);
        return Try.of(dir::toURI).get();
    }

    public static byte[] readFile(String path) {
        File file = new File(path);

        return Try.withResources(() -> new FileInputStream(file))
                .of(inputStream -> {
                    byte[] bytes = new byte[(int) file.length()];
                    inputStream.read(bytes);
                    return bytes;
                }).get();
    }

    public static void createFile(String name, byte[] content) {
        Try.withResources(() -> new FileOutputStream(new File(name)))
                .of(outputStream -> {
                    outputStream.write(content);
                    return outputStream;
                });
    }
}
