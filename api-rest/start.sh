docker-compose down

docker build -t api-rest:latest .

docker compose up --build --force-recreate --remove-orphans