local-environment:
	docker-compose -f docker-compose-run-local.yaml down
	docker-compose -f docker-compose-run-local.yaml stop
	docker-compose -f docker-compose-run-local.yaml build --force-rm
	docker-compose -f docker-compose-run-local.yaml up

run-docker:
	docker build -t eventify-api -f Dockerfile .
	docker run -e PORT=8080 --env-file .env	-p 8080:8080 --rm -it  eventify-api 