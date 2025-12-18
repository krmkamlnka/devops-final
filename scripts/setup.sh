#!/usr/bin/env bash
set -euo pipefail

LOG_DIR="/opt/devops-project/scripts"
LOG_FILE="${LOG_DIR}/setup_$(date +%F_%H-%M-%S).log"

mkdir -p "$LOG_DIR"

# Всё, что выводится в консоль, дублируем в лог
exec > >(tee -a "$LOG_FILE") 2>&1

echo "=== Setup started at $(date) ==="

echo "[1/4] Updating system..."
sudo apt-get update -y
sudo apt-get upgrade -y

echo "[2/4] Installing packages: Git, Java 17, curl, unzip..."
sudo apt-get install -y git curl unzip ca-certificates gnupg lsb-release openjdk-17-jdk

echo "[3/4] Installing Gradle..."
# Ubuntu repos sometimes have old Gradle; we'll install via SDKMAN-less simple approach using apt if present.
# For exam purposes, apt is ok; if it's too old later, we'll switch to wrapper-only workflow in the project.
sudo apt-get install -y gradle || true

echo "[4/4] Version checks..."
echo "git:      $(git --version)"
echo "java:     $(java -version 2>&1 | head -n 1)"
echo "javac:    $(javac -version)"
echo "gradle:   $(gradle -v 2>/dev/null | head -n 1 || echo 'gradle not found (ok if project uses ./gradlew)')"

echo "=== Setup finished at $(date) ==="
echo "Log saved to: $LOG_FILE"
