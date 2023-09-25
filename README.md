# egt-digital task
## Running the application
### option 1
Navigate to <code>docker-compose.yaml</code> and execute <code>docker-compose up</code>. This will build the application and start all required services that it depends on.
### option 2
- start dependencies with <code>docker-compose.yaml</code> without **gateway-app**
- Import the project into an IDE -> build it using maven <code>mvn package</code>
- navigate to the target directory where the jar is and run <code>java -jar -Dspring.profiles.active=dev gateway.jar</code> **run application with dev profile!**
## Connecting to the DataBase
Using a db client of your preferred choice connect to : <code>jdbc:mysql://localhost:3306</code> with <code>username & password = root</code> (default values of mysql)
### querying the database
```
use gateway;

SELECT * FROM user_sessions
```
## Executing requests to the API
- **statistics API**
```
  curl --request GET \
  --url 'http://localhost:8080/public/v1/statistics?userId=238485' \
  --header 'Content-Type: application/json'
```
- **json_api find**
```
curl --request GET \
  --url http://localhost:8080/public/v1/json_api/find \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/2023.5.8' \
  --data '{
 "requestId": "b89577fe-8c37-4962-8af3-7cb8a21q90",
 "sessionId": 47966003032113150
}'
```
_requestId_ - String

_sessionId_ - Number(integer)
- **json_api insert**
```
curl --request POST \
  --url http://localhost:8080/public/v1/json_api/insert \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/2023.5.8' \
  --data '{
 "requestId": "2",
 "timestamp": 158721,
 "producerId": "1237574",
 "sessionId": 47913150
}'
```
_requestId_ - String

_sessionId_ - Number(integer)

_timestamp_ - Number(long)

_producerId_ - String
- **xml_api/command**
```
curl --request POST \
  --url http://localhost:8080/public/v1/xml_api/command \
  --header 'Content-Type: application/xml' \
  --header 'User-Agent: insomnia/2023.5.8' \
  --data '<command id="2265562" >
<enter session="2245452" >
<timestamp>1586335186721</timestamp>
<player>238485</player>
</enter>
</command>
'
```
_id_ - String

_timestamp_ - Number(long)

_player_ - Number(integer)

_session_ - String

```
curl --request POST \
  --url http://localhost:8080/public/v1/xml_api/command \
  --header 'Content-Type: application/xml' \
  --header 'User-Agent: insomnia/2023.5.8' \
  --data '<command id="zxc4">
<get session="zxc"/>
</command>
'
```
_id_ - String

_session_ - String
