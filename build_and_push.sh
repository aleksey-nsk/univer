mvn clean package
cp --force target/demo-0.0.1-SNAPSHOT.jar docker/prod/services/backend/
docker build docker/prod/services/backend/ -t alexz2/university_back:1.0.0
docker push alexz2/university_back:1.0.0

docker build docker/prod/services/frontend/ -t alexz2/university_front:1.0.0
docker push alexz2/university_front:1.0.0
