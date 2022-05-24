import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class SunriseSunsetWidget extends StatelessWidget {
  const SunriseSunsetWidget({
    Key? key,
    required String sunriseString,
    required String sunsetString,
  })  : _sunriseString = sunriseString,
        _sunsetString = sunsetString,
        super(key: key);

  final String _sunriseString;
  final String _sunsetString;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: SvgPicture.asset(
                'images/sunrise.svg',
                height: 40,
              ),
            ),
            Text(_sunriseString,
                style: const TextStyle(
                  fontSize: 48,
                  fontWeight: FontWeight.bold,
                  color: Colors.blue,
                )),
          ],
        ),
        Row(
          children: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: SvgPicture.asset(
                'images/sunset.svg',
                height: 40,
              ),
            ),
            Text(_sunsetString,
                style: const TextStyle(
                  fontSize: 48,
                  fontWeight: FontWeight.bold,
                  color: Colors.blue,
                )),
          ],
        ),
      ],
    );
  }
}
