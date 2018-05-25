# RAHi-app
API Documentation
Updating location and Bus parameters
URL: "http://13.58.183.35/api/update_location_and_parameters/"

Method: POST

for storing a known location

Json object Expected:
{
  "key": "70b66a89929e93416d2ef535893ea14da331da8991cc7c74010b4f3d7fabfd62",
  "known_location": true,
  "place_name": "TextField",
  "time_recorded": "DateTimeField",
  "bus_number": "TextField",
  "latitude": "",
  "longitude": "",
  "fuel": "",
  "speed": "",
  "distance": ""
}
example:

{
	"key": "70b66a89929e93416d2ef535893ea14da331da8991cc7c74010b4f3d7fabfd62",
	"bus_number" : "UP53AT8319",
	"time_recorded":"05/07/2017 11:59:46",
	"known_location": "true",
	"place_name": "Cantt Varanasi"
}
If successful, Json response:
{
"status": "true"
}
If not successful, Json response:
{
"status": "error"
}
for updating location and parameters of a Bus
Json object Expected:
{
  "key": "70b66a89929e93416d2ef535893ea14da331da8991cc7c74010b4f3d7fabfd62",
  "known_location": "false",
  "place_name": "",
  "time_recorded": "DateTimeField",
  "bus_number": "TextField",
  "latitude": "FloatField",
  "longitude": "FloatField",
  "fuel": "FloatField",
  "speed": "FloatField",
  "distance": "FloatField"
}
example:

{
	"key": "70b66a89929e93416d2ef535893ea14da331da8991cc7c74010b4f3d7fabfd62",
  "known_location": "false",
  "place_name": "",
	"bus_number" : "UP53AT8319",
	"time_recorded": "05/07/2017 11:59:46",
	"latitude": "25.17",
	"longitude": "83.0",
	"fuel":"43",
	"speed":"32",
	"distance":"56"
}
If successful, Json response:
{
  "status": "true"
}
If not successful, Json response:
{
  "status": "error"
}
Getting current data of a Bus
URL: "http://13.58.183.35/api/get_bus_data_current_time/"

Method: POST

Json object Expected:
{
  "key": "32c25a275fdf9df2668560732691af2a95c53429605c34ea989fd359",
  "bus_number": "TextField"
}
example:

{
	"key": "32c25a275fdf9df2668560732691af2a95c53429605c34ea989fd359",
	"bus_number" : "UP53AT8319"
}
If successful, Json response:
{
  "latitude": "FloatField",
  "longitude": "FloatField",
  "speed": "FloatField",
  "fuel": "FloatField",
  "distance": "FloatField",
  "time_recorded": "DateTimeField",
  "status": "true"
}
If not successful, Json response:
{
  "status": "error"
}
Getting Data of Bus from a given time
URL: "http://13.58.183.35/api/get_bus_data_from_time/"

Method: POST

Json object Expected:
{
  "key": "bd0e7468203f76439a9d4cb3d29a2403cfe49e41e781813e0cdec392cf054dc9",
  "bus_number": "TextField",
  "from_time": "DateTimeField",
  "to_time": "DateTimeField"
}
example:

