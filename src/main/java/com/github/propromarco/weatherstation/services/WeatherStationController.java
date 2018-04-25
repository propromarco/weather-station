package com.github.propromarco.weatherstation.services;

import com.github.propromarco.weatherstation.jabx.Current;
import com.github.propromarco.weatherstation.jabx.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        Forecast troisdorf = weatherStationService.getTroisdorfForecast();
        Forecast hadamar = weatherStationService.getHadamarForecast();
        Forecast koeln = weatherStationService.getKoelnForecast();
        Current troisdorfCurrent = weatherStationService.getTroisdorfCurrent();
        Current hadamarCurrent = weatherStationService.getHadamarCurrent();
        Current koelnCurrent = weatherStationService.getKoelnCurrent();
        List<CurrentWithForecast> all = new ArrayList<CurrentWithForecast>();
        all.add(new CurrentWithForecast(troisdorfCurrent, troisdorf));
        all.add(new CurrentWithForecast(koelnCurrent, koeln));
        all.add(new CurrentWithForecast(hadamarCurrent, hadamar));
        model.addAttribute("all", all);
        model.addAttribute("ort", troisdorf);
        model.addAttribute("current", troisdorfCurrent);
        model.addAttribute("helper", new Helper());
        return "index";
    }

}
