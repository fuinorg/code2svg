package org.fuin.code2svg.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.fuin.utils4j.Utils4J;
import org.fuin.utils4j.fileprocessor.FileHandler;
import org.fuin.utils4j.fileprocessor.FileHandlerResult;
import org.fuin.utils4j.fileprocessor.FileProcessor;

public class GitExample {

    private static List<String> commits(final Git git, final Repository repo, final File repoDir, final File file) {
        final List<String> commits = new ArrayList<>();
        try {
            final String relativePath = Utils4J.getRelativePath(repoDir, file);
            final LogCommand logCommand = git.log().add(repo.resolve(Constants.HEAD)).addPath(relativePath);

            for (final RevCommit revCommit : logCommand.call()) {
                commits.add(revCommit.getName());
            }
        } catch (final IOException | GitAPIException ex) {
            throw new RuntimeException("Error reading git commit history of " + file, ex);
        }
        return commits;
    }

    private static void commit(final Git git, final String message, final File repoDir, final List<File> files) {
        try {
            for (final File file : files) {
                final String relativePath = Utils4J.getRelativePath(repoDir, file);
                git.add().addFilepattern(relativePath).call();
            }
            git.commit().setMessage(message).call();
        } catch (final GitAPIException ex) {
            throw new RuntimeException("Error git commit of " + files, ex);
        }
    }
        
    public static void main(String[] args) throws IOException, NoHeadException, GitAPIException {

        final List<File> files = new ArrayList<>();
        final File repoDir = new File("/home/developer/git/org.fuin.dsl.ddd");
        new FileProcessor(new FileHandler() {
            @Override
            public FileHandlerResult handleFile(final File file) {
                if (file.getName().endsWith(".ddd.svg")) {
                    files.add(file);
                }
                return FileHandlerResult.CONTINUE;
            }
        }).process(new File(repoDir, "doc"));

        try (final Git git = Git.open(repoDir)) {
            final Repository repo = git.getRepository();
            for (final File file : files) {
                final String relativePath = Utils4J.getRelativePath(repoDir, file);
                System.out.println(relativePath + " = " + commits(git, repo, repoDir, file));
            }
        }

    }

}
