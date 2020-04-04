const path = require("path");
const express = require("express");
var cors = require('cors')
const request = require('request');
const bodyParser = require('body-parser')
const app = express();

app.use(cors())
app.use(bodyParser.json());


app.use(express.static(__dirname + '/src'));

app.get('/*', function(req,res){
res.sendFile(path.join(__dirname, 'src', 'index.html'))
});

app.post('/mlWebservice', function (req, res) {
    console.log("Request Body: " +JSON.stringify(req.body))
    var options = {
        'method': 'POST',
        'url': 'https://ussouthcentral.services.azureml.net/workspaces/62ecd3c0feeb40cd9081f5c943c0c815/services/b8a0b8a6f66e477194880d34b7817178/execute?api-version=2.0&details=true',
        'headers': {
          'Authorization': 'Bearer D0GlhssiqSt3GhG6ajq7W0ynBHhaDFWiL2cARJuL32y2GKZ17beULa+f+TzO5C2T/HOlsE4JoYHOYFxSDZkVyg==',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(req.body)
      
      };
      request(options, function (error, response) { 
        if (error) throw new Error(error);
        console.log("ML Response Body: " + response.body);
        res.json(response.body);
      });
  })

  
// Start the app by listening on the default Heroku port
app.listen(process.env.PORT || 8080);