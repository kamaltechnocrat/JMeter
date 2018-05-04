Running Watij require some setting in src.test_Suite.deployment_path.properties to be changed accordingly

1. workSpacePath "Critical" Mostly PreTest02NG used and any other file upload Utils
default: workSpacePath=C:\\workspace\\grantium_qa_watij

2. siteBase "Critical" to Launch G3 App
default: siteBase=http://localhost:8080/grantium/

3. ntUserName "Nice to Change" used mainly for Notification to setup system Setting in PreTest
default: ntUserName=yourName

4. reportBaseURL "Optional" unless you will be configuring and Launching Reports using Watij
default: reportBaseUrl=http://localhost:8080/grantium_reporting_crxi/
