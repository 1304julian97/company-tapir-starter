## Quick start

## Links:

* [tapir documentation](https://tapir.softwaremill.com/en/latest/)
* [tapir github](https://github.com/softwaremill/tapir)
* [bootzooka: template microservice using tapir](https://softwaremill.github.io/bootzooka/)
* [sbtx wrapper](https://github.com/dwijnand/sbt-extras#installation)


# STEPS TO RUN

* Under folder `docker` run `docker-compose up`
  * This command will start database and run de script located under `sql` folder
* `sbt run` or go to `Main` file and run using Intellij
* if you want to check via command line the data base:
  * Run `docker ps`
  * Look up the name of the container and take it.
  * run `docker exec -it docker_db_1 bash` (keep in mind docker_db_1 is the name of the image, replace according to your needs)
  * Inside docker bash run: `psql -U docker -d myimdb`