docker run -d --name jenkins -p 2024:2024 \
  -v $(pwd)/jenkins:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v $(pwd):/var \
  --restart unless-stopped \
  jenkins
