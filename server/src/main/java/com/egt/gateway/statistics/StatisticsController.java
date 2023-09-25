package com.egt.gateway.statistics;

import com.egt.gateway.statistics.domain.UserStatistics;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/v1/statistics")
@AllArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping()
    public UserStatistics getUserStatistics(@RequestParam("userId") String userId)
    {
        return statisticsService.getUserStatistics(userId);
    }

}
