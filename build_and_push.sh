# Backend image
mvn clean package
cp --force target/demo-0.0.1-SNAPSHOT.jar docker/build/backend/
docker build docker/build/backend/ -t alexz2/university_back:1.0.0
docker push alexz2/university_back:1.0.0

# Frontend image
docker build docker/build/frontend/ -t alexz2/university_front:1.0.0
docker push alexz2/university_front:1.0.0
