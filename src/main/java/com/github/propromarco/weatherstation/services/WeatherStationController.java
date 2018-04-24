package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.data.WeatherForecastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WeatherStationController {

    private static final String INDEX_HTML = "/index.html";

    @Autowired
    private WeatherStationService weatherStationService;

    @RequestMapping("/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(INDEX_HTML);
    }

    @RequestMapping(INDEX_HTML)
    public String index(Model model) {
        WeatherForecastResponse troisdorf = weatherStationService.getTroisdorf();
        WeatherForecastResponse hadamar = weatherStationService.getHadamar();
        WeatherForecastResponse koeln = weatherStationService.getKoeln();
        model.addAttribute("ort", koeln);
        model.addAttribute("helper", new Helper());
        return "index";
    }

}
