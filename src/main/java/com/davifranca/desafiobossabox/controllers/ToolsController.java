package com.davifranca.desafiobossabox.controllers;

import com.davifranca.desafiobossabox.dto.ToolsRequest;
import com.davifranca.desafiobossabox.dto.ToolsResponse;
import com.davifranca.desafiobossabox.entity.Tools;
import com.davifranca.desafiobossabox.repository.ToolsRepository;
import com.davifranca.desafiobossabox.services.ToolsService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tools")
public class ToolsController {

    @Autowired
    private ToolsService toolsService;

    @GetMapping
    public ResponseEntity findAll(){
        List<Tools> allTools = toolsService.getAllTools();

        return ResponseEntity.ok().body(allTools);
    }

    @GetMapping("/findByTag")
    public ResponseEntity findToolsByTag(@RequestParam List<String> tags){
        List<Tools> tooList = toolsService.findToolByTag(tags);

        return ResponseEntity.ok().body(tooList);
    }

    @PostMapping
    public ResponseEntity saveTools(@RequestBody ToolsRequest toolsRequest){
        Tools toolsToBeSaved = toolsRequest.toModel();
        ToolsResponse toolsResponse = toolsService.saveTools(toolsToBeSaved);

        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toolsResponse.id())
                .toUri();

        return ResponseEntity.created(headerLocation).body(toolsResponse);
    }

    @DeleteMapping
    public ResponseEntity deleteTools(@RequestParam Long id){
        toolsService.deleteToolsById(id);

        return ResponseEntity.ok().body("Tools Deleted");
    }
}
