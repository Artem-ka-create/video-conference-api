#!/bin/bash

# Ensure Git tracks our branch and the work tree is set correctly
git --work-tree=/app --git-dir=/app/.git checkout main

# Stash any local changes including untracked files
git --work-tree=/app --git-dir=/app/.git stash --include-untracked

# Pull the latest changes from Git
git --work-tree=/app --git-dir=/app/.git pull origin main

# Check for conflicts and favor the latest changes from the repository
if git --work-tree=/app --git-dir=/app/.git diff --name-only --diff-filter=U | grep -q '.'; then
    git --work-tree=/app --git-dir=/app/.git checkout --theirs .
    git --work-tree=/app --git-dir=/app/.git add -u
fi

# Apply stashed changes (if necessary and possible)
git --work-tree=/app --git-dir=/app/.git stash pop || true

# Clean up untracked files, excluding the JAR file
git --work-tree=/app --git-dir=/app/.git clean -fd -e video-conference-api-0.0.1-SNAPSHOT.jar

# Rebuild the application with Maven
mvn clean install

# Ensure the JAR file is executable
chmod +x target/video-conference-api-0.0.1-SNAPSHOT.jar

# Start the application
java -jar target/video-conference-api-0.0.1-SNAPSHOT.jar