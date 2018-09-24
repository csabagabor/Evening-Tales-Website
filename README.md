# Bedtime stories websites for kids with Spring Boot REST API + PostgreSQL hosted on Heroku
### About
- This is a small application which serves a new bedtime story for kids every day.
- Currently it hosts a total of 60 stories so new stories won't be available after 24 Nov 2018. 
- Every story can be rated by users and all the average ratings are stored in a `PostgreSQL` database. The frontend communicates with the backend with a `REST` api.
### Features
- `Spring Boot` backend with REST API hosted on HEROKU
- frontent in `JS` + `jQuery` + `Bootstrap 4`
- `PostgreSQL` database(contains only 1 table for the avg. ratings ->> stories are stored in a `json` file)
### How to build/run
1. first clone the project
2. after creating a heroku account, follow [this](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku) guide to add a new Heroku repo
3. create a new PostgreSQL database with `heroku addons:create heroku-postgresql` in `Heroku CLI`
4. to create a new table type `heroku pg:psql` in the `CLI` and then copy this query:
```sql
CREATE TABLE TaleRating (
  id              SERIAL PRIMARY KEY,
  date_added         date NOT NULL,
  rating REAL NOT NULL,
  nr_rating INT NOT NULL
);
```
5. Now, you have your table but it is empty. You have to add data to it. The next script will initialise rating, nr_rating with 0 and the date will be autogenerated. Just change the date interval to your needs:
```sql
INSERT INTO TaleRating (date_added , rating, nr_rating)
SELECT date::date, 0, 0
from generate_series(
  '2018-09-16'::date,
  '2018-09-20'::date,
  '1 day'::interval
) date;
```
6. Great. So far so good. Now check if everything is ok:
```sql
 select * from TaleRating;
```
7. To run the app locally, you will have to configure it with your connection string. To get your `PostgreSQL` connection string, type `heroku config` in the `CLI` and copy the connection URL into the `ConnectionFactory` class located in the `com.example.demo.repository` package. Your code should look something like this:
```java
URI dbUri = null;
String databaseUrl = System.getenv("DATABASE_URL");
if(databaseUrl == null){
            //local setup
    dbUri = new URI("postgres://jaqyspwadaepgk:f74d0f8dbee50d751e8d9095efb5e62ed5e07df76c7681c6e142da0311a5fa1c@ec2-23-23-253-106.compute-1.amazonaws.com:5432/d1c92ljjeq05k1");
        }
else{
     dbUri = new URI(databaseUrl);
 }
```
8. Change the `domain` variable in the `javascipt` files located in `website/js` folder to your own Heroku app's URL(or `localhost` if you want to test it locally).
9. You can now test the application either locally, or remotely on Heroku.
### How to add new stories/tales
1. to generate the raw content of `tales.json` you can run this simple `Java` code then copy its output:
```java
LocalDate startDate = LocalDate.of(2018, 11, 21);
LocalDate endDate = LocalDate.of(2019, 9, 15);
StringBuilder builder = new StringBuilder();
for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)){
			builder.append("\n  \""  + date + "\": { \n " +
			    "  \"title\": \"\", \n " +
			    "  \"description\": \"\" \n " +
			    " },"
					);		   
		}
System.out.print(builder.substring(0, builder.length()-1));//remove comma at the end
```
2. Before inserting the new story into the title/description field, you have to escape some characters in it to be a valid `json`; a good tool for this is [here](https://www.freeformatter.com/json-escape.html)
### Future improvements
- for faster retrieval of data from the database, an in-memory database needs to be implemented like `Redis`; the same applies to the `json` file which contains the stories(this file should be mapped into memory)
- the application is not built with security in mind, so anyone can send as many ratings as he wants with any simple tool like `Postman`(note that the frontend itself is secured in this sense, because a single user cannot send more than 1 rating/story only if the person clears his cookies/local storage)
- more stories need to be added
