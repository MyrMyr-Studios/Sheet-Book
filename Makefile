.PHONY: all frontend backend down clean debug-frontend debug-backend

all:
	docker compose up app --build --detach
	docker compose up web --build --detach

frontend:
	docker compose up web --build --detach

backend:
	docker compose up app --build --detach

debug-frontend:
	docker compose up web
	
debug-backend:
	docker compose up app

down:
	docker compose down

clean:
	docker compose down --volumes --rmi all