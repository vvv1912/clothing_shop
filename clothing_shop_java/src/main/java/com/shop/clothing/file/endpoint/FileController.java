package com.shop.clothing.file.endpoint;

import com.shop.clothing.common.Cqrs.ISender;
import com.shop.clothing.file.command.uploadFile.UploadFileCommand;
import com.shop.clothing.file.command.uploadFiles.UploadFilesCommand;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/api/file")
@AllArgsConstructor
public class FileController {
    private final ISender sender;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) {
        var command = new UploadFileCommand(file);
        var result = sender.send(command);
        return ResponseEntity.ok(result.orThrow());
    }
    @PostMapping("/uploads")
    public ResponseEntity<Collection<String>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        var command = new UploadFilesCommand(files);
        var result = sender.send(command);
        return ResponseEntity.ok(result.orThrow());
    }
}
