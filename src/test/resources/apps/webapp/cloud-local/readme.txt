Steps to run the hello world web example locally:

1. Build the docker image (if not built yet):
docker build -t helloworld-web:1.0 .

2. Run the docker image
docker run -p 50000:80 -d helloworld-web:1.0

3. Check it is up and running: open a browser and visit http://localhost:50000

4. Run the BrowserStack agent
./BrowserStackLocal --key <YOUR_ACCESS_KEY> --force-local

5. Run the test WebPageAndroidCloudLocalTest.java

