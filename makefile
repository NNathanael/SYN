all:
	set DEBUG=myapp:* & npm start
install:
	npm install
	npm install mongodb
	npm install mongoose
	npm install jquery --save
