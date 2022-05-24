import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class TemperatureWidget extends StatelessWidget {
  const TemperatureWidget({
    Key? key,
    required String minString,
    required String maxString,
  })  : _minString = minString,
        _maxString = maxString,
        super(key: key);

  final String _minString;
  final String _maxString;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            SvgPicture.asset(
              'images/temperature.svg',
              height: 40,
            ),
            Text(_minString,
                style: const TextStyle(
                  fontSize: 48,
                  fontWeight: FontWeight.bold,
                  color: Colors.green,
                )),
          ],
        ),
        Row(
          children: [
            SvgPicture.asset(
              'images/temperature.svg',
              height: 40,
            ),
            Text(_maxString,
                style: const TextStyle(
                  fontSize: 48,
                  fontWeight: FontWeight.bold,
                  color: Colors.green,
                )),
          ],
        ),
      ],
    );
  }
}
