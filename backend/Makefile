run-application:
	docker-compose -f docker-compose-run-application.yaml down
	docker-compose -f docker-compose-run-application.yaml stop
	docker-compose -f docker-compose-run-application.yaml build --force-rm
	docker-compose -f docker-compose-run-application.yaml up

run-database:
	docker-compose -f docker-compose-run-database.yaml down
	docker-compose -f docker-compose-run-database.yaml stop
	docker-compose -f docker-compose-run-database.yaml build --force-rm
	docker-compose -f docker-compose-run-database.yaml up

run-api:
	docker build -t eventify-api -f Dockerfile .
	docker run -e PORT=8080 --env-file .env	-p 8080:8080 --rm -it  eventify-api

run-application:
	docker-compose -f docker-compose-run-application.yaml down
	docker-compose -f docker-compose-run-application.yaml stop
	docker-compose -f docker-compose-run-application.yaml build --force-rm
	docker-compose -f docker-compose-run-application.yaml up

push-img-base-dev:
	docker tag backend-api:latest us-east1-docker.pkg.dev/symmetric-lore-437617-v9/eventify-backend/backend-api:latest
	docker push us-east1-docker.pkg.dev/symmetric-lore-437617-v9/eventify-backend/backend-api:latest