import 'package:flutter/material.dart';

class DayInfo extends StatelessWidget {
  const DayInfo({
    Key? key,
    required String actString,
  })  : _actString = actString,
        super(key: key);

  final String _actString;

  @override
  Widget build(BuildContext context) {
    return Text(
      _actString,
      style: const TextStyle(fontSize: 24, color: Colors.deepOrangeAccent),
    );
  }
}
