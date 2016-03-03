# Parse Push Notification Sample Code

This sample code demonstrates a working example of push notifications with Parse! This source code was originally created by Vishal Kapoor as part of our Jan 2015 Android bootcamp. The code has been updated significantly by Roger Hu on March 3rd, 2016 to reflect Parse Server.

See our [configuring parse server push notifications guide](https://guides.codepath.com/android/Configuring-a-Parse-Server#enabling-push-notifications) for more details.

### Setup

1. Update `res/strings.xml` and update your GCM sender ID.

2. Update your `PARSE_CLOUD_SERVER_URL` in `MainApp.java`.

3. Update your `PARSE_APP_ID` in `MainApp.java`.

Make sure you have your Parse cloud server configured with the `pushChannelTest` function:

```javascript
Parse.Cloud.define('pushChannelTest', function(request, response) {

  // request has 2 parameters: params passed by the client and the authorized user
  var params = request.params;
  var user = request.user;

  // extract out the channel to send
  var action = params.action;
  var message = params.message;
  var customData = params.customData;

  // use to custom tweak whatever payload you wish to send
  var pushQuery = new Parse.Query(Parse.Installation);
  pushQuery.equalTo("deviceType", "android");

  var payload = {"data": {
      "alert": message,
      "action": action,
      "customdata": customData}
                };

  // Note that useMasterKey is necessary for Push notifications to succeed.

  Parse.Push.send({
  where: pushQuery,      // for sending to a specific channel                                                                                                                                 data: payload,
  }, { success: function() {
     console.log("#### PUSH OK");
  }, error: function(error) {
     console.log("#### PUSH ERROR" + error.message);
  }, useMasterKey: true});

  response.success('success');
});

```

You can verify that the Parse Cloud function works by using this curl command (make sure the application ID matches):

```bash
 curl -X POST -H "X-Parse-Application-Id: myAppId" -H "Content-Type: application/json" -d '{"action": "SEND_PUSH", "message": "hello", "customData": "My message"}' https://yourappname.herokuapp.com/parse/functions/pushChannelTest
```
