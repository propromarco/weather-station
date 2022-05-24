import 'dart:async';

import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:intl/intl.dart';
import 'package:http/http.dart' as http;
import 'dart:developer';

import 'widgets/date_row.dart';
import 'widgets/sunrise_sunset.dart';
import 'widgets/temperature.dart';
import 'widgets/WeatherData.dart';
import 'widgets/day_info.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _timeString = '';
  String _dateString = '';
  String _dayString = '';
  String _minString = '5°C';
  String _maxString = '18°C';
  String _actString = '18°C';
  String _sunriseString = '07:30Uhr';
  String _sunsetString = '18:30Uhr';
  String _description = '';
  String _icon = '01n';

  @override
  void initState() {
    initializeDateFormatting();
    _timeString = _formatTime(DateTime.now());
    _dateString = _formatDate(DateTime.now());
    _dayString = 'Troisdorf';
    Timer.periodic(const Duration(seconds: 1), (Timer t) => _getTime());
    Timer.periodic(const Duration(seconds: 5), (Timer t) => fetchData());
    super.initState();
  }

  Future<WeatherData> fetchData() async {
    var uri = Uri.parse('http://localhost:9090/api');
    var response = await http.get(uri);
    var weather = weatherDataFromJson(utf8.decode(response.bodyBytes));
    setState(() {
      _dayString = weather.name!;
      _sunriseString = weather.sunrise!;
      _sunsetString = weather.sunset!;
      _minString = weather.tempMin!;
      _maxString = weather.tempMax!;
      _description = weather.description!;
      _icon = weather.icon!;
      _actString = weather.temp!;
    });
    return weather;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black87,
      body: Container(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              DateRow(
                  dayString: _dayString,
                  dateString: _dateString,
                  timeString: _timeString),
              Row(
                children: [
                  Expanded(
                    flex: 3,
                    child: TemperatureWidget(
                      minString: _minString,
                      maxString: _maxString,
                    ),
                  ),
                  Expanded(
                    flex: 2,
                    child: Container(
                      child: SvgPicture.asset('images/' + _icon + '.svg'),
                      color: Colors.white70,
                    ),
                  ),
                  Expanded(
                    flex: 3,
                    child: SunriseSunsetWidget(
                      sunriseString: _sunriseString,
                      sunsetString: _sunsetString,
                    ),
                  ),
                ],
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  DayInfo(actString: _description),
                  DayInfo(actString: _actString),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _getTime() {
    final DateTime now = DateTime.now();
    final String formattedTime = _formatTime(now);
    final String formattedDate = _formatDate(now);
    setState(() {
      _timeString = formattedTime;
      _dateString = formattedDate;
    });
  }

  String _formatTime(DateTime dateTime) {
    return DateFormat.Hms('de').format(dateTime);
  }

  String _formatDate(DateTime dateTime) {
    return DateFormat.yMd('de').format(dateTime);
  }
}
