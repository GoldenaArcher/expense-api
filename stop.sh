#!/bin/bash

set -e

error_exit() {
    echo "Error occurred. Exiting script." >&2
    exit 1
}

echo "Stopping Docker Compose services..."
docker compose down --volumes --remove-orphans || error_exit

echo "Removing unused Docker resources (optional cleanup)..."
docker system prune --volumes -f || error_exit

echo "Docker Compose services have been stopped and cleaned up."
