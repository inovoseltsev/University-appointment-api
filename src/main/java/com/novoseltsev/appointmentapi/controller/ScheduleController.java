package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.ScheduleDayDto;
import com.novoseltsev.appointmentapi.domain.entity.ScheduleDay;
import com.novoseltsev.appointmentapi.service.ScheduleService;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/appointments-api/users/teachers/schedule")
@Validated
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/{id}")
    public ScheduleDayDto getDayById(@PathVariable Long id) {
        return ScheduleDayDto.fromScheduleDay(scheduleService.findById(id));
    }

    @GetMapping("/teacher-days/{teacherId}")
    public List<ScheduleDayDto> getTeacherSchedule(
            @PathVariable Long teacherId
    ) {
        return scheduleService.findTeacherScheduleDays(teacherId)
                .stream().map(ScheduleDayDto::fromScheduleDay)
                .collect(Collectors.toList());
    }

    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<ScheduleDayDto> createDay(
            @Valid @RequestBody ScheduleDayDto dayDto,
            @PathVariable Long teacherId
    ) {
        ScheduleDay createdDay = scheduleService
                .createDay(dayDto.toScheduleDay(), teacherId);
        return new ResponseEntity<>(ScheduleDayDto.fromScheduleDay(createdDay),
                HttpStatus.CREATED);
    }

    @PostMapping("/teacher-days/{teacherId}")
    public ResponseEntity<HttpStatus> createDays(
            @PathVariable Long teacherId,
            @RequestBody List<@Valid ScheduleDayDto> daysDto
    ) {
        scheduleService.createAll(daysDto.stream()
                .map(ScheduleDayDto::toScheduleDay)
                .collect(Collectors.toList()), teacherId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ScheduleDayDto updateDay(@Valid @RequestBody ScheduleDayDto dayDto) {
        return ScheduleDayDto.fromScheduleDay(scheduleService
                .updateDay(dayDto.toScheduleDay()));
    }

    @PutMapping("/teacher-days/{teacherId}")
    public void updateTeacherDays(
            @RequestBody Queue<@Valid ScheduleDayDto> daysDto,
            @PathVariable Long teacherId
    ) {
        scheduleService.updateAllTeacherDays(daysDto.stream()
                .map(ScheduleDayDto::toScheduleDay)
                .collect(Collectors.toCollection(LinkedList::new)), teacherId);
    }

    @DeleteMapping("/{id}")
    public void deleteDay(@PathVariable Long id) {
        scheduleService.deleteDayById(id);
    }

    @DeleteMapping
    public void deleteDays(@RequestBody List<Long> dayIdList) {
        scheduleService.deleteAll(dayIdList);
    }
}
