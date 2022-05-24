import 'dart:convert';

WeatherData weatherDataFromJson(String str) =>
    WeatherData.fromJson(json.decode(str));

class WeatherData {
  WeatherData({
    this.name,
    this.icon,
    this.description,
    this.temp,
    this.tempMin,
    this.tempMax,
    this.sunrise,
    this.sunset,
  });

  WeatherData.fromJson(dynamic json) {
    name = json['name'];
    icon = json['icon'];
    description = json['description'];
    temp = json['temp'];
    tempMin = json['tempMin'];
    tempMax = json['tempMax'];
    sunrise = json['sunrise'];
    sunset = json['sunset'];
  }

  String? name;
  String? icon;
  String? description;
  String? temp;
  String? tempMin;
  String? tempMax;
  String? sunrise;
  String? sunset;
}
