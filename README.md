## ADAL Library Test Application

#### Environment ariables

You will need to set `CLIENT_ID` and `TENANT_ID` environment variables to run the app. Both values can be found in 1Password under "Azure AD Test" in the "Roam" vault.

#### Running the app

Once the app is cloned, navigate to the root directory and execute the command `./gradlew bootRun`.

#### Generating a token

Once the server is running, make a `GET` request to `http://localhost:8080/auth?username=<username>&password=<password>` (username and password can be found in 1Password). 