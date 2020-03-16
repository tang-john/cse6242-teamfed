# TeamFed

This is an Angular WebApp that is deployed in a Docker Image

## Development server (Local Run without Docker)

Run `npm install` to install all the dependencies.  
Run `npm start` for a dev server. Navigate to `http://localhost/#/landingPage`


## Docker server (Locally run latest code using Docker)

Download the latest Docker from https://www.docker.com/products/docker-desktop.  
Run `docker build -t teamfed:dev .` to create a docker image from the Dockerfile.   
Run `docker run -p 8080:8080 <imageId>` to run the image. The imageId is the output from build/creating a docker image.  
Navigate to `http://localhost/#/landingPage`

## Deploy to Heroku (Prod Run)

Install the HerokuCLI from https://devcenter.heroku.com/articles/heroku-cli  
Run `heroku login` to create a session to your heroku account.  
Now you can sign into Container Registry `heroku container:login`.  

To Deploy:
* Run `heroku container:push web -a teamfed-project` to create a docker image and push it to our Heroku repository.  
* Run `heroku container:release web -a teamfed-project` to release the latest image and this will update the webapp.  
* Run `heroku logs --tail -a teamfed-project` to see the logs and output from interacting with the webapp.  

Navigate to `https://teamfed-project.herokuapp.com/#/landingPage` and watch the as the logs change and acknolegde the interaction with the webapp.
