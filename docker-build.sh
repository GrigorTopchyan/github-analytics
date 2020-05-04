#Replace # characters from github access token because github detects commited access token and invalidates it
sed -i 's/#//g' src/main/resources/application.properties

#Build github-analytics jar with maven
mvn clean install -DskipTests

#Create github-analytics image
docker build -t github-analytics .

#Create github-analytics-react image
cd github-analytics-react/
docker build -t github-analytics-react .

cd ../

#Run
docker-compose -f docker-compose.yml up

