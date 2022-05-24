import 'package:flutter/material.dart';

class DateRow extends StatelessWidget {
  const DateRow({
    Key? key,
    required String dayString,
    required String dateString,
    required String timeString,
  })  : _dayString = dayString,
        _dateString = dateString,
        _timeString = timeString,
        super(key: key);

  final String _dayString;
  final String _dateString;
  final String _timeString;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Expanded(
          flex: 4,
          child: SizedBox(
              height: 100,
              child: Center(
                child: Text(
                  _dayString,
                  style: const TextStyle(
                    fontSize: 48,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              )),
        ),
        Expanded(
          flex: 4,
          child: SizedBox(
              height: 100,
              child: Center(
                child: Text(
                  _dateString,
                  style: const TextStyle(
                    fontSize: 48,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              )),
        ),
        Expanded(
          flex: 4,
          child: SizedBox(
              height: 100,
              child: Center(
                child: Text(
                  _timeString,
                  style: const TextStyle(
                    fontSize: 48,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              )),
        )
      ],
    );
  }
}
