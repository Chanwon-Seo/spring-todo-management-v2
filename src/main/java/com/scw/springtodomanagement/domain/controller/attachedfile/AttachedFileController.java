package com.scw.springtodomanagement.domain.controller.attachedfile;

import com.scw.springtodomanagement.domain.service.AttachedFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "02. Post")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attachedfile")
public class AttachedFileController {
    private final AttachedFileService attachedFileService;

    @GetMapping("/{id}")
    public void findByAttachedFile() {

    }

}