{
	"key": "bd0e7468203f76439a9d4cb3d29a2403cfe49e41e781813e0cdec392cf054dc9",
	"bus_number" : "UP53AT8319",
	"from_time":"05/07/2017 11:59:46",
	"to_time":"06/07/2017 11:59:46"

}
If successful, Json response:
{
  "status": "true",
  "number_of_locations": "IntegerField",
  "locations":{
    "0" :{
        "latitude": "FloatField",
        "longitude": "FloatField",
        "time_recorded": "DateTimeField"
      },
    "1":{
      "latitude": "FloatField",
      "longitude": "FloatField",
      "time_recorded": "DateTimeField"
    },
    "2":{
      "so on.."
    }
  },
  "number_of_known_locations": "IntegerField",
  "known_locations":{
    "0":{
      "place_name": "TextField",
      "time_recorded": "DateTimeField"
    },
    "1":{
      "place_name": "TextField",
      "time_recorded": "DateTimeField"
    },
    "2":{
      "so on.."
    }
  },
  "number_of_parameters": "IntegerField",
  "speed_data":{
    "0":{
      "speed": "FloatField",
      "time_recorded": "DateTimeField"
    },
    "1":{
      "so on.."
    },
  },
  "fuel_data":{
    "0":{
      "fuel": "FloatField",
      "time_recorded": "DateTimeField"
    },
    "1": {
      "so on.."
    },
  },
  "distance_data":{
    "0": {
      "distance": "FloatField",
      "time_recorded": "DateTimeField"
    },
    "1": {
      "so on.."
    }
  }
}
If not successful, Json response:
{
  "status": "error"
}
Getting current data of a Bus
URL: "http://13.58.183.35/api/web/get_fuel_data/"

Method: POST

Json object Expected: None
response

{
  "bus_number":{
    "0":{
      "time_recorded":"DateTimeField",
      "fuel":"FloatField"
    },
    "1":{
      "time_recorded":"DateTimeField",
      "fuel":"FloatField"
    },
  }
  "bus_number":{
    "0":{
      "time_recorded":"DateTimeField",
      "fuel":"FloatField"
    }
    "so on"
  }
}
example response:

{
  "UP53AT8319":{
    "0": {
      "time_recorded": "2017-07-05 09:54:16+00:00",
      "fuel": 43.0
      },
    "1": {
      "time_recorded": "2017-07-05 11:59:46+00:00",
      "fuel": 41.0
      }
    },
 "UP34AT8965":{
   "0":{
     "time_recorded": "2017-07-05 09:34:16+00:00",
     "fuel": 45.3
   }
 }
}
Note: user should be authenticated.

## Getting Data of Bus of a user
* URL: "http://13.58.183.35/api/get_bus_data_user/"
* Method: POST

  * Json object Expected:
```json
{
  "key": "32c25a275fdf9df2668560732691af2a95c53429605c34ea989fd359",
  "username": "CharField"
  "password": "CharField"
}
example:

{
  "key": "32c25a275fdf9df2668560732691af2a95c53429605c34ea989fd359",
  "username": "admin"
  "password": "aashu"
}
If successful, Json response:
If username and password are correct:
{
  "buses":{
    "0":{
      "bus_number":"TextField",
      "driver":"CharField"
    },
    "1":{
      "bus_number":"TextField",
      "driver":"CharField"
    }
   }
}
example response:

{
  "buses":{
    "0": {
      "bus_number": "UP53AT8319",
      "driver":"suresh"
      },
   "1":{
     "bus_number": "UP34AT8965",
     "driver":"ramesh"
  	}
   }
 }
If username and password are wrong:
{
Invalid login details supplied.
}
If not successful, Json response:
{
  "status": "error"
}
Getting Data of Bus of a user
URL: "http://13.58.183.35/api/get_bus_data_user/"

Method: POST

Json object Expected:
{
  "key": "32c25a275fdf9df2668560732691af2a95c53429605c34ea989fd359",
  "username": "CharField"
  "password": "CharField"
}
example:

{
  "key": "32c25a275fdf9df2668560732691af2a95c53429605c34ea989fd359",
  "username": "admin"
  "password": "aashu"
}
If successful, Json response:
If username and password are correct:
{
  "buses":{
    "0":{
      "bus_number":"TextField",
      "driver":"CharField"
    },
    "1":{
      "bus_number":"TextField",
      "driver":"CharField"
    }
   }
}
example response:

{
  "buses":{
    "0": {
      "bus_number": "UP53AT8319",
      "driver":"suresh"
      },
   "1":{
     "bus_number": "UP34AT8965",
     "driver":"ramesh"
  	}
   }
 }
If username and password are wrong:
{
Invalid login details supplied.
}
If not successful, Json response:
{
  "status": "error"
}
