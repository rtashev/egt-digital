package com.egt.gateway.command;

import com.egt.gateway.command.domain.CommandRequest;
import com.egt.gateway.session.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("public/v1/")
@AllArgsConstructor
public class CommandController {

    private final SessionService sessionService;

    @PostMapping(value = "xml_api/command",
                 consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE},
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> executeCommand(@RequestBody CommandRequest command) {
        return command.enter != null ? executeEnterCommand(command) : executeGetCommand(command);
    }

    private ResponseEntity<?> executeGetCommand(CommandRequest command) {
        var requestIdsList = sessionService.findRequestIds(command.get.session, command.id);
        return ResponseEntity.of(Optional.ofNullable(requestIdsList));
    }

    private ResponseEntity<?> executeEnterCommand(CommandRequest command) {
        String userId = command.enter.player.toString();
        sessionService.insertSession(command.enter.session, command.id, userId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
