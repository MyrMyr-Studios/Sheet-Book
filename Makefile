.PHONY: all frontend backend down clean

all:
	docker compose up app --build --detach
	docker compose up web --detach

frontend:
	docker compose up web --detach

backend:
	docker compose up app --build --detach

down:
	docker compose down

clean:
	docker compose down --volumes --rmi all