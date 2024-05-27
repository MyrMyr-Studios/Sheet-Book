all:
	docker compose up app --build --detach

down:
	docker compose down

clean:
	docker compose down --volumes --rmi all